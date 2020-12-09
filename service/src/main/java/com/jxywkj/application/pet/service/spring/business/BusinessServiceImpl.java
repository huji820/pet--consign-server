package com.jxywkj.application.pet.service.spring.business;

import com.jxywkj.application.pet.common.enums.BusinessStateEnum;
import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.BeanUtils;
import com.jxywkj.application.pet.common.utils.FileUtils;
import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.common.utils.StringUtils;
import com.jxywkj.application.pet.dao.business.BusinessMapper;
import com.jxywkj.application.pet.model.business.Business;
import com.jxywkj.application.pet.model.business.Business2;
import com.jxywkj.application.pet.model.common.BusinessPosition;
import com.jxywkj.application.pet.model.common.Customer;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.params.LonAndLat;
import com.jxywkj.application.pet.model.vo.AuthVo;
import com.jxywkj.application.pet.service.facade.business.BusinessService;
import com.jxywkj.application.pet.service.facade.common.CustomerMessageService;
import com.jxywkj.application.pet.service.facade.common.CustomerService;
import com.jxywkj.application.pet.service.spring.consign.GaoDeMapUtils;
import com.yangwang.sysframework.utils.DateUtil;
import com.yangwang.sysframework.utils.JsonUtil;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import com.yangwang.sysframework.utils.network.HttpUtil;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName BusinessService
 * @Description: 商家端
 * @Author Aze
 * @Date 2019/8/14 13:43
 * @Version 1.0
 **/
@Service
public class BusinessServiceImpl implements BusinessService {

    /** 平台认证*/
    private static final String BUSINESS_AUTH = "/api/business/auth";
    /** 商家信息*/
    private static final String BUSINESS_DETAIL = "/api/business/detail";
    /** 响应成功key*/
    private static final String RESPOND_CODE_KEY = "code";
    /** 响应成功code值*/
    private static final int RESPOND_SUCCESS_CODE = 10000;
    /** 响应成功返回值key*/
    private static final String RESPOND_SUCCESS_DATE_KEY = "root";

    @Value("${market.server.host}")
    String marketServiceHost;

    @Resource
    private HttpUtil httpUtil;

    @Resource
    GaoDeMapUtils gaoDeMapUtils;

    @Resource
    BusinessMapper businessMapper;

    @Resource
    FileUtils fileUtils;

    @Resource
    CustomerMessageService customerMessageService;

    @Resource
    CustomerService customerService;

    @Override
    public Business login(Business business) {
        business.setState(BusinessStateEnum.NORMAL.getType());
        return businessMapper.login(business);
    }

    @Override
    public int saveBusiness(Business business) {
        return businessMapper.saveBusiness(business);
    }

    @Override
    public Business getByBusinessNo(String businessNo, BusinessStateEnum businessStateEnum) {
        return businessMapper.getByBusinessNo(businessNo, businessStateEnum.getType());
    }

    @Override
    public Business selByBusinessByNo(String businessNo) {
        return businessMapper.selByBusinessByNo(businessNo);
    }

    @Override
    public List<Business> listByPosition(double longitude, double latitude, int offset, int limit, BusinessStateEnum businessStateEnum) {
        return businessMapper.listByPosition(getPosition(longitude, latitude, 20D, 20D),
                businessStateEnum.getType(), offset, limit);
    }

    @Override
    public int registerBusiness(Business business) {
        Customer customer = customerService.getCustomerByCustomerNo(business.getCustomerNo());
        business.setPassword("999");
        business.setBusinessNo(getBusinessNo());
        business.setRegisterTime(DateUtil.format(new Date(), DateUtil.FORMAT_FULL));
        business.setPayBond(0);
        business.setAuthType(0);
        business.setFans(0);
        business.setFollowQty(0);
        business.setState(0);
        business.setUnionId(customer.getUnionId());
        business.setPower(0);
        business.setComplete(1);
        business.setCredit(0);
        business.setLevel("一级铲屎官");
        setBusinessLonAndLat(business);
        return businessMapper.saveBusiness(business);
    }


    @Override
    public Business getBusinessByPhone(String phone, BusinessStateEnum businessStateEnum) {
        return businessMapper.getByPhone(phone, businessStateEnum != null ? String.valueOf(businessStateEnum.getType()) : null);
    }

    @Override
    public List<Business> listUnauditedBusiness(Station station) {
        return businessMapper.listByProvinceAndType(station.getProvince(),
                BusinessStateEnum.UNAUDITED.getType());
    }

    @Override
    public int updateBusinessState(Business business) {
        return businessMapper.updateBusinessState(business);
    }

