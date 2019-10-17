package com.sm.pfprod.web.rest.biz.tests;

import com.alibaba.fastjson.JSON;
import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.biz.exam.PfExmMedResultDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestEvaDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestExamTagDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCatalogueTreeDto;
import com.sm.pfprod.model.dto.common.PfChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCommonListDto;
import com.sm.pfprod.model.entity.*;
import com.sm.pfprod.model.enums.PfSimulateCaseTypeEnum;
import com.sm.pfprod.model.enums.SysDicGroupEnum;
import com.sm.pfprod.model.enums.SysParamEnum;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.PfOrgChartVo;
import com.sm.pfprod.service.biz.tests.PfTestWaitingRoomService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.security.CurrentUserUtils;
import com.sm.pfprod.web.util.EnumUtil;
import com.sm.pfprod.web.util.ParamUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: PfTestWaitingRoomController
 * @Description: 候诊室
 * @Author yangtongbin
 * @Date 2018/11/4
 */
@RestController
@RequestMapping(value = "/pf/r/waiting/room")
public class PfTestWaitingRoomRestController extends BaseController {

    @Resource
    private PfTestWaitingRoomService pfTestWaitingRoomService;

    @Resource
    private EnumUtil enumUtil;

    @Resource
    private ParamUtil paramUtil;

