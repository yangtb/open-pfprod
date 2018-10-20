package com.sm.pfprod.service.biz.inquisition;

import com.sm.pfprod.model.dto.biz.inquisition.PfInquisitionQuestionDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCommonSearchDto;
import com.sm.pfprod.model.entity.BasInques;
import com.sm.pfprod.model.entity.BasInquesAnswer;
import com.sm.pfprod.model.entity.BasInquesCa;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.PfCommonZtreeVo;

import java.util.List;

/**
 * @ClassName: PfInquisitionService
 * @Description: 问诊相关
 * @Author yangtongbin
 * @Date 2018/10/3
 */
public interface PfInquisitionService {

    /**
     * 问诊题库分类树
     *
     * @return
     */
    List<PfCommonZtreeVo> listQuestionClassifyTree();

    /**
     * 新增问诊题库信息
     *
     * @param dto
     * @return
     */
    Long addQuestionClassify(BasInquesCa dto);

    /**
     * 编辑问诊题库信息
     *
     * @param dto
     * @return
     */
    boolean editQuestionClassify(BasInquesCa dto);

    /**
     * 删除问诊题库信息
     *
     * @param dto
     * @return
     */
    boolean delQuestionClassify(PfBachChangeStatusDto dto);

    /**
     * 问诊问题列表
     *
     * @param dto
     * @return
     */
    PageResult listQuestion(PfInquisitionQuestionDto dto);

    /**
     * 新增问题
     *
     * @param dto
     * @return
     */
    boolean addQuestion(BasInques dto);

    /**
     * 编辑问题
     *
     * @param dto
     * @return
     */
    boolean editQuestion(BasInques dto);

    /**
     * 删除问题
     *
     * @param dto
     * @return
     */
    boolean delQuestion(PfBachChangeStatusDto dto);

    /**
     * 问题答案列表
     *
     * @param dto
     * @return
     */
    PageResult listAnswer(PfInquisitionQuestionDto dto);

    /**
     * 删除答案信息
     *
     * @param dto
     * @return
     */
    boolean delAnswer(PfBachChangeStatusDto dto);

    /**
     * 保存答案信息
     *
     * @param dto
     * @return
     */
    Long saveAnswer(BasInquesAnswer dto);

    /**
     * 问诊问题列表搜索
     *
     * @param dto
     * @return
     */
    PageResult searchQuestion(PfCommonSearchDto dto);
}
