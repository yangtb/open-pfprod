package com.sm.pfprod.service.biz.tests;

import com.sm.pfprod.model.dto.biz.tests.PfTestExamDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestExamTagDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestWatingRoomDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.*;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.test.*;
import com.sm.pfprod.model.vo.biz.test.paper.PfTestPaperVo;

import java.util.List;

/**
 * @ClassName: PfTestWaitingRoomService
 * @Description: 候诊室service
 * @Author yangtongbin
 * @Date 2018/11/10
 */
public interface PfTestWaitingRoomService {

    /**
     * 候诊室列表
     *
     * @param dto
     * @return
     */
    PageResult listWaitingRoom(PfTestWatingRoomDto dto);

    /**
     * 接诊列表
     *
     * @param dto
     * @return
     */
    PageResult listReceivePat(PfTestWatingRoomDto dto);

    /**
     * 获取试卷信息
     *
     * @param dto
     * @return
     */
    PfTestPaperVo selectTestPaperInfo(PfTestExamDto dto);

    /**
     * 开始考试
     *
     * @param dto
     * @return
     */
    PfWaitingRoomStartVo startExam(ExmTestexec dto);

    /**
     * 交卷
     *
     * @param dto
     * @return
     */
    boolean endExam(ExmTestexec dto);

    /**
     * 查询患者信息
     *
     * @param dto
     * @return
     */
    PfWaitingRoomPatVo selectPatInfo(PfTestExamTagDto dto);

    /**
     * 查询问诊列表
     *
     * @param dto
     * @return
     */
    PageResult listTestCons(PfTestExamTagDto dto);

    /**
     * 问诊 - 保存问答问题
     *
     * @param dto
     * @return
     */
    Long saveConsQa(ExmMedResultInques dto);

    /**
     * 问诊 - 线索标志
     *
     * @param dto
     * @return
     */
    boolean updateConsStatus(PfBachChangeStatusDto dto);

    /**
     * 问诊 - qa列表
     *
     * @param dto
     * @return
     */
    List<PfWaitingRoomConsVo> listConsQa(PfTestExamTagDto dto);

    /**
     * 查询检查图片
     *
     * @param dto
     * @return
     */
    FaqMedCaseBody selectPic(PfTestExamTagDto dto);


    /**
     * 检查 - 列表
     *
     * @param dto
     * @return
     */
    PageResult listTestCheck(PfTestExamTagDto dto);

    /**
     * 检查 - 保存问答问题
     *
     * @param dto
     * @return
     */
    Long saveCheckQa(ExmMedResultBody dto);

    /**
     * 检查 - 线索标志
     *
     * @param dto
     * @return
     */
    boolean updateCheckStatus(PfBachChangeStatusDto dto);

    /**
     * 检查 - qa列表
     *
     * @param dto
     * @return
     */
    List<PfWaitingRoomCheckVo> listCheckQa(PfTestExamTagDto dto);

    /**
     * 检验 - 列表
     *
     * @param dto
     * @return
     */
    PageResult listTestExam(PfTestExamTagDto dto);

    /**
     * 检验 - 保存问答问题
     *
     * @param dto
     * @return
     */
    Long saveExamQa(ExmMedResultInspect dto);

    /**
     * 检验 - 线索标志
     *
     * @param dto
     * @return
     */
    boolean updateExamStatus(PfBachChangeStatusDto dto);

    /**
     * 检验 - qa列表
     *
     * @param dto
     * @return
     */
    List<PfWaitingRoomExamVo> listExamQa(PfTestExamTagDto dto);
}
