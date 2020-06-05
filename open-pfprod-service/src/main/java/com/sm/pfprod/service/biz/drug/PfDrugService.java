package com.sm.pfprod.service.biz.drug;

import com.sm.pfprod.model.dto.biz.drug.PfDrugInfoDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCatalogueTreeDto;
import com.sm.pfprod.model.dto.common.PfChangeStatusDto;
import com.sm.pfprod.model.entity.BasDrugs;
import com.sm.pfprod.model.entity.BasDrugsClass;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.drug.PfDrugZtreeVo;

import java.util.List;

/**
 * @ClassName: PfDrugService
 * @Description: 药品信息服务
 * @Author yangtongbin
 * @Date 2018/9/28
 */
public interface PfDrugService {

    /**
     * 药品目录树
     *
     * @param dto
     * @return
     */
    List<PfDrugZtreeVo> listDrugCatalogueTree(PfCatalogueTreeDto dto);

    /**
     * 药品目录信息
     *
     * @param idDrugsclass 药品目录id
     * @return
     */
    BasDrugsClass selectDrugDetail(Long idDrugsclass);

    /**
     * 保存药品目录
     *
     * @param dto
     * @return
     */
    Long saveDrugCatalogue(BasDrugsClass dto);

    /**
     * 删除药品目录
     *
     * @param dto
     * @return
     */
    boolean delDrugCatalogue(PfChangeStatusDto dto);


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
