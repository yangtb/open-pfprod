package com.sm.pfprod.web.rest.biz.kb;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.biz.kb.assess.*;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.FaqEvaCase;
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

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/referral/list")
    public ResultObject listReferral(@RequestBody PfAssessCommonDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCaseItem() != null, "list");

        return ResultObject.createSuccess("listReferral", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.listReferral(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/referral/del")
    public ResultObject delReferral(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("delReferral", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.delReferral(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/referral/save")
    public ResultObject saveReferral(@RequestBody PfAssessReferralDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCase() != null, "idEvaCase");
        Assert.isTrue(StringUtils.isNotBlank(dto.getItemName()), "itemName");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "等效答案");
        return ResultObject.createSuccess("saveReferral", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.saveReferral(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/diagnosis/list")
    public ResultObject listDiagnosisAnswer(@RequestBody PfAssessCommonDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCaseItem() != null, "idEvaCaseItem");

        return ResultObject.createSuccess("listDiagnosisAnswer", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.listDiagnosisAnswer(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/diagnosis/del")
    public ResultObject delDiagnosis(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("delDiagnosis", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.delDiagnosis(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/diagnosis/save")
    public ResultObject saveDiagnosis(@RequestBody PfAssessDiagnosisDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCase() != null, "idEvaCase");
        Assert.isTrue(StringUtils.isNotBlank(dto.getItemName()), "itemName");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "等效答案");
        return ResultObject.createSuccess("saveDiagnosis", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.saveDiagnosis(dto));
    }


    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/reason/list")
    public ResultObject listReasonAnswer(@RequestBody PfAssessCommonDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCaseItem() != null, "idEvaCaseItem");
        Assert.isTrue(StringUtils.isNotBlank(dto.getSdType()), "sdType");
        return ResultObject.createSuccess("listReasonAnswer", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.listReasonAnswer(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/reason/del")
    public ResultObject delReason(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("delReason", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.delReason(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/reason/save")
    public ResultObject saveReason(@RequestBody PfAssessReasonDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCase() != null, "idEvaCase");
        Assert.isTrue(StringUtils.isNotBlank(dto.getItemName()), "itemName");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "等效答案");
        return ResultObject.createSuccess("saveReason", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.saveReason(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/cover/list")
    public ResultObject listCoverAnswer(@RequestBody PfAssessCommonDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCaseItem() != null, "idEvaCaseItem");
        Assert.isTrue(StringUtils.isNotBlank(dto.getSdType()), "sdType");
        return ResultObject.createSuccess("listCoverAnswer", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.listCoverAnswer(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/cover/del")
    public ResultObject delCover(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("delCover", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.delCover(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/cover/save")
    public ResultObject saveCover(@RequestBody PfAssessCoverDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCase() != null, "idEvaCase");
        Assert.isTrue(StringUtils.isNotBlank(dto.getItemName()), "itemName");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "等效答案");
        return ResultObject.createSuccess("saveCover", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.saveCover(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/must/list")
    public ResultObject listMustAnswer(@RequestBody PfAssessCommonDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCaseItem() != null, "idEvaCaseItem");
        Assert.isTrue(StringUtils.isNotBlank(dto.getSdType()), "sdType");
        return ResultObject.createSuccess("listMustAnswer", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.listMustAnswer(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/must/del")
    public ResultObject delMust(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("delMust", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.delMust(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/must/save")
    public ResultObject saveMust(@RequestBody PfAssessMustDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCase() != null, "idEvaCase");
        Assert.isTrue(StringUtils.isNotBlank(dto.getItemName()), "itemName");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "等效答案");
        return ResultObject.createSuccess("saveMust", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.saveMust(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/effciency/del")
    public ResultObject delEffciency(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("delEffciency", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.delEffciency(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/effciency/save")
    public ResultObject saveEffciency(@RequestBody PfAssessEffciencyDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCase() != null, "idEvaCase");
        Assert.isTrue(StringUtils.isNotBlank(dto.getItemName()), "itemName");
        Assert.isTrue(StringUtils.isNotBlank(dto.getSdEvaEffciency()), "sdEvaEffciency");
        Assert.isTrue(dto.getScoreEva() != null, "scoreEva");
        Assert.isTrue(dto.getQuaUpper() != null, "quaUpper");
        return ResultObject.createSuccess("saveEffciency", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.saveEffciency(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/order/list")
    public ResultObject listOrderAnswer(@RequestBody PfAssessCommonDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCaseItem() != null, "idEvaCaseItem");
        Assert.isTrue(StringUtils.isNotBlank(dto.getSdType()), "sdType");
        return ResultObject.createSuccess("listOrderAnswer", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.listOrderAnswer(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/order/del")
    public ResultObject delOrder(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("delOrder", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.delOrder(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/order/save")
    public ResultObject saveOrder(@RequestBody PfAssessOrderDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCase() != null, "idEvaCase");
        Assert.isTrue(StringUtils.isNotBlank(dto.getItemName()), "itemName");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "等效答案");
        return ResultObject.createSuccess("saveOrder", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.saveOrder(dto));
    }


}
