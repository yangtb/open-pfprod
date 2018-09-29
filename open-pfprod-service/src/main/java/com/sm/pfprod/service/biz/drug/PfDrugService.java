package com.sm.pfprod.service.biz.drug;

import com.sm.pfprod.model.dto.biz.drug.PfDrugInfoDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.BasDrugs;
import com.sm.pfprod.model.result.PageResult;

/**
 * @ClassName: PfDiseaseService
 * @Description: 药品信息服务
 * @Author yangtongbin
 * @Date 2018/9/28
 */
public interface PfDrugService {

    /**
     * 药品信息列表
     *
     * @param dto
     * @return
     */
    PageResult listDrugInfo(PfDrugInfoDto dto);

    /**
     * 新增药品信息
     *
     * @param dto
     * @return
     */
    boolean addDrugInfo(BasDrugs dto);

    /**
     * 编辑药品信息
     *
     * @param dto
     * @return
     */
    boolean editDrugInfo(BasDrugs dto);

    /**
     * 删除药品信息
     *
     * @param dto
     * @return
     */
    boolean delDrugInfo(PfBachChangeStatusDto dto);
}
