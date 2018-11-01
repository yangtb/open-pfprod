package com.sm.pfprod.service.biz.tests;

import com.sm.pfprod.model.dto.biz.tests.PfAddCaseDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestPaperDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCatalogueTreeDto;
import com.sm.pfprod.model.entity.ExmTestpaper;
import com.sm.pfprod.model.entity.ExmTestpaperCa;
import com.sm.pfprod.model.entity.ExmTestpaperMedicalrec;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.PfCommonZtreeVo;

import java.util.List;

/**
 * @ClassName: PfCheckService
 * @Description: 检查Paper
 * @Author yangtongbin
 * @Date 2018/10/7
 */
public interface PfTestPaperService {

    /**
     * 检查Paper分类树
     *
     * @param idOrg 机构ID
     * @return
     */
    List<PfCommonZtreeVo> listPaperClassifyTree(Long idOrg);

    /**
     * 保存Paper信息
     *
     * @param dto
     * @return
     */
    Long savePaperClassify(ExmTestpaperCa dto);

    /**
     * 删除Paper信息
     *
     * @param dto
     * @return
     */
    boolean delPaperClassify(PfBachChangeStatusDto dto);

    /**
     * 试卷列表
     *
     * @param dto
     * @return
     */
    PageResult listPaper(PfTestPaperDto dto);

    /**
     * 新增试卷信息
     *
     * @param dto
     * @return
     */
    Long savePaper(ExmTestpaper dto);

    /**
     * 删除试卷信息
     *
     * @param dto
     * @return
     */
    boolean delPaper(PfBachChangeStatusDto dto);

    /**
     * 病例tree
     *
     * @param dto
     * @return
     */
    List<PfCommonZtreeVo> listCaseTree(PfCatalogueTreeDto dto);

    /**
     * 试题清单列表
     *
     * @param dto
     * @return
     */
    PageResult listPaperItem(PfTestPaperDto dto);

    /**
     * 添加试题清单
     *
     * @param dto
     * @return
     */
    boolean addPaperItem(PfAddCaseDto dto);

    /**
     * 删除试题清单
     *
     * @param dto
     * @return
     */
    boolean delPaperItem(PfBachChangeStatusDto dto);

    /**
     * 更新排序
     *
     * @param dto
     * @return
     */
    boolean updatePaperItemSort(ExmTestpaperMedicalrec dto);

}
