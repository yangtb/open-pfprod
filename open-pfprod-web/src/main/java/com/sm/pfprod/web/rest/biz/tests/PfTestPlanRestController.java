package com.sm.pfprod.web.rest.biz.tests;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.enums.YesOrNoNum;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.biz.tests.PfAddCaseDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCatalogueTreeDto;
import com.sm.pfprod.model.entity.ExmTestplan;
import com.sm.pfprod.model.entity.ExmTestplanMedicalrec;
import com.sm.pfprod.model.enums.OperationTypeEnum;
import com.sm.pfprod.service.biz.tests.PfTestPlanService;
import com.sm.pfprod.web.security.CurrentUserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/pf/r/test/plan")
public class PfTestPlanRestController {

    @Resource
    private PfTestPlanService pfTestPlanService;

    /**
     * 新增计划
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addPlan(@RequestBody ExmTestplan dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getNaTestplan()), "naTestplan");
        Assert.isTrue(dto.getIdTestpaper() != null, "idTestpaper");
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        return ResultObject.createSuccess("addPlan", ResultObject.DATA_TYPE_OBJECT,
                pfTestPlanService.savePlan(dto));
    }

    /**
     * 编辑计划
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010', 'ROLE_SUPER')")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editPlan(@RequestBody ExmTestplan dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestplan() != null, "idTestplan");
        Assert.isTrue(dto.getIdTestpaper() != null, "idTestpaper");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("editPlan", ResultObject.DATA_TYPE_OBJECT,
                pfTestPlanService.savePlan(dto));
    }

    /**
     * 删除计划
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delPlan(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setStatus(YesOrNoNum.YES.getCode());
        return pfTestPlanService.delPlan(dto) ? ResultObject.createSuccess("delPlan", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delPlan", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 停用、启用
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updatePlanStatus(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setOperationType(OperationTypeEnum.UPDATE_STATUS.getCode());
        return pfTestPlanService.delPlan(dto) ? ResultObject.createSuccess("updatePlanStatus", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("updatePlanStatus", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 试题病例tree
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @PostMapping(value = "/tree")
    public ResultObject listCaseTree(@RequestBody PfCatalogueTreeDto dto) {
        return ResultObject.createSuccess("listCaseTree", ResultObject.DATA_TYPE_LIST,
                pfTestPlanService.listCaseTree(dto));
    }

    /**
     * 添加试题清单
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010', 'ROLE_SUPER')")
    @RequestMapping(value = "/item/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addPlanItem(@RequestBody PfAddCaseDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        Assert.isTrue(dto.getIdTestplan() != null, "idTestplan");
        return ResultObject.createSuccess("addPlanItem", ResultObject.DATA_TYPE_OBJECT,
                pfTestPlanService.addPlanItem(dto));
    }

    /**
     * 删除试题
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/item/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delPlanItem(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setStatus(YesOrNoNum.YES.getCode());
        return pfTestPlanService.delPlanItem(dto) ? ResultObject.createSuccess("delPlanItem", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delPlanItem", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 修改试题排序
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010', 'ROLE_SUPER')")
    @RequestMapping(value = "/item/update/sort", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updatePaperItemSort(@RequestBody ExmTestplanMedicalrec dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestplanMedicalrec() != null, "idTestplanMedicalrec");
        Assert.isTrue(dto.getSort() != null, "sort");
        return ResultObject.createSuccess("updatePlanItemSort", ResultObject.DATA_TYPE_OBJECT,
                pfTestPlanService.updatePlanItemSort(dto));
    }

    /**
     * 班级-学生tree
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @PostMapping(value = "/student/tree")
    public ResultObject listStudentTree(@RequestBody PfCatalogueTreeDto dto) {
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        return ResultObject.createSuccess("listStudentTree", ResultObject.DATA_TYPE_LIST,
                pfTestPlanService.listStudentTree(dto));
    }

    /**
     * 添加计划学生
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010', 'ROLE_SUPER')")
    @RequestMapping(value = "/student/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addPlanStudent(@RequestBody PfAddCaseDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        Assert.isTrue(dto.getIdTestplan() != null, "idTestplan");
        return ResultObject.createSuccess("addPlanStudent", ResultObject.DATA_TYPE_OBJECT,
                pfTestPlanService.addPlanStudent(dto));
    }

    /**
     * 删除计划学生
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/student/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delPlanStudent(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setStatus(YesOrNoNum.YES.getCode());
        return pfTestPlanService.delPlanStudent(dto) ? ResultObject.createSuccess("delPlanStudent", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delPlanStudent", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 生成计划
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010', 'ROLE_SUPER')")
    @RequestMapping(value = "/detail/generate", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject generatePlan(@RequestBody PfAddCaseDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestplan() != null, "idTestplan");
        return ResultObject.createSuccess("addPlanStudent", ResultObject.DATA_TYPE_OBJECT,
                pfTestPlanService.generatePlan(dto.getIdTestplan()));
    }

}
