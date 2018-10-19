package com.sm.pfprod.web.rest.biz.kb;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.FaqMedCase;
import com.sm.pfprod.model.enums.OperationTypeEnum;
import com.sm.pfprod.service.biz.kb.PfKbPartService;
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
 * @ClassName: PfKbPartRestController
 * @Description: 病例组件用例
 * @Author yangtongbin
 * @Date 2018/10/17
 */
@RestController
@RequestMapping(value = "/pf/r/kb/part")
public class PfKbPartRestController {

    @Resource
    private PfKbPartService pfKbPartService;

    /**
     * 新增病例组件用例
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @PostMapping(value = "/add")
    public ResultObject addKbPart(@RequestBody FaqMedCase dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        Assert.isTrue(StringUtils.isNotBlank(dto.getCdMedAsse()), "cdMedAsse");
        Assert.isTrue(StringUtils.isNotBlank(dto.getFgActive()), "fgActive");
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setIdOrg(CurrentUserUtils.getCurrentUser().getIdOrg());
        return ResultObject.createSuccess("addKbPart", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.addKbPart(dto));
    }

    /**
     * 编辑病例组件用例
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @PostMapping(value = "/edit")
    public ResultObject editKbPart(@RequestBody FaqMedCase dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedCase() != null, "idMedCase");
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        Assert.isTrue(StringUtils.isNotBlank(dto.getCdMedAsse()), "cdMedAsse");
        Assert.isTrue(StringUtils.isNotBlank(dto.getFgActive()), "fgActive");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setIdOrg(CurrentUserUtils.getCurrentUser().getIdOrg());
        return pfKbPartService.editKbPart(dto) ? ResultObject.createSuccess("editKbPart", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("editKbPart", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);

    }

    /**
     * 停用、启用病例组件用例
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/updateStatus")
    public ResultObject updateKbPartStatus(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setOperationType(OperationTypeEnum.UPDATE_STATUS.getCode());
        return pfKbPartService.delKbPart(dto) ? ResultObject.createSuccess("updateKbPartStatus", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("updateKbPartStatus", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }
}
