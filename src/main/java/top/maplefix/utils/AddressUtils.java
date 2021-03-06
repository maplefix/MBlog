package top.maplefix.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;
import top.maplefix.utils.file.FileUtils;

import java.io.File;
import java.lang.reflect.Method;

/**
 * @author : Maple
 * @description : 根据登录IP获取省份地区信息：
 *                  1.调用太平台网络API实现（境外IP有时获取不到）
 *                  2.调用腾讯API实现(每天限额10000次)
 * @date : 2020/2/21 21:04
 */
@Slf4j
public class AddressUtils {

    /**
     * 太平洋网络IP查询API地址
     */
    private static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";
    /**
     * 腾讯根据IP获取位置信息API地址
     */
    private static final String TX_URL = "https://apis.map.qq.com/ws/location/v1/ip";

    /**
     * 太平洋网络获取位置信息,参考api<p>http://whois.pconline.com.cn/</p>
     * @param ip ip 地址
     * @return 位置信息
     */
    public static String getRealAddressByIp(String ip){
        long start = System.currentTimeMillis();
        String address = "未知位置";
        RestTemplate restTemplate = new RestTemplate();
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        String rspStr = restTemplate.getForObject(IP_URL + "?json=true&ip=" + ip, String.class);
        if (StringUtils.isEmpty(rspStr)) {
            log.error("该IP{}获取地理位置异常", ip);
            return address;
        }
        /**
         {
             "ip": "82.165.100.123",
             "pro": "",
             "proCode": "999999",
             "city": "",
             "cityCode": "0",
             "region": "",
             "regionCode": "0",
             "addr": " 德国",
             "regionNames": "",
             "err": "noprovince"
         }
         */
        JSONObject obj = JSONObject.parseObject(rspStr);
        String province = obj.getString("pro");
        String city = obj.getString("city");
        String nation = obj.getString("addr");
        if(!StringUtils.isEmpty(province) && !StringUtils.isEmpty(city)){
            address = province + " " + city;
        }else if(!StringUtils.isEmpty(nation)) {
            address = nation;
        }
        long end = System.currentTimeMillis();
        log.info("太平洋位置API耗时：{}",(end-start));
        return address;
    }

    /**
     * 腾讯API获取位置信息,api参考<p>https://lbs.qq.com/webservice_v1/guide-ip.html</p>
     * @param ip ip 地址
     * @return
     */
    public static String getRealAddressByIpWithTxApi(String ip){
        long start = System.currentTimeMillis();
        //腾讯API调用key
        String key = "KKKBZ-B5JKD-NEF4O-HXJHS-5JY3F-HHBH7";
        String address = "未知位置";
        RestTemplate restTemplate = new RestTemplate();
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        //https://apis.map.qq.com/ws/location/v1/ip?ip=61.135.17.68&key=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77
        JSONObject rspStr = restTemplate.getForObject(TX_URL + "?output=json&ip=" + ip + "&key=" + key, JSONObject.class);
        if (StringUtils.isEmpty(rspStr)) {
            log.error("该IP{}获取地理位置异常", ip);
            return address;
        }
        JSONObject ad_info = rspStr.getJSONObject("result").getJSONObject("ad_info");
        /**
         {
             "status": 0,
             "message": "query ok",
             "result": {
                 "ip": "82.165.100.123",
                 "location": {
                     "lat": 52.5167,
                     "lng": 13.4
                 },
                 "ad_info": {
                     "nation": "德国",
                     "province": "",
                     "city": "",
                     "district": "",
                     "adcode": -1
                 }
             }
         }
         */
        String province = ad_info.getString("province");
        String city = ad_info.getString("city");
        String nation = ad_info.getString("nation");
        if(!StringUtils.isEmpty(province) && !StringUtils.isEmpty(city)){
            address = province + " " + city;
        }else if(!StringUtils.isEmpty(nation)) {
            address = nation;
        }
        long end = System.currentTimeMillis();
        log.info("腾讯位置API耗时：{}", (end-start));
        return address;
    }

    /**
     * 根据Ip使用ip2region进行查询地址
     *
     * @param ip ip地址
     * @return 地址信息
     */
    public static String getCityInfoByIp(String ip) {
        try {
            String path = "ip2region/ip2region.db";
            String name = "ip2region.db";
            DbConfig config = new DbConfig();
            File file = FileUtils.inputStreamToFile(new ClassPathResource(path).getInputStream(), name);
            DbSearcher searcher = new DbSearcher(config, file.getPath());
            Method method;
            method = searcher.getClass().getMethod("btreeSearch", String.class);
            DataBlock dataBlock;
            dataBlock = (DataBlock) method.invoke(searcher, ip);
            String address = dataBlock.getRegion().replace("0|", "");
            if (address.charAt(address.length() - 1) == '|') {
                address = address.substring(0, address.length() - 1);
            }
            return address.equals("内网IP|内网IP") ? "内网IP" : address;
        } catch (Exception e) {
            log.error("Get Error In Get City Address ,{}", e.getMessage(), e);
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(getRealAddressByIp("127.0.0.1"));
        System.out.println(getRealAddressByIpWithTxApi("82.165.100.123"));
    }
}
