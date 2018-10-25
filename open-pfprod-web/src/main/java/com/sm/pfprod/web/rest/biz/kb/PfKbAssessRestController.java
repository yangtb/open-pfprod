package com.sm.pfprod.web.rest.biz.kb;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.*;
import com.sm.pfprod.model.enums.OperationTypeEnum;
import com.sm.pfprod.service.biz.kb.PfKbAssessService;
import com.sm.pfprod.web.security.CurrentUserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName: PfKbAssessRestController
 * @Description: 评估组件用例
 * @Author yangtongbin
 * @Date 2018/10/22
 */
@RestController
@RequestMapping(value = "/pf/r/kb/assess")
public class PfKbAssessRestController {

    @Resource
    private PfKbAssessService pfKbAssessService;

    /**
     * 新增病例组件用例
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @PostMapping(value = "/add")
    public ResultObject addKbAssess(@RequestBody FaqEvaCase dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        Assert.isTrue(StringUtils.isNotBlank(dto.getCdEvaAsse()), "cdEvaAsse");
        Assert.isTrue(StringUtils.isNotBlank(dto.getFgActive()), "fgActive");
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setIdOrg(CurrentUserUtils.getCurrentUser().getIdOrg());
        return ResultObject.createSuccess("addKbAssess", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.saveKbAssess(dto));
    }

    /**
     * 编辑病例组件用例
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @PostMapping(value = "/edit")
    public ResultObject editKbAssess(@RequestBody FaqEvaCase dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCase() != null, "idMedCase");
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        Assert.isTrue(StringUtils.isNotBlank(dto.getCdEvaAsse()), "cdEvaAsse");
        Assert.isTrue(StringUtils.isNotBlank(dto.getFgActive()), "fgActive");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setIdOrg(CurrentUserUtils.getCurrentUser().getIdOrg());
        return ResultObject.createSuccess("editKbAssess", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.saveKbAssess(dto));
    }

    /**
     * 停用、启用病例组件用例
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/updateStatus")
    public ResultObject updateKbAssessStatus(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setOperationType(OperationTypeEnum.UPDATE_STATUS.getCode());
        return pfKbAssessService.delKbAssess(dto) ? ResultObject.createSuccess("updateKbAssessStatus", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("updateKbAssessStatus", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }


}
