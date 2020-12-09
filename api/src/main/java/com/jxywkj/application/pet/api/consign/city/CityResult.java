package com.jxywkj.application.pet.api.consign.city;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-07-15 14:44
 * @Version 1.0
 */
@Data
public class CityResult {
    List<CityHeader> headers;
    List<CityBody> bodys;

    public CityResult() {
        headers = new ArrayList<>();
        bodys = new ArrayList();
    }

    public void addCityBody(CityBody cb) {
        CityHeader ch = new CityHeader(cb);
        if (!headers.contains(ch)) {
            headers.add(ch);
            bodys.add(new CityBody(cb.cityPY.substring(0,1), cb.cityPY.substring(0,1), cb.cityPY.substring(0,1)));
        }
        bodys.add(cb);
    }

    public void sort() {
        Collections.sort(bodys);
        Collections.sort(headers);
    }
}
