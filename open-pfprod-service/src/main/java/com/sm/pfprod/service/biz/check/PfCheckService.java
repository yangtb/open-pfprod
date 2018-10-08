package com.sm.pfprod.service.biz.check;

import com.sm.pfprod.model.dto.biz.check.PfCheckQuestionDto;
import com.sm.pfprod.model.dto.biz.inquisition.PfInquisitionQuestionDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.BasBody;
import com.sm.pfprod.model.entity.BasBodyCa;
import com.sm.pfprod.model.entity.BasBodyResult;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.PfCommonZtreeVo;

import java.util.List;

/**
 * @ClassName: PfCheckService
 * @Description: 检查题库
 * @Author yangtongbin
 * @Date 2018/10/7
 */
public interface PfCheckService {

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
    Long addQuestionClassify(BasBodyCa dto);

    /**
     * 编辑题库信息
     *
     * @param dto
     * @return
     */
    boolean editQuestionClassify(BasBodyCa dto);

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
    PageResult listQuestion(PfCheckQuestionDto dto);

    /**
     * 新增问题
     *
     * @param dto
     * @return
     */
    boolean addQuestion(BasBody dto);

    /**
     * 编辑问题
     *
     * @param dto
     * @return
     */
    boolean editQuestion(BasBody dto);

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
    PageResult listAnswer(PfCheckQuestionDto dto);

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
    Long saveAnswer(BasBodyResult dto);
}
