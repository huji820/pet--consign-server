package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.consign.Partner;

import java.util.List;

public interface PartnerService {

    /**
     * 合伙伙伴查询数量
     * @param stationNo
     * @param partnerName
     * @return
     */
    int countPartner(int stationNo, String partnerName,String province,String city);

    /**
     * 获取托运做的所有合作伙伴
     * @param stationNo
     * @return
     */
    List<Partner> listPartner(int stationNo, String partnerName, String province, String city,String county, int start, int end);

    /**
     * 添加合作伙伴
     * @param partner
     * @return
     */
    void insertPartner(Partner partner);

    /**
     * 删除合作伙伴
     * @param partnerNo
     * @return
     */
    void deletePartner(int partnerNo);

    /**
     * 修改和合作伙伴建立连接
     * @param partner 网点
     */
    void updatePartner(Partner partner);
}
