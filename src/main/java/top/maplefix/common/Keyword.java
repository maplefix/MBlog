package top.maplefix.common;

/**
 * @author : Maple
 * @description : 关键字
 * @date : Created in 2019/7/25 0:19
 * @version : v1.0
 */
public enum Keyword {


    /**
     * 系统配置
     */
    SYSTEM_CONFIG("systemConfig"),
    /**
     * 主题配置
     */
    THEME_CONFIG("themeConfig");

    private final String value;

    Keyword(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}