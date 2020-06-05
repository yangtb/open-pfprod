package com.sm.pfprod.model.vo.biz.test;

import com.sm.pfprod.model.entity.FaqMedCasePatient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfWaitingRoomPatVo extends FaqMedCasePatient implements Serializable {

    /**
     * 性别str
     */
    private String sexStr;

    /**
     * 照片_正面
     */
    private String frontPhotoUrl;

    /**
     * 照片_背面
     */
    private String backPhotoUrl;

}
