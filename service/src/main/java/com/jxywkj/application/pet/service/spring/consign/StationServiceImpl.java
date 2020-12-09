package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.common.enums.MediaFileTypeEnum;
import com.jxywkj.application.pet.common.enums.StaffStateEnum;
import com.jxywkj.application.pet.common.utils.AliOssObjectUtils;
import com.jxywkj.application.pet.common.utils.FileUpLoadState;
import com.jxywkj.application.pet.common.utils.FileUtils;
import com.jxywkj.application.pet.dao.consign.StationMapper;
import com.jxywkj.application.pet.model.common.StationPosition;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.service.facade.consign.StationService;
import com.yangwang.sysframework.utils.TypeConvertUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @
 * @Description
 * @Author Administrator
 * @Date 2019-06-20 21:17
 * @Version 1.0
 */
@Service
public class StationServiceImpl implements StationService {

    @Resource
    StationMapper stationMapper;

    @Resource
    AliOssObjectUtils aliOssObjectUtils;

    @Resource
    FileUtils fileUtils;

    @Override
    public Station getStation(int stationNo) {
        return stationMapper.getStation(stationNo);
    }

    @Override
    public List<Station> listStation(String startCity) {
        return stationMapper.listStationByCityName(startCity);
    }

    @Override
    public int uploadCollectionQRCode(String collectionQRCode, int stationNo) {
        return stationMapper.uploadCollectionQRCode(collectionQRCode, stationNo);
    }

    @Override
    public int updateStation(Station station) {
        return stationMapper.updateStation(station);
    }

    @Override
    public List<Station> listAllStation() {
        return stationMapper.listAllStation();
    }

    @Override
    public Station getByPhone(String phone) {
        return stationMapper.getByPhone(phone);
    }

    @Override
    public List<Station> listStationByProvinceAndCity(String province, String city) {
        return stationMapper.listByProvinceAndCity(province, city);
    }

    @Override
    public Station getStationByStaffOpenId(String openId) {
        return stationMapper.getByStaffOpenId(openId, String.valueOf(StaffStateEnum.NORMAL.getType()));
    }

    @Override
    public String getPhoneByStationNo(int stationNo) {
        return stationMapper.getPhoneByStationNo(stationNo);
    }

    @Override
    public Station getByAdminOpenId(String openId) {
        return stationMapper.getByAdminOpenId(openId, String.valueOf(StaffStateEnum.NORMAL.getType()));
    }

    @Override
    public Station getByUnionId(String unionId){
        return stationMapper.getByUnionId(unionId, String.valueOf(StaffStateEnum.NORMAL.getType()));
    }

    public Station getByOpenId(String openId){
        return stationMapper.getByOpenId(openId, String.valueOf(StaffStateEnum.NORMAL.getType()));
    }

    @Override
    public Station getByOrderNo(String orderNo) {
        return stationMapper.getByOrderNo(orderNo);
    }

    @Override
    public List<Station> getByTransportNo(int transportNo) {
        return stationMapper.getByTransportNo(transportNo);
    }

    @Override
    public List<Station> listAdminStation(Station station, int start, int limit) {
        return stationMapper.listAdminStation(station, start, limit);
    }

    @Override
    public int countAdminStation(Station station) {
        return stationMapper.countAdminStation(station);
    }

    @Override
    public int deleteStation(List<Station> stations) {
        return stationMapper.deleteStation(stations);
    }

    @Override
    public Station getByCustomerNo(String customerNo) {
        return stationMapper.getByCustomerNo(customerNo);
    }

    @Override
    public String addStationMedia(String stationNo, MultipartFile[] multipartFiles) throws IOException {
        String imgAddress = "";
        //上传收款二维码
        List<FileUpLoadState> fileUpLoadStates = uploadFile(multipartFiles);
        for (FileUpLoadState fileUpLoadState:fileUpLoadStates) {
            //把地址保存
            imgAddress = fileUpLoadState.getFileAddress();
            int i = stationMapper.uploadCollectionQRCode(imgAddress, Integer.valueOf(stationNo));
        }
        return imgAddress;
    }

    @Override
    public List<Station> listByPosition(double longitude, double latitude, int offset, int limit) {
        StationPosition position = getPosition(longitude, latitude, 20D, 20D);
        return stationMapper.listByPosition(position, 1, offset, limit);
    }

    @Override
    public List<Station> listGroupByCity() {
        return stationMapper.listGroupByCity();
    }

    @Override
    public List<Station> listByProvince(String province) {
        return stationMapper.listByProvince(province);
    }

    @Override
    public List<Station> listStationByCity(double lng, double lat, String distanceSort, String orderNum, String serviceEval, String city) {
        //如果是1升序，如果是2降序，其它默认不排序
        distanceSort = "1".equals(distanceSort)?"ASC":("2".equals(distanceSort)?"DESC":null);
        orderNum = "1".equals(orderNum)?"ASC":("2".equals(orderNum)?"DESC":null);
        serviceEval = "1".equals(serviceEval)?"ASC":("2".equals(serviceEval)?"DESC":null);
        List<Station> stations = stationMapper.listStationByCity(lng, lat, distanceSort, orderNum, serviceEval, city);
        return stations;
    }

    @Override
    public boolean stationBelongToCity(String city, String stationNo) {
        Station station = stationMapper.getStation(TypeConvertUtil.$int(stationNo));
        if(station != null&&station.getCity().equals(city)){
            return true;
        }
        return false;
    }

    @Override
    public List<String> listStartCity() {
        return stationMapper.listStartCity();
    }

    private StationPosition getPosition(double longitude, double latitude, double longitudeOffSet, double latitudeOffSet) {
        return new StationPosition(longitude, latitude, longitude - longitudeOffSet, longitude + longitudeOffSet, latitude - latitudeOffSet, latitude + latitudeOffSet);
    }

    /**
     * @return java.util.List<com.jxywkj.application.pet.common.utils.FileUpLoadState>
     * @Author LiuXiangLin
     * @Description 上传文件
     * @Date 10:34 2019/8/16
     * @Param [multipartFiles, orderNo]
     **/
    private List<FileUpLoadState> uploadFile(MultipartFile[] multipartFiles) throws IOException {
        List<FileUpLoadState> result = new ArrayList<>();
        MediaFileTypeEnum mediaFileTypeEnum;

        // 文件流判断
        if (multipartFiles != null && multipartFiles.length > 0) {
            for (MultipartFile multipartFile : multipartFiles) {
                // 获取该文件类型的枚举
                mediaFileTypeEnum = MediaFileTypeEnum.filter(fileUtils.getFileSuffix(multipartFile.getOriginalFilename()));
                // 如果为空则跳出该循环
                if (mediaFileTypeEnum == null) {
                    continue;
                }

                // 上传文件
                result.add(aliOssObjectUtils.uploadImg(multipartFile, mediaFileTypeEnum));
            }
        }
        return result;
    }
}
