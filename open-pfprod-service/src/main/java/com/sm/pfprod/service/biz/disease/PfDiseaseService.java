package com.sm.pfprod.service.biz.disease;

import com.sm.pfprod.model.dto.biz.disease.PfDiseaseInfoDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.BasDie;
import com.sm.pfprod.model.result.PageResult;

/**
 * @ClassName: PfDiseaseService
 * @Description: 疾病信息服务
 * @Author yangtongbin
 * @Date 2018/9/28
 */
public interface PfDiseaseService {

    /**
     * 疾病信息列表
     *
     * @param dto
     * @return
     */
    PageResult listDiseaseInfo(PfDiseaseInfoDto dto);


    /**
     * 新增疾病信息
     *
     * @param dto
     * @return
     */
    boolean addDiseaseInfo(BasDie dto);

    /**
     * 编辑疾病信息
     *
     * @param dto
     * @return
     */
    boolean editDiseaseInfo(BasDie dto);

    /**
     * 删除疾病信息
     *
     * @param dto
     * @return
     */
    boolean delDiseaseInfo(PfBachChangeStatusDto dto);
}
