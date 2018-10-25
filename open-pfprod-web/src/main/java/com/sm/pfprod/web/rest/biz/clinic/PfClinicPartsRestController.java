package com.sm.pfprod.web.rest.biz.clinic;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.BasAlgorithm;
import com.sm.pfprod.model.entity.BasEvaAsse;
import com.sm.pfprod.model.entity.BasMedAsse;
import com.sm.pfprod.model.enums.OperationTypeEnum;
import com.sm.pfprod.service.biz.clinic.PfClinicPartsService;
import com.sm.pfprod.web.security.CurrentUserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName: PfClinicPartsRestController
 * @Description: 临床组件
 * @Author yangtongbin
 * @Date 2018/10/12
 */
@RestController
@RequestMapping(value = "/pf/r/clinic")
public class PfClinicPartsRestController {

    @Resource
    private PfClinicPartsService pfClinicPartsService;

    /**
     * 新增组件信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_CLINIC0010','ROLE_SUPER')")
    @PostMapping(value = "/part/add")
    public ResultObject addPart(@RequestBody BasMedAsse dto) {
        /* 参数校验 */
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("addPart", ResultObject.DATA_TYPE_OBJECT,
                pfClinicPartsService.addPart(dto));
    }

    /**
     * 编辑组件信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_CLINIC0010', 'ROLE_SUPER')")
    @PostMapping(value = "/part/edit")
    public ResultObject editPart(@RequestBody BasMedAsse dto) {
        /* 参数校验 */
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfClinicPartsService.editPart(dto) ? ResultObject.createSuccess("editPart", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("editPart", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);

    }

    /**
     * 删除组件信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_CLINIC0010','ROLE_SUPER')")
    @RequestMapping(value = "/part/del")
    public ResultObject delPart(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfClinicPartsService.delPart(dto) ? ResultObject.createSuccess("delPart", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delQuestion", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 所有组件信息
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_CLINIC0010','ROLE_SUPER')")
    @RequestMapping(value = "/part/all")
    public ResultObject allPart() {
        /* 参数校验 */
        return ResultObject.createSuccess("allPart", ResultObject.DATA_TYPE_OBJECT,
                pfClinicPartsService.listAllPart());
    }

    /**
     * 停用、启用组件信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_CLINIC0010','ROLE_SUPER')")
    @RequestMapping(value = "/part/updateStatus")
    public ResultObject updatePartStatus(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setOperationType(OperationTypeEnum.UPDATE_STATUS.getCode());
        return pfClinicPartsService.delPart(dto) ? ResultObject.createSuccess("updatePartStatus", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("updatePartStatus", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }


    /**
     * 新增评估表信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_CLINIC0020','ROLE_SUPER')")
    @PostMapping(value = "/sheet/add")
    public ResultObject addSheet(@RequestBody BasEvaAsse dto) {
        /* 参数校验 */
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("addSheet", ResultObject.DATA_TYPE_OBJECT,
                pfClinicPartsService.addSheet(dto));
    }

    /**
     * 编辑评估表信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_CLINIC0020', 'ROLE_SUPER')")
    @PostMapping(value = "/sheet/edit")
    public ResultObject editSheet(@RequestBody BasEvaAsse dto) {
        /* 参数校验 */
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfClinicPartsService.editSheet(dto) ? ResultObject.createSuccess("editSheet", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("editSheet", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);

    }

    /**
     * 删除评估表信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_CLINIC0020','ROLE_SUPER')")
    @RequestMapping(value = "/sheet/del")
    public ResultObject delSheet(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfClinicPartsService.delSheet(dto) ? ResultObject.createSuccess("delSheet", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delQuestion", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 停用、启用评估表信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_CLINIC0020','ROLE_SUPER')")
    @RequestMapping(value = "/sheet/updateStatus")
    public ResultObject updateSheetStatus(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setOperationType(OperationTypeEnum.UPDATE_STATUS.getCode());
        return pfClinicPartsService.delSheet(dto) ? ResultObject.createSuccess("updateSheetStatus", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("updateSheetStatus", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 所有评估组件列表
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_CLINIC0010','ROLE_SUPER')")
    @RequestMapping(value = "/sheet/all")
    public ResultObject allSheet() {
        /* 参数校验 */
        return ResultObject.createSuccess("allSheet", ResultObject.DATA_TYPE_OBJECT,
                pfClinicPartsService.listAllSheet());
    }

    /**
     * 新增算法信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_CLINIC0020','ROLE_SUPER')")
    @PostMapping(value = "/algorithm/add")
    public ResultObject addAlgorithm(@RequestBody BasAlgorithm dto) {
        /* 参数校验 */
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("addAlgorithm", ResultObject.DATA_TYPE_OBJECT,
                pfClinicPartsService.addAlgorithm(dto));
    }

    /**
     * 编辑算法信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_CLINIC0020', 'ROLE_SUPER')")
    @PostMapping(value = "/algorithm/edit")
    public ResultObject editAlgorithm(@RequestBody BasAlgorithm dto) {
        /* 参数校验 */
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfClinicPartsService.editAlgorithm(dto) ? ResultObject.createSuccess("editAlgorithm", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("editAlgorithm", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);

    }

    /**
     * 删除算法信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_CLINIC0020','ROLE_SUPER')")
    @RequestMapping(value = "/algorithm/del")
    public ResultObject delAlgorithm(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfClinicPartsService.delAlgorithm(dto) ? ResultObject.createSuccess("delAlgorithm", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delQuestion", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 停用、启用算法信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_CLINIC0020','ROLE_SUPER')")
    @RequestMapping(value = "/algorithm/updateStatus")
    public ResultObject updateAlgorithmStatus(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setOperationType(OperationTypeEnum.UPDATE_STATUS.getCode());
        return pfClinicPartsService.delAlgorithm(dto) ? ResultObject.createSuccess("updateAlgorithmStatus", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("updateAlgorithmStatus", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

}
