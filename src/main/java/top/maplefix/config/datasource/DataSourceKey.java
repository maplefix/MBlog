package top.maplefix.config.datasource;

/**
 * @author : Maple
 * @description : 多数据源key
 * @date : Created in 2019/7/24 18：10
 * @editor: Edited in 2019/1120 14:17
 * @version: v2.1
 */
public enum DataSourceKey {
    /**
     * 数据源一
     */
    MASTER("master"),

    /**
     * 数据源二
     */
    SLAVE("slave");

    private String name;

    DataSourceKey (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}