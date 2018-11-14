package com.sm.pfprod.web.rest.biz.tests;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.biz.tests.PfTestExamTagDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCommonListDto;
import com.sm.pfprod.model.entity.*;
import com.sm.pfprod.service.biz.tests.PfTestWaitingRoomService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.security.CurrentUserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
        dto.setIdStudent(CurrentUserUtils.getCurrentUserId());
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

        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        return ResultObject.createSuccess("startExam", ResultObject.DATA_TYPE_OBJECT, pfTestWaitingRoomService.endExam(dto));
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
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
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
        Assert.isTrue(StringUtils.isNotBlank(dto.getSdEvaReferral()), "sdEvaReferral");
        Assert.isTrue(dto.getIdDie() != null, "id_die");
        Assert.isTrue(StringUtils.isNotBlank(dto.getReasonIn()), "reasonIn");

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
        Assert.isTrue(StringUtils.isNotBlank(dto.getReasonOut()), "reasonOut");

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

}
