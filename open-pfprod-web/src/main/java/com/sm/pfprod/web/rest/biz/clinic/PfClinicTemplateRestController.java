package com.sm.pfprod.web.rest.biz.clinic;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.biz.clinic.PfClinicDimensionDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.*;
import com.sm.pfprod.service.biz.clinic.PfClinicTemplateService;
import com.sm.pfprod.web.security.CurrentUserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfClinicTemplateRestController
 * @Description: 临床模板定义rest
 * @Author yangtongbin
 * @Date 2018/10/8
 */
@Controller
@RequestMapping(value = "/pf/r/clinic")
public class PfClinicTemplateRestController {

    @Resource
    private PfClinicTemplateService pfClinicTemplateService;

    /**
     * 分类树
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0030','ROLE_SUPER')")
    @RequestMapping(value = "/template/classify/tree", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject listClassifyTree() {
        return ResultObject.createSuccess("listClassifyTree", ResultObject.DATA_TYPE_LIST,
                pfClinicTemplateService.listClassifyTree());
    }

    /**
     * 新增分类信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/template/classify/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addClassify(@RequestBody BasDemoCa dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("addClassify", ResultObject.DATA_TYPE_OBJECT,
                pfClinicTemplateService.addClassify(dto));
    }

    /**
     * 编辑分类信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010', 'ROLE_SUPER')")
    @RequestMapping(value = "/template/classify/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editClassify(@RequestBody BasDemoCa dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdDemoCa() != null, "idDemoCa");
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfClinicTemplateService.editClassify(dto) ? ResultObject.createSuccess("editClassify", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("editClassify", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);

    }

    /**
     * 删除分类信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/template/classify/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delClassify(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfClinicTemplateService.delClassify(dto) ? ResultObject.createSuccess("delClassify", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delClassify", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 新增信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/template/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addTemplate(@RequestBody BasDemo dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdDemoCa() != null, "idDemoCa");
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("addTemplate", ResultObject.DATA_TYPE_OBJECT,
                pfClinicTemplateService.addTemplate(dto));
    }

    /**
     * 编辑信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010', 'ROLE_SUPER')")
    @RequestMapping(value = "/template/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editTemplate(@RequestBody BasDemo dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdDemo() != null, "idDemo");
        Assert.isTrue(dto.getIdDemoCa() != null, "idDemoCa");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfClinicTemplateService.editTemplate(dto) ? ResultObject.createSuccess("editTemplate", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("editTemplate", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);

    }

    /**
     * 删除信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/template/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delQuestion(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfClinicTemplateService.delTemplate(dto) ? ResultObject.createSuccess("delQuestion", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delQuestion", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 删除评估标签
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/template/tag/sheet/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delSheetTag(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfClinicTemplateService.delSheetTag(dto) ? ResultObject.createSuccess("delSheetTag", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delSheetTag", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 保存评估标签信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/template/tag/sheet/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject saveSheetTag(@RequestBody BasEvaTag dto) {
        /* 参数校验 */
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("saveSheetTag", ResultObject.DATA_TYPE_OBJECT,
                pfClinicTemplateService.saveSheetTag(dto));
    }

    /**
     * 删除病例标签
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/template/tag/caseHistory/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delCaseHistoryTag(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfClinicTemplateService.delCaseHistoryTag(dto) ? ResultObject.createSuccess("delCaseHistoryTag", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delCaseHistoryTag", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 保存病例标签信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/template/tag/caseHistory/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject saveCaseHistoryTag(@RequestBody BasMedicalTag dto) {
        /* 参数校验 */
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("saveCaseHistoryTag", ResultObject.DATA_TYPE_OBJECT,
                pfClinicTemplateService.saveCaseHistoryTag(dto));
    }

    /**
     * 评估维度树
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0030','ROLE_SUPER')")
    @RequestMapping(value = "/template/tag/dimension/classify/tree", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject listDimensionTree() {
        return ResultObject.createSuccess("listDimensionTree", ResultObject.DATA_TYPE_LIST,
                pfClinicTemplateService.listDimensionTree());
    }

    /**
     * 删除评估维度
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/template/tag/dimension/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delDimensionTag(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfClinicTemplateService.delDimensionTag(dto) ? ResultObject.createSuccess("delDimensionTag", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delDimensionTag", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 保存评估维度
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/template/tag/dimension/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject saveDimensionTag(@RequestBody BasDemoAsses dto) {
        /* 参数校验 */
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("saveDimensionTag", ResultObject.DATA_TYPE_OBJECT,
                pfClinicTemplateService.saveDimensionTag(dto));
    }

    /**
     * 根据id查询评估维度信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/select/tag/dimensionInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject selectDimensionTagInfo(@RequestBody PfClinicDimensionDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdDimemsion() != null, "idDimemsion");
        return ResultObject.createSuccess("selectDimensionTagInfo", ResultObject.DATA_TYPE_OBJECT,
                pfClinicTemplateService.selectDimensionTagInfo(dto.getIdDimemsion()));
    }

}
