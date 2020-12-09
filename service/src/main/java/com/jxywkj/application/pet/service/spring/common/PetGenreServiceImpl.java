package com.jxywkj.application.pet.service.spring.common;

import com.jxywkj.application.pet.dao.common.PetGenreMapper;
import com.jxywkj.application.pet.model.common.PetGenre;
import com.jxywkj.application.pet.model.common.PetSort;
import com.jxywkj.application.pet.service.facade.common.PetGenreService;
import com.jxywkj.application.pet.service.facade.common.PetSortService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ 宠物类型Service
 * @Description
 * @Author Administrator
 * @Date 2019-07-12 22:34
 * @Version 1.0
 */
@Service
public class PetGenreServiceImpl implements PetGenreService {

    @Resource
    PetGenreMapper petGenreMapper;

    @Resource
    PetSortService petSortService;

    @Override
    public List<PetGenre> listAll() {
        return petGenreMapper.listAll();
    }

    @Override
    public List<PetGenre> listPetClassifyByTypeName(String petTypeName) {
        return petGenreMapper.listPetClassifyByTypeName(petTypeName);
    }

    @Override
    public PetGenre getByClassifyName(String classifyName) {
        return petGenreMapper.getByClassifyName(classifyName);
    }

    @Override
    public List<PetGenre> listByKeyWord(String keyWord) {
        List<PetGenre> petGenres = petGenreMapper.listByKeyWord(keyWord);
        for (PetGenre petGenre : petGenres){
            if(petGenre.getPetSort() != null){
                PetSort petSort = petSortService.getByPetSortNo(petGenre.getPetSort().getPetSortNo());
                petGenre.setPetSort(petSort);
            }
        }
        return petGenres;
    }
}
