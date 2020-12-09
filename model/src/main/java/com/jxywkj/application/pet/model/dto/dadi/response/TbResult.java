package com.jxywkj.application.pet.model.dto.dadi.response;

import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className TbResult
 * @date 2020/8/13 17:16
 **/
@Data
public class TbResult {
    String policyNo;
    String operateSuccess;
    String operateMessage;
    String errorCode;
    String importSN;
    String premium;
}
