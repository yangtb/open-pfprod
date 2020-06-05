package com.sm.pfprod.service.biz.disease;

import com.sm.pfprod.model.dto.biz.disease.PfDiseaseInfoDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCatalogueTreeDto;
import com.sm.pfprod.model.dto.common.PfChangeStatusDto;
import com.sm.pfprod.model.entity.BasDie;
import com.sm.pfprod.model.entity.BasDieClass;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.disease.PfDiseaseZtreeVo;

import java.util.List;

/**
 * @ClassName: PfDiseaseService
 * @Description: 疾病信息服务
 * @Author yangtongbin
 * @Date 2018/9/28
 */
public interface PfDiseaseService {


    /**
     * 疾病目录树
     *
     * @param dto
     * @return
     */
    List<PfDiseaseZtreeVo> listDiseaseCatalogueTree(PfCatalogueTreeDto dto);

    /**
     * 疾病目录信息
     *
     * @param idDieClass 疾病目录id
     * @return
     */
    BasDieClass selectDiseaseDetail(Long idDieClass);

    /**
     * 保存疾病目录
     *
     * @param dto
     * @return
     */
    Long saveDiseaseCatalogue(BasDieClass dto);

    /**
     * 删除疾病目录
     *
     * @param dto
     * @return
     */
    boolean delDiseaseCatalogue(PfChangeStatusDto dto);

    /**
     * 疾病信息列表
     *
     * @param dto
     * @return
     */
    PageResult listDiseaseInfo(PfDiseaseInfoDto dto);

    PageResult listIdeReason(PfDiseaseInfoDto dto);

    /**
     * 获取目录下所有疾病
     *
     * @param dto
     * @return
     */
    PageResult listDiseaseByCatalogueId(PfDiseaseInfoDto dto);


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
