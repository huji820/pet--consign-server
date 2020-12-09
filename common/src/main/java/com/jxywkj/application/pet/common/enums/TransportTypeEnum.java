package com.jxywkj.application.pet.common.enums;


/**
 * @Author LiuXiangLin
 * @Description 运输类型枚举
 * @Date 11:29 2019/7/23
 * @Param
 * @return
 **/
public enum TransportTypeEnum {
    /**/
    SPECIAL_TRAIN("专车", 1),
    RAILWAY("铁路", 2),
    AIRCRAFT("单飞", 3),
    RANDOM("随机", 4),
    BUS("大巴", 5);

    String name;
    int type;

    TransportTypeEnum(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public static TransportTypeEnum getTransportTypeEnum(int type) {
        if (type == SPECIAL_TRAIN.getType()) {
            return SPECIAL_TRAIN;
        }
        if (type == RAILWAY.getType()) {
            return RAILWAY;
        }
        if (type == AIRCRAFT.getType()) {
            return AIRCRAFT;
        }
        if (type == RANDOM.getType()) {
            return RANDOM;
        }
        if (type == BUS.getType()) {
            return BUS;
        }

        return null;
    }

    /**
     * <p>
     * 通过订单类型查询订单类型枚举
     * </p>
     *
     * @param typeName 订单类型
     * @return com.jxywkj.application.pet.common.enums.TransportTypeEnum
     * @author LiuXiangLin
     * @date 15:33 2019/12/26
     **/
    public static TransportTypeEnum filterByTypeName(String typeName) {
        if (SPECIAL_TRAIN.getName().equals(typeName)) {
            return SPECIAL_TRAIN;
        }
        if (RAILWAY.getName().equals(typeName)) {
            return RAILWAY;
        }
        if (AIRCRAFT.getName().equals(typeName)) {
            return AIRCRAFT;
        }
        if (RANDOM.getName().equals(typeName)) {
            return RANDOM;
        }
        if (BUS.getName().equals(typeName)) {
            return BUS;
        }
        return null;
    }

}
