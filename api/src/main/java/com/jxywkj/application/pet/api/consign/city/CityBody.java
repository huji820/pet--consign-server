package com.jxywkj.application.pet.api.consign.city;

import com.yangwang.sysframework.utils.PinyinUtil;
import lombok.Data;

/**
 * @Description
 * @Author Administrator
 * @Date 2019-07-15 14:45
 * @Version 1.0
 */
@Data
public class CityBody  implements Comparable<CityBody> {

    String cityName;
    String cityPinYin;
    String cityPY;
    String first;

    public CityBody() {

    }

    public CityBody(String cityName) {
        this.cityName = cityName;
        if (cityName.equals("长春市")) {
            this.cityPinYin = "changcunshi";
            this.cityPY = "ccs";
        }
        else if (cityName.equals("常州市")) {
            this.cityPinYin = "changzhoushi";
            this.cityPY = "czs";
        } else if (cityName.equals("厦门市")) {
            this.cityPinYin = "xiamenshi";
            this.cityPY = "xms";
        } else if (cityName.equals("重庆市")) {
            this.cityPinYin = "chongqingshi";
            this.cityPY = "cqs";
        } else {
            this.cityPinYin = PinyinUtil.cn2py(cityName, PinyinUtil.RET_PINYIN_TYPE_FULL);
            this.cityPY = PinyinUtil.cn2py(cityName, PinyinUtil.RET_PINYIN_TYPE_HEADCHAR);
        }
        this.first = this.cityPY.substring(0, 1);
    }

    public CityBody(String cityName, String cityPinYin, String cityPY) {
        this.cityName = cityName;
        this.cityPinYin = cityPinYin;
        this.cityPY = cityPY;
        this.first =this.cityPY.substring(0, 1);
    }

    @Override
    public int compareTo(CityBody o) {
        return this.getFirst().compareTo(o.getFirst());
    }
}
