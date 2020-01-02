package com.sm.pfprod.web.rest.biz.kb;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.FaqEvaTag;
import com.sm.pfprod.model.entity.FaqMedTag;
import com.sm.pfprod.model.entity.FaqMedicalrec;
import com.sm.pfprod.model.entity.FaqMedicalrecCa;
import com.sm.pfprod.model.enums.OperationTypeEnum;
import com.sm.pfprod.model.vo.biz.PfCommonZtreeVo;
import com.sm.pfprod.service.biz.kb.PfCaseHistoryService;
import com.sm.pfprod.web.security.CurrentUserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: PfCaseHistoryRestController
 * @Description: 临床病例库rest
 * @Author yangtongbin
 * @Date 2018/10/10
 */
@RestController
@RequestMapping(value = "/pf/r/case/history")
public class PfCaseHistoryRestController {

    @Resource
    private PfCaseHistoryService pfCaseHistoryService;

    /**
     * 分类树
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0040','ROLE_SUPER')")
    @PostMapping(value = "/classify/tree")
    public ResultObject listClassifyTree() {
        Long idOrg = CurrentUserUtils.getCurrentUserIdOrg();
        List<PfCommonZtreeVo> ztreeVos = pfCaseHistoryService.listClassifyTree();
        ztreeVos.stream().forEach(pfCommonZtreeVo -> {
            if (!pfCommonZtreeVo.getExt().equals(String.valueOf(idOrg))) {
                pfCommonZtreeVo.setNoR(true);
            }
        });
        return ResultObject.createSuccess("listClassifyTree", ResultObject.DATA_TYPE_LIST, ztreeVos);
    }

    /**
     * 新增分类信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0040','ROLE_SUPER')")
    @PostMapping(value = "/classify/add")
    public ResultObject addClassify(@RequestBody FaqMedicalrecCa dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("addClassify", ResultObject.DATA_TYPE_OBJECT,
                pfCaseHistoryService.addClassify(dto));
    }

    /**
     * 编辑分类信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0040', 'ROLE_SUPER')")
    @PostMapping(value = "/classify/edit")
    public ResultObject editClassify(@RequestBody FaqMedicalrecCa dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedicalrecCa() != null, "idMedicalrecCa");
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfCaseHistoryService.editClassify(dto) ? ResultObject.createSuccess("editClassify", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("editClassify", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);

    }

    /**
     * 删除分类信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0040','ROLE_SUPER')")
    @PostMapping(value = "/classify/del")
    public ResultObject delClassify(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfCaseHistoryService.delClassify(dto) ? ResultObject.createSuccess("delClassify", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delClassify", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 新增病例模板信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0040','ROLE_SUPER')")
    @PostMapping(value = "/template/add")
    public ResultObject addTemplate(@RequestBody FaqMedicalrec dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedicalrecCa() != null, "idMedicalrecCa");
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setIdOrg(CurrentUserUtils.getCurrentUser().getIdOrg());
        return ResultObject.createSuccess("addTemplate", ResultObject.DATA_TYPE_OBJECT,
                pfCaseHistoryService.addTemplate(dto));
    }

    /**
     * 编辑病例模板信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0040', 'ROLE_SUPER')")
    @PostMapping(value = "/template/edit")
    public ResultObject editTemplate(@RequestBody FaqMedicalrec dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdDemo() != null, "idDemo");
        Assert.isTrue(dto.getIdMedicalrec() != null, "idMedicalrec");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setIdOrg(CurrentUserUtils.getCurrentUser().getIdOrg());
        return pfCaseHistoryService.editTemplate(dto) ? ResultObject.createSuccess("editTemplate", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("editTemplate", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);

    }

    /**
     * 删除病例模板信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0040','ROLE_SUPER')")
    @RequestMapping(value = "/template/del")
    public ResultObject delTemplate(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfCaseHistoryService.delTemplate(dto) ? ResultObject.createSuccess("delTemplate", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delQuestion", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 停用、启用病例模板信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0040','ROLE_SUPER')")
    @RequestMapping(value = "/template/updateStatus")
    public ResultObject updateTemplateStatus(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setOperationType(OperationTypeEnum.UPDATE_STATUS.getCode());
        return pfCaseHistoryService.delTemplate(dto) ? ResultObject.createSuccess("updateTemplateStatus", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("updateTemplateStatus", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 保存病例标签
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0040','ROLE_SUPER')")
    @PostMapping(value = "/med/tag/save")
    public ResultObject saveMedTag(@RequestBody FaqMedTag dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedicalrec() != null, "idMedicalrec");
        Assert.isTrue(dto.getIdTag() != null, "idTag");
        Assert.isTrue(dto.getIdMedCase() != null, "idMedCase");
        return ResultObject.createSuccess("saveMedTag", ResultObject.DATA_TYPE_OBJECT,
                pfCaseHistoryService.saveMedTag(dto));
    }

    /**
     * 保存评估标签
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0040','ROLE_SUPER')")
    @PostMapping(value = "/med/eva/save")
    public ResultObject saveEvaTag(@RequestBody FaqEvaTag dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedicalrec() != null, "idMedicalrec");
        Assert.isTrue(dto.getIdTag() != null, "idTag");
        Assert.isTrue(dto.getIdEvaCase() != null, "idEvaCase");
        return ResultObject.createSuccess("saveEvaTag", ResultObject.DATA_TYPE_OBJECT,
                pfCaseHistoryService.saveEvaTag(dto));
    }

    /**
     * 查询病例标签信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0040','ROLE_SUPER')")
    @PostMapping(value = "/select/med/tag")
    public ResultObject selectMedTag(@RequestBody FaqMedTag dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedicalrec() != null, "idMedicalrec");
        if (dto.getIdTag() == null && StringUtils.isBlank(dto.getCdMedAsse())) {
            throw new BizRuntimeException(ErrorCode.ERROR_GENERAL_110001, "idTag和cdMedAsse必须传一个");
        }
        return ResultObject.createSuccess("selectMedTag", ResultObject.DATA_TYPE_OBJECT,
                pfCaseHistoryService.selectMedTag(dto));
    }

    /**
     * 查询评估标签信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0040','ROLE_SUPER')")
    @PostMapping(value = "/select/eva/tag")
    public ResultObject selectEvaTag(@RequestBody FaqEvaTag dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedicalrec() != null, "idMedicalrec");
        if (dto.getIdTag() == null && StringUtils.isBlank(dto.getCdEvaAsse())) {
            throw new BizRuntimeException(ErrorCode.ERROR_GENERAL_110001, "idTag和cdEvaAsse必须传一个");
        }
        return ResultObject.createSuccess("selectEvaTag", ResultObject.DATA_TYPE_OBJECT,
                pfCaseHistoryService.selectEvaTag(dto));
    }

    /**
     * 重载病例组件
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0040','ROLE_SUPER')")
    @PostMapping(value = "/save/as/med")
    public ResultObject saveAsMed(@RequestBody FaqMedTag dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getOldIdMedCase() != null, "oldIdMedCase");
        Assert.isTrue(StringUtils.isNotBlank(dto.getCaseName()), "caseName");
        Assert.isTrue(StringUtils.isNotBlank(dto.getCdMedAsse()), "cdMedAsse");
        Assert.isTrue(dto.getIdMedicalrec() != null, "idMedicalrec");
        Assert.isTrue(dto.getIdTag() != null, "idTag");
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("saveAsMed", ResultObject.DATA_TYPE_OBJECT,
                pfCaseHistoryService.saveAsMed(dto));
    }

    /**
     * 重载评估组件
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0040','ROLE_SUPER')")
    @PostMapping(value = "/save/as/eva")
    public ResultObject saveAsEva(@RequestBody FaqEvaTag dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getOldIdEvaCase() != null, "oldIdEvaCase");
        Assert.isTrue(StringUtils.isNotBlank(dto.getCaseName()), "caseName");
        Assert.isTrue(StringUtils.isNotBlank(dto.getCdEvaAsse()), "cdEvaAsse");
        Assert.isTrue(dto.getIdMedicalrec() != null, "idMedicalrec");
        Assert.isTrue(dto.getIdTag() != null, "idTag");
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("saveAsEva", ResultObject.DATA_TYPE_OBJECT,
                pfCaseHistoryService.saveAsEva(dto));
    }

}
