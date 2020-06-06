package com.sm.pfprod.web.rest.system.grade;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.enums.YesOrNoNum;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCommonListDto;
import com.sm.pfprod.model.entity.SysClass;
import com.sm.pfprod.model.enums.OperationTypeEnum;
import com.sm.pfprod.service.system.grade.PfGradeService;
import com.sm.pfprod.web.security.CurrentUserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/pf/r/class")

public class PfGradeRestController {

    @Resource
    private PfGradeService pfGradeService;

    /**
     * 新增班级
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_SYSTEM_CLASS_MG','ROLE_SUPER')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addGrade(@RequestBody SysClass dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        return ResultObject.createSuccess("addGrade", ResultObject.DATA_TYPE_OBJECT,
                pfGradeService.saveGrade(dto));
    }

    /**
     * 编辑班级
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_SYSTEM_CLASS_MG', 'ROLE_SUPER')")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editGrade(@RequestBody SysClass dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdClass() != null, "idClass");
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("saveGrade", ResultObject.DATA_TYPE_OBJECT,
                pfGradeService.saveGrade(dto));
    }

    /**
     * 删除班级
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_SYSTEM_CLASS_MG','ROLE_SUPER')")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delGrade(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setStatus(YesOrNoNum.YES.getCode());
        return pfGradeService.delGrade(dto) ? ResultObject.createSuccess("delGrade", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delGrade", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 停用、启用
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_SYSTEM_CLASS_MG','ROLE_SUPER')")
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updateGradeStatus(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setOperationType(OperationTypeEnum.UPDATE_STATUS.getCode());
        return pfGradeService.delGrade(dto) ? ResultObject.createSuccess("updateGradeStatus", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("updateGradeStatus", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 添加学生
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_SYSTEM_CLASS_MG', 'ROLE_SUPER')")
    @RequestMapping(value = "/student/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addStudent(@RequestBody PfCommonListDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getExtId() != null, "班级");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "学生list");
        return ResultObject.createSuccess("addStudent", ResultObject.DATA_TYPE_OBJECT,
                pfGradeService.saveStudent(dto));
    }

    /**
     * 删除学生
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_SYSTEM_CLASS_MG','ROLE_SUPER')")
    @RequestMapping(value = "/student/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject deStudent(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setStatus(YesOrNoNum.YES.getCode());
        return pfGradeService.delStudent(dto) ? ResultObject.createSuccess("deStudent", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("deStudent", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }
}
