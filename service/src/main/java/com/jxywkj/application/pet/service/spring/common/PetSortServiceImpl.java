package com.jxywkj.application.pet.service.spring.common;

import com.jxywkj.application.pet.dao.common.PetSortMapper;
import com.jxywkj.application.pet.model.common.PetSort;
import com.jxywkj.application.pet.service.facade.common.PetSortService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 宠物类别Service
 *
 * @Description
 * @Author Administrator
 * @Date 2019-07-12 22:32
 * @Version 1.0
 */
@Service
public class PetSortServiceImpl implements PetSortService {

    @Resource
    PetSortMapper petSortMapper;

    @Override
    public PetSort getPetType(String petTypeName) {
        return petSortMapper.getPetType(petTypeName);
    }

    @Override
    public List<String> listPetTypeName() {
        List<String> result = new ArrayList<>();
        List<PetSort> petSorts = petSortMapper.listPetType();
        for (PetSort petSort : petSorts) {
            result.add(petSort.getPetSortName());
        }
        return result;
    }

    @Override
    public List<PetSort> listAllType() {
        return petSortMapper.listAllType();
    }

    @Override
    public PetSort getByPetSortNo(Integer petSortNo) {
        return petSortMapper.getByPetSortNo(petSortNo);
    }
}
