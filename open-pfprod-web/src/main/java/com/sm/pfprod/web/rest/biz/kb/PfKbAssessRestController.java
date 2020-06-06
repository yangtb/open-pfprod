package com.sm.pfprod.web.rest.biz.kb;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.enums.YesOrNoNum;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.biz.kb.assess.*;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.*;
import com.sm.pfprod.model.enums.OperationTypeEnum;
import com.sm.pfprod.service.biz.kb.PfKbAssessService;
import com.sm.pfprod.web.security.CurrentUserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
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
    @PreAuthorize("hasAnyRole('ROLE_FAQ0020', 'ROLE_SUPER')")
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
    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/updateStatus")
    public ResultObject updateKbAssessStatus(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setOperationType(OperationTypeEnum.UPDATE_STATUS.getCode());
        return pfKbAssessService.delKbAssess(dto) ? ResultObject.createSuccess("updateKbAssessStatus", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("updateKbAssessStatus", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/referral/list")
    public ResultObject listReferral(@RequestBody PfAssessCommonDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCaseItem() != null, "list");

        return ResultObject.createSuccess("listReferral", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.listReferral(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/referral/del")
    public ResultObject delReferral(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("delReferral", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.delReferral(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/referral/save")
    public ResultObject saveReferral(@RequestBody PfAssessReferralDto dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getItemName()), "itemName");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "等效答案");
        if (StringUtils.isNotBlank(dto.getTagFlag()) && dto.getTagFlag().equals(YesOrNoNum.YES.getCode())) {

        } else {
            /* 参数校验 */
            Assert.isTrue(dto.getIdEvaCase() != null, "idEvaCase");
        }
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        dto.setCreator(CurrentUserUtils.getCurrentUsername());

        return ResultObject.createSuccess("saveReferral", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.saveReferral(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/diagnosis/list")
    public ResultObject listDiagnosisAnswer(@RequestBody PfAssessCommonDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCaseItem() != null, "idEvaCaseItem");

        return ResultObject.createSuccess("listDiagnosisAnswer", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.listDiagnosisAnswer(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/diagnosis/del")
    public ResultObject delDiagnosis(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("delDiagnosis", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.delDiagnosis(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/diagnosis/save")
    public ResultObject saveDiagnosis(@RequestBody PfAssessDiagnosisDto dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getItemName()), "itemName");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "等效答案");
        if (StringUtils.isNotBlank(dto.getTagFlag()) && dto.getTagFlag().equals(YesOrNoNum.YES.getCode())) {

        } else {
            Assert.isTrue(dto.getIdEvaCase() != null, "idEvaCase");
        }
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("saveDiagnosis", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.saveDiagnosis(dto));
    }


    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/reason/list")
    public ResultObject listReasonAnswer(@RequestBody PfAssessCommonDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCaseItem() != null, "idEvaCaseItem");
        Assert.isTrue(StringUtils.isNotBlank(dto.getSdType()), "sdType");
        return ResultObject.createSuccess("listReasonAnswer", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.listReasonAnswer(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/reason/del")
    public ResultObject delReason(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("delReason", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.delReason(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/reason/save")
    public ResultObject saveReason(@RequestBody PfAssessReasonDto dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getItemName()), "itemName");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "等效答案");
        Assert.isTrue(dto.getIdDie() != null, "疾病");
        if (StringUtils.isNotBlank(dto.getTagFlag()) && dto.getTagFlag().equals(YesOrNoNum.YES.getCode())) {

        } else {
            Assert.isTrue(dto.getIdEvaCase() != null, "idEvaCase");
        }
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("saveReason", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.saveReason(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/cover/list")
    public ResultObject listCoverAnswer(@RequestBody PfAssessCommonDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCaseItem() != null, "idEvaCaseItem");
        Assert.isTrue(StringUtils.isNotBlank(dto.getSdType()), "sdType");
        return ResultObject.createSuccess("listCoverAnswer", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.listCoverAnswer(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/cover/del")
    public ResultObject delCover(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("delCover", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.delCover(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/cover/save")
    public ResultObject saveCover(@RequestBody PfAssessCoverDto dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getItemName()), "itemName");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "等效答案");
        if (StringUtils.isNotBlank(dto.getTagFlag()) && dto.getTagFlag().equals(YesOrNoNum.YES.getCode())) {

        } else {
            Assert.isTrue(dto.getIdEvaCase() != null, "idEvaCase");
        }
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("saveCover", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.saveCover(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/must/list")
    public ResultObject listMustAnswer(@RequestBody PfAssessCommonDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCaseItem() != null, "idEvaCaseItem");
        Assert.isTrue(StringUtils.isNotBlank(dto.getSdType()), "sdType");
        return ResultObject.createSuccess("listMustAnswer", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.listMustAnswer(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/must/del")
    public ResultObject delMust(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("delMust", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.delMust(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/must/save")
    public ResultObject saveMust(@RequestBody PfAssessMustDto dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getItemName()), "itemName");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "等效答案");
        if (StringUtils.isNotBlank(dto.getTagFlag()) && dto.getTagFlag().equals(YesOrNoNum.YES.getCode())) {

        } else {
            Assert.isTrue(dto.getIdEvaCase() != null, "idEvaCase");
        }
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("saveMust", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.saveMust(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/thorough/list")
    public ResultObject listThoroughAnswer(@RequestBody PfAssessCommonDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCaseItem() != null, "idEvaCaseItem");
        Assert.isTrue(StringUtils.isNotBlank(dto.getSdType()), "sdType");
        return ResultObject.createSuccess("listMustAnswer", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.listThoroughAnswer(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/thorough/del")
    public ResultObject delThorough(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("delMust", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.delThorough(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/thorough/save")
    public ResultObject saveThorough(@RequestBody PfAssessThoroughDto dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getItemName()), "itemName");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "等效答案");
        if (StringUtils.isNotBlank(dto.getTagFlag()) && dto.getTagFlag().equals(YesOrNoNum.YES.getCode())) {

        } else {
            Assert.isTrue(dto.getIdEvaCase() != null, "idEvaCase");
        }
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("saveThorough", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.saveThorough(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/effciency/del")
    public ResultObject delEffciency(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("delEffciency", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.delEffciency(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/effciency/save")
    public ResultObject saveEffciency(@RequestBody PfAssessEffciencyDto dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getItemName()), "itemName");
        Assert.isTrue(StringUtils.isNotBlank(dto.getSdEvaEffciency()), "sdEvaEffciency");
        Assert.isTrue(dto.getScoreEva() != null, "scoreEva");
        Assert.isTrue(dto.getQuaUpper() != null, "quaUpper");
        if (StringUtils.isNotBlank(dto.getTagFlag()) && dto.getTagFlag().equals(YesOrNoNum.YES.getCode())) {

        } else {
            Assert.isTrue(dto.getIdEvaCase() != null, "idEvaCase");
        }
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        dto.setCreator(CurrentUserUtils.getCurrentUsername());

        return ResultObject.createSuccess("saveEffciency", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.saveEffciency(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/order/list")
    public ResultObject listOrderAnswer(@RequestBody PfAssessCommonDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdEvaCaseItem() != null, "idEvaCaseItem");
        Assert.isTrue(StringUtils.isNotBlank(dto.getSdType()), "sdType");
        return ResultObject.createSuccess("listOrderAnswer", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.listOrderAnswer(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/order/del")
    public ResultObject delOrder(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("delOrder", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.delOrder(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/order/save")
    public ResultObject saveOrder(@RequestBody PfAssessOrderDto dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getItemName()), "itemName");
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "等效答案");
        if (StringUtils.isNotBlank(dto.getTagFlag()) && dto.getTagFlag().equals(YesOrNoNum.YES.getCode())) {

        } else {
            Assert.isTrue(dto.getIdEvaCase() != null, "idEvaCase");
        }
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("saveOrder", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.saveOrder(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/common/del")
    public ResultObject delCommonAssess(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        Assert.isTrue(StringUtils.isNotBlank(dto.getExtType()), "extType");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("delAssess", ResultObject.DATA_TYPE_OBJECT,
                pfKbAssessService.delCommonAssess(dto));
    }


    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/from/case/save")
    public ResultObject fromCaseSave(@RequestBody PfAssessCommonSaveDto dto) {
        /* 参数校验 */
        if ("must".equals(dto.getModule())) {
            PfAssessMustDto pfAssessMustDto = new PfAssessMustDto();
            BeanUtils.copyProperties(dto, pfAssessMustDto);
            pfAssessMustDto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
            pfAssessMustDto.setCreator(CurrentUserUtils.getCurrentUsername());

            pfAssessMustDto.setScoreEva(BigDecimal.valueOf(1));
            pfAssessMustDto.setSdEva(dto.getSdEva());
            pfAssessMustDto.setFromCaseFlag(dto.getFromCaseFlag());

            for (PfAssessCommonitemDto item : dto.getNames()) {
                pfAssessMustDto.setItemName(item.getName());
                List<FaqEvaCaseItemMust> list = new ArrayList<>();
                FaqEvaCaseItemMust faqEvaCaseItemMust = new FaqEvaCaseItemMust();
                if ("1".equals(dto.getSdEva())) {
                    faqEvaCaseItemMust.setIdInques(item.getId());
                }
                if ("2".equals(dto.getSdEva())) {
                    faqEvaCaseItemMust.setIdBody(item.getId());
                }
                if ("3".equals(dto.getSdEva())) {
                    faqEvaCaseItemMust.setIdInspectItem(item.getId());
                }
                faqEvaCaseItemMust.setSdEvaMust(dto.getSdEva());
                faqEvaCaseItemMust.setFgCrs("0");
                list.add(faqEvaCaseItemMust);
                pfAssessMustDto.setList(list);
                pfKbAssessService.saveMust(pfAssessMustDto);
            }
        }

        if ("reason".equals(dto.getModule())) {
            PfAssessReasonDto pfAssessReasonDto = new PfAssessReasonDto();
            BeanUtils.copyProperties(dto, pfAssessReasonDto);
            pfAssessReasonDto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
            pfAssessReasonDto.setCreator(CurrentUserUtils.getCurrentUsername());

            pfAssessReasonDto.setScoreEva(BigDecimal.valueOf(1));
            pfAssessReasonDto.setSdEva(dto.getSdEva());
            pfAssessReasonDto.setFromCaseFlag(dto.getFromCaseFlag());

            for (PfAssessCommonitemDto item  : dto.getNames()) {
                pfAssessReasonDto.setItemName(item.getName());
                List<FaqEvaCaseItemReason> list = new ArrayList<>();
                FaqEvaCaseItemReason faqEvaCaseItemReason = new FaqEvaCaseItemReason();
                if ("1".equals(dto.getSdEva())) {
                    faqEvaCaseItemReason.setIdInques(item.getId());
                }
                if ("2".equals(dto.getSdEva())) {
                    faqEvaCaseItemReason.setIdBody(item.getId());
                }
                if ("3".equals(dto.getSdEva())) {
                    faqEvaCaseItemReason.setIdInspectItem(item.getId());
                }
                faqEvaCaseItemReason.setSdEvaEffciency(dto.getSdEva());
                faqEvaCaseItemReason.setFgCrs("0");
                list.add(faqEvaCaseItemReason);
                pfAssessReasonDto.setList(list);
                pfKbAssessService.saveReason(pfAssessReasonDto);
            }
        }

        if ("cover".equals(dto.getModule())) {
            PfAssessCoverDto pfAssessCoverDto = new PfAssessCoverDto();
            BeanUtils.copyProperties(dto, pfAssessCoverDto);
            pfAssessCoverDto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
            pfAssessCoverDto.setCreator(CurrentUserUtils.getCurrentUsername());

            pfAssessCoverDto.setScoreEva(BigDecimal.valueOf(1));
            pfAssessCoverDto.setSdEva(dto.getSdEva());
            pfAssessCoverDto.setFromCaseFlag(dto.getFromCaseFlag());

            for (PfAssessCommonitemDto item  : dto.getNames()) {
                pfAssessCoverDto.setItemName(item.getName());
                List<FaqEvaCaseItemCover> list = new ArrayList<>();
                FaqEvaCaseItemCover faqEvaCaseItemCover = new FaqEvaCaseItemCover();
                if ("1".equals(dto.getSdEva())) {
                    faqEvaCaseItemCover.setIdInques(String.valueOf(item.getId()));
                }
                if ("2".equals(dto.getSdEva())) {
                    faqEvaCaseItemCover.setIdBody(item.getId());
                }
                if ("3".equals(dto.getSdEva())) {
                    faqEvaCaseItemCover.setIdInspectItem(item.getId());
                }
                faqEvaCaseItemCover.setSdEvaCover(dto.getSdEva());
                faqEvaCaseItemCover.setFgCrs("0");
                list.add(faqEvaCaseItemCover);
                pfAssessCoverDto.setList(list);
                pfKbAssessService.saveCover(pfAssessCoverDto);
            }
        }

        if ("thorough".equals(dto.getModule())) {
            PfAssessThoroughDto pfAssessThoroughDto = new PfAssessThoroughDto();
            BeanUtils.copyProperties(dto, pfAssessThoroughDto);
            pfAssessThoroughDto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
            pfAssessThoroughDto.setCreator(CurrentUserUtils.getCurrentUsername());

            pfAssessThoroughDto.setScoreEva(BigDecimal.valueOf(1));
            pfAssessThoroughDto.setSdEva(dto.getSdEva());
            pfAssessThoroughDto.setFromCaseFlag(dto.getFromCaseFlag());

            for (PfAssessCommonitemDto item  : dto.getNames()) {
                pfAssessThoroughDto.setItemName(item.getName());
                List<FaqEvaCaseItemThorough> list = new ArrayList<>();
                FaqEvaCaseItemThorough faqEvaCaseItemThorough = new FaqEvaCaseItemThorough();
                if ("1".equals(dto.getSdEva())) {
                    faqEvaCaseItemThorough.setIdInques(item.getId());
                }
                if ("2".equals(dto.getSdEva())) {
                    faqEvaCaseItemThorough.setIdBody(item.getId());
                }
                if ("3".equals(dto.getSdEva())) {
                    faqEvaCaseItemThorough.setIdInspectItem(item.getId());
                }
                faqEvaCaseItemThorough.setSdEvaMust(dto.getSdEva());
                faqEvaCaseItemThorough.setFgCrs("0");
                list.add(faqEvaCaseItemThorough);
                pfAssessThoroughDto.setList(list);
                pfKbAssessService.saveThorough(pfAssessThoroughDto);
            }
        }
        return ResultObject.createSuccess("fromCaseSave", ResultObject.DATA_TYPE_OBJECT, true);
    }

}