    /**
     * 开始执行
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/start")
    @ResponseBody
    public ResultObject startExam(@RequestBody ExmTestexec dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestplanDetail() != null, "idTestplanDetail");
        Assert.isTrue(dto.getIdMedicalrec() != null, "idMedicalrec");
        Assert.isTrue(StringUtils.isNotBlank(dto.getSdTestexec()), "sdTestexec");
        Assert.isTrue(dto.getIdStudent() != null, "idStudent");
        //dto.setIdStudent(CurrentUserUtils.getCurrentUserId());
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        return ResultObject.createSuccess("startExam", ResultObject.DATA_TYPE_OBJECT, pfTestWaitingRoomService.startExam(dto));
    }

    /**
     * 执行完成
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/end")
    @ResponseBody
    public ResultObject endExam(@RequestBody ExmTestexec dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexec() != null, "idTestexec");
        Assert.isTrue(dto.getIdTestplanDetail() != null, "idTestplanDetail");

        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        return ResultObject.createSuccess("startExam", ResultObject.DATA_TYPE_OBJECT, pfTestWaitingRoomService.endExam(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/test/cons/list")
    @ResponseBody
    public Object listTestCons(@RequestBody PfTestExamTagDto dto) {
        dto.setPage(1);
        dto.setLimit(100);
        PageResult pageResult = pfTestWaitingRoomService.listTestCons(dto);
        return pageResult.getData();
    }

    /**
     * 保存问诊问题
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/cons/qa/save")
    @ResponseBody
    public ResultObject saveConsQa(@RequestBody ExmMedResultInques dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        Assert.isTrue(dto.getIdInques() != null, "idInques");
        Assert.isTrue(dto.getIdMedCaseList() != null, "idMedCaseList");

        return ResultObject.createSuccess("saveConsQa", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.saveConsQa(dto));
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/qa/edit")
    @ResponseBody
    public ResultObject editConsQa(@RequestBody PfExmMedResultDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getId() != null, "id");

        return ResultObject.createSuccess("editConsQa", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.editConsQa(dto));
    }

    /**
     * 问诊-线索标志
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/cons/qa/status")
    @ResponseBody
    public ResultObject updateConsStatus(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfTestWaitingRoomService.updateConsStatus(dto) ? ResultObject.createSuccess("updateConsStatus", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("updateConsStatus", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 问诊-问答列表
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/cons/qa/list")
    @ResponseBody
    public ResultObject listConsQa(@RequestBody PfTestExamTagDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        return ResultObject.createSuccess("listConsQa", ResultObject.DATA_TYPE_LIST,
                pfTestWaitingRoomService.listConsQa(dto));
    }

    /**
     * 获取检查图片
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030', 'ROLE_SUPER')")
    @PostMapping(value = "/check/qa/pic")
    public ResultObject selectPic(@RequestBody PfTestExamTagDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedicalrec() != null, "idMedicalrec");
        Assert.isTrue(StringUtils.isNotBlank(dto.getCdMedAsse()), "cdMedAsse");
        return ResultObject.createSuccess("selectPic", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.selectPic(dto));
    }

    /**
     * 检查 - 问题
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/check/qa/save")
    @ResponseBody
    public ResultObject saveCheckQa(@RequestBody ExmMedResultBody dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        Assert.isTrue(dto.getIdBody() != null, "idBody");
        Assert.isTrue(dto.getIdMedCaseList() != null, "idMedCaseList");

        return ResultObject.createSuccess("saveCheckQa", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.saveCheckQa(dto));
    }

    /**
     * 检查 - 编辑问题
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/check/qa/edit")
    @ResponseBody
    public ResultObject editCheckQa(@RequestBody ExmMedResultBody dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        Assert.isTrue(dto.getIdMedCaseList() != null, "idMedCaseList");

        return ResultObject.createSuccess("editCheckQa", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.editCheckQa(dto));
    }

    /**
     * 检查-线索标志
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/check/qa/status")
    @ResponseBody
    public ResultObject updateCheckStatus(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfTestWaitingRoomService.updateCheckStatus(dto) ? ResultObject.createSuccess("updateCheckStatus", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("updateCheckStatus", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 检查-问答列表
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/check/qa/list")
    @ResponseBody
    public ResultObject listCheckQa(@RequestBody PfTestExamTagDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        return ResultObject.createSuccess("listCheckQa", ResultObject.DATA_TYPE_LIST,
                pfTestWaitingRoomService.listCheckQa(dto));
    }

    /**
     * 检验 - 问题
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/exam/qa/save")
    @ResponseBody
    public ResultObject saveExamQa(@RequestBody ExmMedResultInspect dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        Assert.isTrue(dto.getIdMedCaseList() != null, "idMedCaseList");

        return ResultObject.createSuccess("saveExamQa", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.saveExamQa(dto));
    }

    /**
     * 检验 - 问题 - 批量处理
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/exam/qa/batch/save")
    @ResponseBody
    public ResultObject saveBatchExamQa(@RequestBody PfTestExamTagDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");

        return ResultObject.createSuccess("saveBatchExamQa", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.saveBatchExamQa(dto));
    }

    /**
     * 检验 - 编辑问题
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/exam/qa/edit")
    @ResponseBody
    public ResultObject editExamQa(@RequestBody ExmMedResultInspect dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        Assert.isTrue(dto.getIdMedCaseList() != null, "idMedCaseList");
        return ResultObject.createSuccess("editExamQa", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.editExamQa(dto));
    }

    /**
     * 检验-线索标志
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/exam/qa/status")
    @ResponseBody
    public ResultObject updateExamStatus(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfTestWaitingRoomService.updateExamStatus(dto) ? ResultObject.createSuccess("updateExamStatus", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("updateExamStatus", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 检验-问答列表
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/exam/qa/list")
    @ResponseBody
    public ResultObject listExamQa(@RequestBody PfTestExamTagDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        return ResultObject.createSuccess("listExamQa", ResultObject.DATA_TYPE_LIST,
                pfTestWaitingRoomService.listExamQa(dto));
    }

    /**
     * 拟诊 - 保存
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/referral/save")
    @ResponseBody
    public ResultObject saveReferral(@RequestBody ExmMedResultReferral dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        Assert.isTrue(dto.getIdDie() != null, "id_die");

        return ResultObject.createSuccess("saveReferral", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.saveReferral(dto));
    }

    /**
     * 拟诊 - 排除
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/referral/out")
    @ResponseBody
    public ResultObject outReferral(@RequestBody ExmMedResultReferral dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResultReferral() != null, "idTestexecResultReferral");
        //Assert.isTrue(StringUtils.isNotBlank(dto.getReasonOut()), "reasonOut");

        return ResultObject.createSuccess("outReferral", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.saveReferral(dto));
    }

    /**
     * 拟诊-列表
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/referral/list")
    @ResponseBody
    public ResultObject listReferral(@RequestBody PfTestExamTagDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        return ResultObject.createSuccess("listReferral", ResultObject.DATA_TYPE_LIST,
                pfTestWaitingRoomService.listReferral(dto));
    }

    /**
     * 医嘱 - 查询
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/orders/select")
    @ResponseBody
    public ResultObject selectOrders(@RequestBody ExmMedResultOrder dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        return ResultObject.createSuccess("selectOrders", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.selectOrders(dto.getIdTestexecResult()));
    }

    /**
     * 医嘱-保存
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/orders/save")
    @ResponseBody
    public ResultObject saveOrders(@RequestBody ExmMedResultOrder dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        return ResultObject.createSuccess("saveOrders", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.saveOrder(dto));
    }

    /**
     * 医嘱-保存药品
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/orders/drugs/save")
    @ResponseBody
    public ResultObject saveDrugs(@RequestBody PfCommonListDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        Assert.isTrue(dto.getExtId() != null, "extId");
        Assert.isTrue(StringUtils.isNotBlank(dto.getExtType()), "extType");
        return ResultObject.createSuccess("saveDrugs", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.saveDrugs(dto));
    }

    /**
     * 医嘱-删除用药
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/orders/drugs/del")
    @ResponseBody
    public ResultObject delDrugs(@RequestBody PfChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getId() != null, "id");
        Assert.isTrue(StringUtils.isNotBlank(dto.getType()), "type");
        return ResultObject.createSuccess("delDrugs", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.delDrugs(dto.getType(), dto.getId()));
    }

    /**
     * 保存诊断
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/summary/diagnosis/save")
    @ResponseBody
    public ResultObject saveDiagnosis(@RequestBody ExmMedResultDiagnosis dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        Assert.isTrue(dto.getIdDie() != null, "idDie");
        return ResultObject.createSuccess("saveDiagnosis", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.saveDiagnosis(dto));
    }


    /**
     * 保存鉴别诊断
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/summary/diagnosis/identify/save")
    @ResponseBody
    public ResultObject saveIdentifyDiagnosis(@RequestBody ExmMedResultIdentify dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        Assert.isTrue(StringUtils.isNotBlank(dto.getNaDie()), "naDie");
        Assert.isTrue(StringUtils.isNotBlank(dto.getDesDieReason()), "desDieReason");
        return ResultObject.createSuccess("saveDiagnosis", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.saveIdentifyDiagnosis(dto));
    }

    /**
     * 删除诊断
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/summary/diagnosis/del")
    @ResponseBody
    public ResultObject delDiagnosis(@RequestBody ExmMedResultDiagnosis dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResultDiagnosis() != null, "idTestexecResultDiagnosis");
        return ResultObject.createSuccess("delDiagnosis", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.delDiagnosis(dto.getIdTestexecResultDiagnosis()));
    }

    /**
     * 保存诊断小结
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/summary/save")
    @ResponseBody
    public ResultObject saveSummary(@RequestBody ExmMedResultSummary dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        return ResultObject.createSuccess("saveSummary", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.saveSummary(dto));
    }

    /**
     * 保存确诊理由
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/summary/die/reason/save")
    @ResponseBody
    public ResultObject saveDieReason(@RequestBody List<ExmMedResultDieReason> dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto), "dto");
        return ResultObject.createSuccess("saveDieReason", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.saveDieReason(dto));
    }

    /**
     * 删除确诊理由
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/summary/reason/del")
    @ResponseBody
    public ResultObject delDieReason(@RequestBody ExmMedResultDieReason dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdDieReason() != null, "idDieReason");
        return ResultObject.createSuccess("delDieReason", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.delDieReason(dto.getIdDieReason()));
    }

    /**
     * 查询诊断、确诊理由
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/diagnosis/select")
    @ResponseBody
    public ResultObject selectAllDiagnosis(@RequestBody PfTestExamTagDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        return ResultObject.createSuccess("selectAllDiagnosis", ResultObject.DATA_TYPE_LIST,
                pfTestWaitingRoomService.selectAllDiagnosis(dto.getIdTestexecResult()));
    }

    /**
     * 查询拟诊
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/referral/select/all")
    @ResponseBody
    public ResultObject selectAllReferral(@RequestBody PfTestExamTagDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        return ResultObject.createSuccess("selectAllReferral", ResultObject.DATA_TYPE_LIST,
                pfTestWaitingRoomService.selectAllReferral(dto.getIdTestexecResult()));
    }

    /**
     * 查询诊断、诊断小结
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/summary/diagnosis/select")
    @ResponseBody
    public ResultObject selectDiagnosis(@RequestBody ExmMedResultDiagnosis dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        //Assert.isTrue(dto.getIdTestexecResultReferral() != null, "idTestexecResultReferral");
        if (dto.getIdTestexecResultReferral() == null) {
            throw new BizRuntimeException(ErrorCode.ERROR_GENERAL_110001, "请先填写拟诊");
        }
        Assert.isTrue(dto.getIdDie() != null, "idDie");
        Assert.isTrue(dto.getFgDieClass() != null, "fgDieClass");
        return ResultObject.createSuccess("selectDiagnosis", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.selectDiagnosis(dto));
    }

    /**
     * 查询诊断小结
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/summary/select")
    @ResponseBody
    public ResultObject selectSummary(@RequestBody PfTestExamTagDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        return ResultObject.createSuccess("selectSummary", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.selectSummary(dto.getIdTestexecResult()));
    }

    /**
     * 查询病例评估平均得分
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0040','ROLE_SUPER')")
    @PostMapping(value = "/eva/avg/score/select")
    @ResponseBody
    public ResultObject selectAvgScore(@RequestBody PfTestEvaDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        Assert.isTrue(dto.getIdMedicalrec() != null, "idMedicalrec");
        return ResultObject.createSuccess("selectAvgScore", ResultObject.DATA_TYPE_LIST,
                pfTestWaitingRoomService.selectScore(dto.getIdTestexecResult(), dto.getIdMedicalrec()));
    }

    /**
     * 查询病例评估
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0040','ROLE_SUPER')")
    @PostMapping(value = "/eva/list")
    @ResponseBody
    public ResultObject listEva(@RequestBody PfTestEvaDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        return ResultObject.createSuccess("listEva", ResultObject.DATA_TYPE_LIST,
                pfTestWaitingRoomService.listEva(dto));
    }

    /**
     * 查询病例评估日志
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0040','ROLE_SUPER')")
    @PostMapping(value = "/eva/log/list")
    @ResponseBody
    public ResultObject listEvaLog(@RequestBody PfTestEvaDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        return ResultObject.createSuccess("listEvaLog", ResultObject.DATA_TYPE_LIST,
                pfTestWaitingRoomService.listEvaLog(dto.getIdTestexecResult()));
    }

    /**
     * 病例评估
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/med/eva")
    @ResponseBody
    public ResultObject medEva(@RequestBody PfTestEvaDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        return ResultObject.createSuccess("medEva", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.medEva(dto.getIdTestexecResult()));
    }

    /**
     * 修改得分
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/eva/edit")
    @ResponseBody
    public ResultObject editEva(@RequestBody ExmEvaDimension dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResultDimension() != null, "idTestexecResultDimension");
        Assert.isTrue(dto.getScoreDimemsion() != null, "scoreDimemsion");
        return ResultObject.createSuccess("editEva", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.editEva(dto));
    }

    /**
     * 查询病例执行日志
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0040','ROLE_SUPER')")
    @PostMapping(value = "/exec/log/list")
    @ResponseBody
    public ResultObject listExecLog(@RequestBody PfTestEvaDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        return ResultObject.createSuccess("listExecLog", ResultObject.DATA_TYPE_LIST,
                pfTestWaitingRoomService.listExecLog(dto.getIdTestexecResult()));
    }

    /**
     * 查询病例评估结果
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0040','ROLE_SUPER')")
    @PostMapping(value = "/eva/result/select")
    @ResponseBody
    public ResultObject selectEvaResult(@RequestBody PfTestEvaDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        ExmEvaResult exmEvaResult = pfTestWaitingRoomService.selectEvaResult(dto.getIdTestexecResult());
        if (exmEvaResult != null) {
            exmEvaResult.setSdTitleDic(enumUtil.getEnumTxt(SysDicGroupEnum.EXM_EVAR_ESULT.getCode(), exmEvaResult.getSdTitle()));
        }
        return ResultObject.createSuccess("listExecLog", ResultObject.DATA_TYPE_OBJECT, exmEvaResult);
    }

    /**
     * 拟诊原因 - 保存
     *
     * @param list
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/referral/reason/save")
    @ResponseBody
    public ResultObject saveReferralReason(@RequestBody List<ExmMedResultReferralReason> list) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(list), "list");
        return ResultObject.createSuccess("saveReferralReason", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.saveReferralReason(list));
    }

    /**
     * 删除计划详情
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030_CANCEL','ROLE_SUPER')")
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delPlanDetail(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        return ResultObject.createSuccess("delPlanDetail", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.delPlanDetail(dto));
    }

    /**
     * 模拟就诊评估参数获取
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @PostMapping(value = "/case/simulate")
    @ResponseBody
    public ResultObject simulateCase() {
        String simulateCaseType = paramUtil.getParamValue(SysParamEnum.SIMULATE_CASE_TYPE.getCode());
        if (StringUtils.isBlank(simulateCaseType) || simulateCaseType.equals(PfSimulateCaseTypeEnum.CLOSE.getCode())) {
            throw new BizRuntimeException(ErrorCode.COMMON_TIPS_CODE, "暂未开放");
        }

        return ResultObject.createSuccess("simulateCase", ResultObject.DATA_TYPE_OBJECT, null);
    }


    /**
     * 保存执行序号
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030', 'ROLE_EXM0040','ROLE_SUPER')")
    @PostMapping(value = "/exec/serial/save")
    @ResponseBody
    public ResultObject saveExecSerialNo(@RequestBody ExmTestexec dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexec() != null, "idTestexec");
        Assert.isTrue(dto.getCurSerialno() != null, "curSerialno");
        return ResultObject.createSuccess("saveExecSerialNo", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.saveExecSerialNo(dto));
    }

    /**
     * 确诊项 及 排除拟诊项
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0040','ROLE_SUPER')")
    @PostMapping(value = "/eva/diagnostic/analysis/list")
    @ResponseBody
    public ResultObject listDiagnosticAnalysis(@RequestBody PfTestEvaDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");

        return ResultObject.createSuccess("listDiagnosticAnalysis", ResultObject.DATA_TYPE_LIST,
                pfTestWaitingRoomService.listDiagnosticAnalysis(dto));
    }

    /**
     * 查询病例诊断分析详情
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0040','ROLE_SUPER')")
    @PostMapping(value = "/eva/diagnostic/analysis/list/detail")
    @ResponseBody
    public ResultObject listDiagnosticAnalysisDetail(@RequestBody PfTestEvaDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedicalrec() != null, "idMedicalrec");
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        Assert.isTrue(StringUtils.isNotBlank(dto.getIdDieStr()), "idDieStr");

        return ResultObject.createSuccess("listDiagnosticAnalysisDetail", ResultObject.DATA_TYPE_LIST,
                pfTestWaitingRoomService.listDiagnosticAnalysisDetail(dto));
    }

    /**
     * 拟诊疾病目录树
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_EXM0040', 'ROLE_SUPER')")
    @RequestMapping(value = "/referral/catalogue/tree", method = RequestMethod.POST)
    @ResponseBody
    public Object listDiseaseCatalogueTree(@RequestBody PfCatalogueTreeDto dto) {
        return pfTestWaitingRoomService.listDiseaseCatalogueTree(dto);
    }

    /**
     * 思维导图
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_EXM0040', 'ROLE_SUPER')")
    @RequestMapping(value = "/referral/chart/data", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject selectReferralChartData(@RequestBody PfTestEvaDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestexecResult() != null, "idTestexecResult");
        Assert.isTrue(dto.getChartType() != null, "chartType");

        String orgChartStr = pfTestWaitingRoomService.selectReferralChartData(dto);
        PfOrgChartVo pfOrgChartVo = JSON.parseObject(orgChartStr, PfOrgChartVo.class);

        return ResultObject.createSuccess("selectReferralChartData", ResultObject.DATA_TYPE_OBJECT, pfOrgChartVo);
    }


    /**
     * 考试完成后所需跳转信息
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_EXM0040', 'ROLE_SUPER')")
    @RequestMapping(value = "/exam/finish/select", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject selectFinishExamInfo(@RequestBody ExmTestexec dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestplanDetail() != null, "idTestplanDetail");

        return ResultObject.createSuccess("selectFinishExamInfo", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.selectFinishExamInfo(dto.getIdTestplanDetail()));
    }

    /**
     * 患者页签idMedCase
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_EXM0040', 'ROLE_SUPER')")
    @RequestMapping(value = "/assess/pat/idMedCase", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject selectAssessPatIdMedCase(@RequestBody ExmTestexec dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestplanDetail() != null, "idTestplanDetail");

        return ResultObject.createSuccess("selectAssessPatIdMedCase", ResultObject.DATA_TYPE_OBJECT,
                pfTestWaitingRoomService.selectAssessPatIdMedCase(dto.getIdTestplanDetail()));
    }

    /**
     * 查询病例评估指南
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0040','ROLE_SUPER')")
    @PostMapping(value = "/eva/guide/content")
    @ResponseBody
    public ResultObject selectEvaGuideContent(@RequestBody ExmTestexec dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestplanDetail() != null, "idTestplanDetail");
        return ResultObject.createSuccess("selectEvaGuideContent", ResultObject.DATA_TYPE_LIST,
                pfTestWaitingRoomService.selectEvaGuideContent(dto.getIdTestplanDetail()));
    }

}

