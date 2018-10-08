package com.sm.pfprod.service.biz.exam;

import com.sm.pfprod.model.dto.biz.exam.PfExamQuestionDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.BasInspectCa;
import com.sm.pfprod.model.entity.BasInspectItem;
import com.sm.pfprod.model.entity.BasItemResult;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.PfCommonZtreeVo;

import java.util.List;

/**
 * @ClassName: PfCheckService
 * @Description: 检验题库
 * @Author yangtongbin
 * @Date 2018/10/7
 */
public interface PfExamService {

    /**
     * 检查题库分类树
     *
     * @return
     */
    List<PfCommonZtreeVo> listQuestionClassifyTree();

    /**
     * 新增题库信息
     *
     * @param dto
     * @return
     */
    Long addQuestionClassify(BasInspectCa dto);

    /**
     * 编辑题库信息
     *
     * @param dto
     * @return
     */
    boolean editQuestionClassify(BasInspectCa dto);

    /**
     * 删除题库信息
     *
     * @param dto
     * @return
     */
    boolean delQuestionClassify(PfBachChangeStatusDto dto);

    /**
     * 问题列表
     *
     * @param dto
     * @return
     */
    PageResult listQuestion(PfExamQuestionDto dto);

    /**
     * 新增问题
     *
     * @param dto
     * @return
     */
    boolean addQuestion(BasInspectItem dto);

    /**
     * 编辑问题
     *
     * @param dto
     * @return
     */
    boolean editQuestion(BasInspectItem dto);

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
    PageResult listAnswer(PfExamQuestionDto dto);

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
    Long saveAnswer(BasItemResult dto);
}
