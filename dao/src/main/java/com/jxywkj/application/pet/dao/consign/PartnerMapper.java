package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.Partner;
import com.jxywkj.application.pet.model.consign.PartnerContact;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PartnerMapper {

    /**
     * 获取托运做的所有合作伙伴
     *
     * @param stationNo
     * @return
     */
    List<Partner> listPartner(int stationNo);

    /**
     * 添加合作伙伴
     *
     * @param partner
     * @return
     */
    int insertPartner(Partner partner);

    /**
     * 添加一个合伙人并且返回主键
     * @param partner
     * @return
     */
    int insertPartnerAddress(Partner partner);


    /**
     * 删除合作伙伴
     *
     * @param partnerNo
     * @return
     */
    void deletePartner(int partnerNo);

    /**
     * 修改网点
     *
     * @param partner
     */
    void updatePartner(Partner partner);

    /**
     * 合伙伙伴查询数量
     *
     * @param stationNo
     * @param partnerName
     * @return
     */
    int countPartner(@Param("stationNo") int stationNo, @Param("partnerName") String partnerName, @Param("province") String province, @Param("city") String city);

    /**
     * 获取托运做的所有合作伙伴
     *
     * @param stationNo
     * @return
     */
    List<Partner> listPartner(@Param("stationNo") int stationNo, @Param("partnerName") String partnerName, @Param("province") String province, @Param("city") String city, @Param("county") String county, @Param("start") int start, @Param("end") int end);

    /*
     * @Author Aze
     * @Description: 通过合作伙伴No查询联系人信息
     * @Date:
     * @Param
     * @return
     */
    List<PartnerContact> listpartnerContactByPartnerNo(@Param("partnerNo") int stationNo);
}