    @Override
    public int rejectBusiness(Business business) {
        // 获取商家信息
        Business existsBusiness = businessMapper.getByBusinessNo(business.getBusinessNo(), BusinessStateEnum.UNAUDITED.getType());
        if (existsBusiness != null) {
            // 删除数据
            businessMapper.deleteByBusinessNo(business.getBusinessNo());

            // 发送推送
            return customerMessageService.saveRejectBusiness(business);
        }
        return 0;
    }

    @Override
    public Business getByOpenId(String openId) {
        return businessMapper.getByOpenId(openId);
    }

    @Override
    public Business getByUnionId(String unionId){
        return businessMapper.getByUnionId(unionId);
    }

    @Override
    public List<Business> listByGroupCity() {
        return businessMapper.listByGroupCity();
    }

    @Override
    public List<Business> listByProvince(String province) {
        return businessMapper.listByProvince(province);
    }

    @Override
    public Integer getHaveNewGiftBagByCustomerNo(String customerNo) {
        return businessMapper.getHaveNewGiftBagByCustomerNo(customerNo);
    }

    @Override
    public JsonResult update(Business2 business2) {
        try {
            Response response = httpUtil.postRequestBody(marketServiceHost + BUSINESS_DETAIL, BeanUtils.beanToMap(business2));
            return response.code() == 200 ? JsonResult.success() :
                    JsonResult.error(Code.INSERT_ERROR, "上传失败！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonResult.failed();
    }

    @Override
    public JsonResult getAuthByBusinessNo(String businessNo) {
        try {
            Response response = httpUtil.get(marketServiceHost + BUSINESS_AUTH + "/"+businessNo);
            return JsonResult.success(getRootData(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonResult.failed();
    }

    @Override
    public JsonResult insetBusiness(AuthVo authVo) {
        try {
            Response response = httpUtil.postRequestBody(marketServiceHost + BUSINESS_AUTH, BeanUtils.beanToMap(authVo));
            return response.code() == 200 ? JsonResult.success() :
                    JsonResult.error(Code.INSERT_ERROR, "上传失败！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonResult.failed();
    }

    @Override
    public void save(Business2 business2) {
        businessMapper.save(business2);
    }

    @Override
    public List<Business> listAdminBusiness(Business business, String startDate, String endDate, int start, int limit, BusinessStateEnum businessStateEnum) {
        return businessMapper.listAdminBusiness(business, startDate, endDate, start, limit, String.valueOf(businessStateEnum.getType()));
    }

    @Override
    public int countAdminBusiness(Business business, BusinessStateEnum businessStateEnum) {
        return businessMapper.countAdminBusiness(business, String.valueOf(businessStateEnum.getType()));
    }

    /**
     * @return void
     * @Author LiuXiangLin
     * @Description 设置对象经纬度
     * @Date 17:34 2019/9/9
     * @Param [business]
     **/
    private void setBusinessLonAndLat(Business business) {
        if (!StringUtils.isBlank(business.getProvince()) &&
                !StringUtils.isBlank(business.getCity()) &&
                !StringUtils.isBlank(business.getDetailAddress())) {
            LonAndLat lonAndLat = null;
            try {
                lonAndLat = gaoDeMapUtils.getLonAndLat(business.getProvince() + business.getCity() + business.getDetailAddress());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (lonAndLat != null) {
                business.setLongitude(Double.valueOf(lonAndLat.getLongitude()));
                business.setLatitude(Double.valueOf(lonAndLat.getLatitude()));
            }
        }
    }


    /**
     * @return java.lang.String
     * @Author LiuXiangLin
     * @Description 获取商户主键
     * @Date 11:23 2019/9/4
     * @Param []
     **/
    private String getBusinessNo() {
        return StringUtils.getUuid();
    }

    private BusinessPosition getPosition(double longitude, double latitude, double longitudeOffSet, double latitudeOffSet) {
        return new BusinessPosition(longitude, latitude, longitude - longitudeOffSet, longitude + longitudeOffSet, latitude - latitudeOffSet, latitude + latitudeOffSet);
    }

    private Object getRootData(Response response) throws Exception {
        if (response.isSuccessful()) {
            String respondStr = response.body().string();
            Map<String, Object> resultMap = (Map<String, Object>) JsonUtil.formObject(respondStr);
            if (TypeConvertUtil.$int(resultMap.get(RESPOND_CODE_KEY)) == RESPOND_SUCCESS_CODE) {
                return resultMap.get(RESPOND_SUCCESS_DATE_KEY);
            }
        }
        return null;
    }
}
