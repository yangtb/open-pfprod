package com.sm.pfprod.web.rest.biz.kb;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.enums.YesOrNoNum;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.biz.kb.PfSaveAsMedDto;
import com.sm.pfprod.model.dto.biz.kb.part.PfPartCommonDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.*;
import com.sm.pfprod.model.enums.OperationTypeEnum;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.kb.PfKbPartService;
import com.sm.pfprod.web.security.CurrentUserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
     * 自定义问诊
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @PostMapping(value = "/cons/custom")
    public ResultObject customFaqMedCaseInques(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        dto.setOperationType(OperationTypeEnum.UPDATE_STATUS.getCode());
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("delFaqMedCaseInques", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.delFaqMedCaseInques(dto));
    }

    /**
     * 重载检验项目
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @PostMapping(value = "/cons/reset")
    public ResultObject resetKbCons(@RequestBody FaqMedCaseInquesList dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedCaseList() != null, "idMedCaseList");
        Assert.isTrue(dto.getIdInques() != null, "idInques");
        Assert.isTrue(dto.getIdAnswer() != null, "idAnswer");
        return ResultObject.createSuccess("resetKbCons", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.resetKbCons(dto));
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
        if (StringUtils.isNotBlank(dto.getTagFlag()) && dto.getTagFlag().equals(YesOrNoNum.YES.getCode())) {
            //Assert.isTrue(dto.getOldIdMedCase() != null, "oldIdMedCase");
        } else {
            Assert.isTrue(dto.getIdMedCase() != null, "idMedCase");
            Assert.isTrue(StringUtils.isNotBlank(dto.getContent()), "content");
        }
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
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
     * 组件 - add评估指南
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @PostMapping(value = "/guide/save")
    public ResultObject saveKbGuide(@RequestBody FaqMedCaseGuide dto) {
        /* 参数校验 */
        if (StringUtils.isNotBlank(dto.getTagFlag()) && dto.getTagFlag().equals(YesOrNoNum.YES.getCode())) {

        } else {
            Assert.isTrue(dto.getIdMedCase() != null, "idMedCase");
            Assert.isTrue(StringUtils.isNotBlank(dto.getGuideContent()), "guideContent");
            Assert.isTrue(StringUtils.isNotBlank(dto.getGuideNotesUrl()), "guideNotesUrl");
        }
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("saveKbGuide", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.saveKbGuide(dto));
    }

    /**
     * 组件 - select评估指南
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @PostMapping(value = "/guide/select")
    public ResultObject selectKbGuide(@RequestBody FaqMedCaseGuide dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedCase() != null, "idMedCase");
        return ResultObject.createSuccess("selectKbGuide", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.selectKbGuide(dto.getIdMedCase()));
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
        if (StringUtils.isNotBlank(dto.getTagFlag()) && dto.getTagFlag().equals(YesOrNoNum.YES.getCode())) {
            if (dto.getOldIdMedCase() == null) {
                Assert.isTrue(dto.getIdMedia() != null, "idMedia");
            }
        } else {
            Assert.isTrue(dto.getIdMedCase() != null, "idMedCase");
            Assert.isTrue(dto.getIdMedia() != null, "idMedia");
        }
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
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
        if (StringUtils.isNotBlank(dto.getTagFlag()) && dto.getTagFlag().equals(YesOrNoNum.YES.getCode())) {
            if (dto.getOldIdMedCase() == null) {
                Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
                Assert.isTrue(dto.getAge() != 0, "age");
                Assert.isTrue(StringUtils.isNotBlank(dto.getComplaint()), "complaint");
            }
        } else {
            Assert.isTrue(dto.getIdMedCase() != null, "idMedCase");
            Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
            Assert.isTrue(dto.getAge() != 0, "age");
            Assert.isTrue(StringUtils.isNotBlank(dto.getComplaint()), "complaint");
        }
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
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


    /**
     * 保存检验项目
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @PostMapping(value = "/exam/save")
    public ResultObject saveExam(@RequestBody FaqMedCaseInspectList dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedCase() != null, "idMedCase");

        Assert.isTrue(dto.getFgCarried() != null, "fgCarried");
        return ResultObject.createSuccess("saveExam", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.saveExam(dto));
    }

    /**
     * 删除检验项目
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @PostMapping(value = "/exam/del")
    public ResultObject delKbExam(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("delKbExam", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.delKbExam(dto));
    }


    /**
     * 自定义检验项目
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @PostMapping(value = "/exam/custom")
    public ResultObject customKbExam(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        dto.setOperationType(OperationTypeEnum.UPDATE_STATUS.getCode());
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("customKbExam", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.delKbExam(dto));
    }

    /**
     * 重载检验项目
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @PostMapping(value = "/exam/reset")
    public ResultObject resetKbExam(@RequestBody FaqMedCaseInspectList dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedCaseList() != null, "idMedCaseList");
        return ResultObject.createSuccess("resetKbExam", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.resetKbExam(dto));
    }

    /**
     * 保存检查
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @PostMapping(value = "/check/save")
    public ResultObject saveCheck(@RequestBody FaqMedCaseBodyList dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedCase() != null, "idMedCase");
        Assert.isTrue(dto.getFgCarried() != null, "fgCarried");
        return ResultObject.createSuccess("saveCheck", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.saveCheck(dto));
    }

    /**
     * 删除检查
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @PostMapping(value = "/check/del")
    public ResultObject delKbCheck(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("delKbCheck", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.delKbCheck(dto));
    }


    /**
     * 自定义检查
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @PostMapping(value = "/check/custom")
    public ResultObject customKbCheck(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        dto.setOperationType(OperationTypeEnum.UPDATE_STATUS.getCode());
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("customKbCheck", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.delKbCheck(dto));
    }

    /**
     * 重载检查
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @PostMapping(value = "/check/reset")
    public ResultObject resetKbCheck(@RequestBody FaqMedCaseBodyList dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedCaseList() != null, "idMedCaseList");
        return ResultObject.createSuccess("resetKbCheck", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.resetKbCheck(dto));
    }

    /**
     * 保存检查图片
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @PostMapping(value = "/check/pic/save")
    public ResultObject saveFaqMedCaseBody(@RequestBody FaqMedCaseBody dto) {
        /* 参数校验 */
        if (StringUtils.isNotBlank(dto.getTagFlag()) && dto.getTagFlag().equals(YesOrNoNum.YES.getCode())) {
        } else {
            Assert.isTrue(dto.getIdMedCase() != null, "idMedCase");
        }
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("saveFaqMedCaseBody", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.saveFaqMedCaseBody(dto));
    }

    /**
     * 获取检查图片
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @PostMapping(value = "/check/pic/select")
    public ResultObject selectFaqMedCaseBody(@RequestBody FaqMedCaseBody dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdMedCase() != null, "idMedCase");
        return ResultObject.createSuccess("selectFaqMedCaseBody", ResultObject.DATA_TYPE_OBJECT,
                pfKbPartService.selectFaqMedCaseBody(dto.getIdMedCase()));
    }

    /**
     * 批量添加问诊
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @PostMapping(value = "/cons/bach/add")
    public ResultObject bachAddCons(@RequestBody PfSaveAsMedDto dto) {
        /* 参数校验 */
        if (dto.getExtType().equals(YesOrNoNum.NO.getCode())) {
            Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "入参不能为空");
        }
        Assert.isTrue(StringUtils.isNotBlank(dto.getExtType()), "extType");

        if (StringUtils.isNotBlank(dto.getTagFlag()) && dto.getTagFlag().equals(YesOrNoNum.YES.getCode())) {
        } else {
            Assert.isTrue(dto.getExtId() != null, "extId");
        }
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        pfKbPartService.bachAddCons(dto);
        return ResultObject.createSuccess("bachAddCons", ResultObject.DATA_TYPE_OBJECT, true);
    }

    /**
     * 批量添加体格检查
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @PostMapping(value = "/check/bach/add")
    public ResultObject bachAddCheck(@RequestBody PfSaveAsMedDto dto) {
        /* 参数校验 */
        if (dto.getExtType().equals(YesOrNoNum.NO.getCode())) {
            Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "入参不能为空");
        }
        Assert.isTrue(StringUtils.isNotBlank(dto.getExtType()), "extType");

        if (StringUtils.isNotBlank(dto.getTagFlag()) && dto.getTagFlag().equals(YesOrNoNum.YES.getCode())) {
        } else {
            Assert.isTrue(dto.getExtId() != null, "extId");
        }
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        pfKbPartService.bachAddCheck(dto);
        return ResultObject.createSuccess("bachAddCheck", ResultObject.DATA_TYPE_OBJECT, true);
    }

    /**
     * 批量添加辅助检查
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @PostMapping(value = "/exam/bach/add")
    public ResultObject bachAddExam(@RequestBody PfSaveAsMedDto dto) {
        /* 参数校验 */
        if (dto.getExtType().equals(YesOrNoNum.NO.getCode())) {
            Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "入参不能为空");
        }
        Assert.isTrue(StringUtils.isNotBlank(dto.getExtType()), "extType");

        if (StringUtils.isNotBlank(dto.getTagFlag()) && dto.getTagFlag().equals(YesOrNoNum.YES.getCode())) {
        } else {
            Assert.isTrue(dto.getExtId() != null, "extId");
        }
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        pfKbPartService.bachAddExam(dto);
        return ResultObject.createSuccess("bachAddExam", ResultObject.DATA_TYPE_OBJECT, true);
    }


    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/question/list", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject listQuestion(@RequestBody PfPartCommonDto dto) {
        // 默认取1000，一般没这么多
        dto.setPage(1);
        dto.setLimit(1000);
        PageResult pageResult = pfKbPartService.listFaqMedCaseInques(dto);
        return ResultObject.createSuccess("listQuestion", ResultObject.DATA_TYPE_LIST, pageResult.getData());
    }

}
