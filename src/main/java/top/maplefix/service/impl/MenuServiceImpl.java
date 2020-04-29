package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.maplefix.constant.Constant;
import top.maplefix.constant.MenuConstant;
import top.maplefix.mapper.MenuMapper;
import top.maplefix.mapper.RoleMenuMidMapper;
import top.maplefix.model.Menu;
import top.maplefix.service.MenuService;
import top.maplefix.utils.SecurityUtils;
import top.maplefix.utils.StringUtils;
import top.maplefix.vo.MetaVo;
import top.maplefix.vo.RouterVo;
import top.maplefix.vo.TreeSelect;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Maple
 * @description 角色信息接口实现类
 * @date 2020/1/18 11:04
 */
@Service
public class MenuServiceImpl implements MenuService {

    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMidMapper roleMenuMapper;

    @Override
    public List<Menu> selectMenuList(Menu menu) {
        return menuMapper.selectMenuList(menu);
    }

    @Override
    public Set<String> selectMenuPermsByUserId(String userId) {
        List<String> perms = menuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<Menu> selectMenuTreeByUserId(String userId) {
        List<Menu> menus;
        if (SecurityUtils.isAdmin(userId)) {
            menus = menuMapper.selectMenuTreeAll();
        } else {
            menus = menuMapper.selectMenuTreeByUserId(userId);
        }
        return getChildPerms(menus, MenuConstant.TOP_ID);
    }

    @Override
    public List<Integer> selectMenuListByRoleId(String roleId) {
        return menuMapper.selectMenuListByRoleId(roleId);
    }

    @Override
    public List<RouterVo> buildMenus(List<Menu> menus) {
        List<RouterVo> routers = new LinkedList<>();
        for (Menu menu : menus) {
            RouterVo router = new RouterVo();
            router.setName(menu.getMenuName());
            router.setPath(getRouterPath(menu));
            router.setComponent(StringUtils.isEmpty(menu.getComponent()) ? "Layout" : menu.getComponent());
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
            router.setName(menu.getMenuName());
            List<Menu> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && MenuConstant.MENU == menu.getMenuType()) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            }
            routers.add(router);
        }
        return routers;
    }

    @Override
    public List<Menu> buildMenuTree(List<Menu> menus) {
        List<Menu> returnList = new ArrayList<>();
        for (Iterator<Menu> iterator = menus.iterator(); iterator.hasNext(); ) {
            Menu t = iterator.next();
            // 根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (MenuConstant.TOP_ID.equals(t.getParentId())) {
                recursionFn(menus, t);
                returnList.add(t);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<Menu> menus) {
        List<Menu> menuTrees = buildMenuTree(menus);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public Menu selectMenuById(String menuId) {
        return menuMapper.selectMenuById(menuId);
    }

    @Override
    public boolean hasChildByMenuId(String menuId) {
        int result = menuMapper.hasChildByMenuId(menuId);
        return result > 0;
    }

    @Override
    public boolean checkMenuExistRole(String menuId) {
        int result = roleMenuMapper.checkMenuExistRole(menuId);
        return result > 0;
    }

    @Override
    public int insertMenu(Menu menu) {
        return menuMapper.insertMenu(menu);
    }

    @Override
    public int updateMenu(Menu menu) {
        return menuMapper.updateMenu(menu);
    }

    @Override
    public int deleteMenuById(String menuId) {
        String loginUsername = SecurityUtils.getUsername();
        return menuMapper.deleteMenuById(menuId, loginUsername);
    }

    @Override
    public String checkMenuNameUnique(Menu menu) {
        String menuId = menu.getMenuId();
        Menu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (StringUtils.isNotNull(info) && !info.getMenuId().equals(menuId)) {
            return Constant.NOT_UNIQUE;
        }
        return Constant.UNIQUE;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(Menu menu) {
        String routerPath = menu.getPath();
        // 非外链并且是一级目录
        if (MenuConstant.TOP_ID.equals(menu.getParentId())
                && MenuConstant.IS_FRAME == menu.getIsFrame()) {
            routerPath = "/" + menu.getPath();
        }
        return routerPath;
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<Menu> getChildPerms(List<Menu> list, String parentId) {
        List<Menu> returnList = new ArrayList<>();
        for (Iterator<Menu> iterator = list.iterator(); iterator.hasNext(); ) {
            Menu t = iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId().equals(parentId)) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<Menu> list, Menu t) {
        // 得到子节点列表
        List<Menu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (Menu tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<Menu> it = childList.iterator();
                while (it.hasNext()) {
                    Menu n = it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<Menu> getChildList(List<Menu> list, Menu t) {
        List<Menu> tlist = new ArrayList<>();
        for (Menu n : list) {
            if (n.getParentId().equals(t.getMenuId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<Menu> list, Menu t) {
        return getChildList(list, t).isEmpty();
    }
}
