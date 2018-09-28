package com.sm.pfprod.service.biz.disease;

import com.sm.pfprod.model.dto.biz.disease.PfDiseaseInfoDto;
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


}
