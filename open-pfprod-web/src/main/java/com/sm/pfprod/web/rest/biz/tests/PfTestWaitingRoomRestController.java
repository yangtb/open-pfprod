package com.sm.pfprod.web.rest.biz.tests;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.biz.tests.PfTestExamTagDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.ExmMedResultInques;
import com.sm.pfprod.model.entity.ExmTestexec;
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




}
