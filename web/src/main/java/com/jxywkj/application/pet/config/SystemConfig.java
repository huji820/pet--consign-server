package com.jxywkj.application.pet.config;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-05-09 23:15
 * @Version 1.0
 */
public class SystemConfig {
    /**
     * map的初始化大小
     */
    public static int MAP_INIT_SIZE= 16;

    /**
     *商品类型的初始化大小
     */
    public static final int INIT_TYPE_ID = 1001;

    /**
     * 经纬度偏移
     */
    public static double lonAndLatOffset = 0.00005;
    /**
     * 禁用状态
     */
    public static int disabledStatus = 1;
    /**
     * 成功行数
     */
    public static int successRow = 1;
    /**
     * 图片缩放比例
     */
    public static double scaleSize = 0.25;
    /**
     * 对外资源路径
     */
    public static String resourceName = "/resource/";
    /**
     * 导入职员角色的名称
     */
    public static String uploadRoleName= "导入人员";
    /**
     * 导入职员角色的Id(和数据库中对应)
     */
    public static int uploadRoleId = 2;
    /**
     * 达到多少条后插入数据库
     */
    public static int uploadMaxQuantity = 200;
    /**
     * 职员excel列数
     */
    public static int staffExcelColumnCount = 7;
    /**
     * 客户excel列数
     */
    public static int customerExcelColumnCount = 14;
    /**
     * 客户导入默认经度
     */
    public static double uploadCustomerLon = 1;
    /**
     * 客户导入默认维度
     */
    public static double uploadCustomerLat= 1;

    /**
     * 商品类型缓存空间名称
     */
    public static String cacheType= "typeCache";
}
