package com.jxywkj.application.pet.api.consign.city;

import lombok.Data;

import java.util.Objects;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-07-15 14:45
 * @Version 1.0
 */
@Data
public class CityHeader implements Comparable<CityHeader> {
    String cityName;

    public CityHeader() {
    }

    public CityHeader(String cityName) {
        this.cityName = cityName;
    }

    public CityHeader(CityBody body) {
        this.cityName = body.cityPY.substring(0, 1);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityHeader that = (CityHeader) o;
        return cityName.equals(that.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityName);
    }

    @Override
    public int compareTo(CityHeader o) {
        return this.getCityName().compareTo(o.getCityName());
    }
}
