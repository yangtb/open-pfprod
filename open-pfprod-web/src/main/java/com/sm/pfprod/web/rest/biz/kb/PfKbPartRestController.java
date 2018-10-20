package com.sm.pfprod.web.rest.biz.kb;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.*;
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

    /**
     * 保存问诊问题
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @PostMapping(value = "/cons/save")
    public ResultObject saveFaqMedCaseInques(@RequestBody FaqMedCaseInquesList dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedCase() != null, "idMedCase");
        Assert.isTrue(dto.getIdInquesCa() != null, "idInquesCa");
        Assert.isTrue(dto.getIdInques() != null, "idInques");
        Assert.isTrue(StringUtils.isNotBlank(dto.getDesInques()), "desInques");
        Assert.isTrue(dto.getIdAnswer() != null, "idAnswer");
        Assert.isTrue(StringUtils.isNotBlank(dto.getDesAnswer()), "desAnswer");
        Assert.isTrue(dto.getIdMedia() != null, "idMedia");
        Assert.isTrue(dto.getFgCarried() != null, "fgCarried");
        return ResultObject.createSuccess("saveFaqMedCaseInques", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.saveFaqMedCaseInques(dto));
    }

    /**
     * 删除问诊问题
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/cons/del")
    public ResultObject delFaqMedCaseInques(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfKbPartService.delFaqMedCaseInques(dto) ? ResultObject.createSuccess("delFaqMedCaseInques", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delFaqMedCaseInques", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 组件 - add文本
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @PostMapping(value = "/text/save")
    public ResultObject saveKbText(@RequestBody FaqMedCaseText dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedCase() != null, "idMedCase");
        Assert.isTrue(StringUtils.isNotBlank(dto.getContent()), "content");
        return ResultObject.createSuccess("saveKbText", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.saveKbText(dto));
    }

    /**
     * 组件 - select文本
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @PostMapping(value = "/text/select")
    public ResultObject selectKbText(@RequestBody FaqMedCaseText dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedCase() != null, "idMedCase");
        return ResultObject.createSuccess("selectKbText", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.selectKbText(dto.getIdMedCase()));
    }

    /**
     * 组件 - add图片
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @PostMapping(value = "/pic/save")
    public ResultObject saveKbPic(@RequestBody FaqMedCasePic dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedCase() != null, "idMedCase");
        Assert.isTrue(dto.getIdMedia() != null, "idMedia");
        return ResultObject.createSuccess("saveKbPic", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.saveKbPic(dto));
    }

    /**
     * 组件 - select图片
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @PostMapping(value = "/pic/select")
    public ResultObject selectKbPic(@RequestBody FaqMedCasePic dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedCase() != null, "idMedCase");
        return ResultObject.createSuccess("selectKbPic", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.selectKbPic(dto.getIdMedCase()));
    }

    /**
     * 组件 - add患者
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @PostMapping(value = "/pat/save")
    public ResultObject saveKbPat(@RequestBody FaqMedCasePatient dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedCase() != null, "idMedCase");
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        Assert.isTrue(dto.getAge() != 0, "age");
        Assert.isTrue(StringUtils.isNotBlank(dto.getComplaint()), "complaint");
        return ResultObject.createSuccess("saveKbPat", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.saveKbPat(dto));
    }

    /**
     * 组件 - select患者
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @PostMapping(value = "/pat/select")
    public ResultObject selectKbPat(@RequestBody FaqMedCasePatient dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedCase() != null, "idMedCase");
        return ResultObject.createSuccess("selectKbPat", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.selectKbPat(dto.getIdMedCase()));
    }

}
