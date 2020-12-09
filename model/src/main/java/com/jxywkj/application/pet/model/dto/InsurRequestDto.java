package com.jxywkj.application.pet.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class InsurRequestDto {

    @ApiModelProperty(value = "流水号，保证唯一性")
    String sn;

    @ApiModelProperty(value = "货主姓名")
    String name;

    @ApiModelProperty(value = "身份证")
    String idCode;

    @ApiModelProperty(value = "联系电话")
    String phone;

    @ApiModelProperty(value = "保险类型编码")
    String pcode;

    @ApiModelProperty(value = "起始地")
    String startPlace;

    @ApiModelProperty(value = "目的地")
    String destination;

    @ApiModelProperty(value = "运输类型(1汽运，2飞机)")
    String transportType;

    @ApiModelProperty(value = "运单号")
    String transportCode;

    @ApiModelProperty(value = "车次/航班")
    String vehicleNumbe;

    @ApiModelProperty(value = "汽运车牌号")
    String carPlate;

    @ApiModelProperty(value = "出发时间(yyyy-MM-dd HH:mm:ss)")
    String departureTime;

    @ApiModelProperty(value = "预计到达时间(yyyy-MM-dd HH:mm:ss)")
    String arriveTime;

    @ApiModelProperty(value = "宠物种类，例：狗，猫")
    String petCategory;

    @ApiModelProperty(value = "宠物性别，M雄性，F雌性")
    String petSex;

    @ApiModelProperty(value = "宠物出生日期，格式yyyy-MM-dd")
    String petBirthday;

    @ApiModelProperty(value = "宠物品种")
    String petVariety;

    @ApiModelProperty(value = "毛色")
    String petColor;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getTransportCode() {
        return transportCode;
    }

    public void setTransportCode(String transportCode) {
        this.transportCode = transportCode;
    }

    public String getVehicleNumbe() {
        return vehicleNumbe;
    }

    public void setVehicleNumbe(String vehicleNumbe) {
        this.vehicleNumbe = vehicleNumbe;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getPetCategory() {
        return petCategory;
    }

    public void setPetCategory(String petCategory) {
        this.petCategory = petCategory;
    }

    public String getPetSex() {
        return petSex;
    }

    public void setPetSex(String petSex) {
        this.petSex = petSex;
    }

    public String getPetBirthday() {
        return petBirthday;
    }

    public void setPetBirthday(String petBirthday) {
        this.petBirthday = petBirthday;
    }

    public String getPetVariety() {
        return petVariety;
    }

    public void setPetVariety(String petVariety) {
        this.petVariety = petVariety;
    }

    public String getPetColor() {
        return petColor;
    }

    public void setPetColor(String petColor) {
        this.petColor = petColor;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
