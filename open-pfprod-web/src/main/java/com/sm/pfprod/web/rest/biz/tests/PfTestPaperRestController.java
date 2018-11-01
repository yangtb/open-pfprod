package com.sm.pfprod.web.rest.biz.tests;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.enums.YesOrNoNum;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.biz.tests.PfAddCaseDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCatalogueTreeDto;
import com.sm.pfprod.model.entity.ExmTestpaper;
import com.sm.pfprod.model.entity.ExmTestpaperCa;
import com.sm.pfprod.model.entity.ExmTestpaperMedicalrec;
import com.sm.pfprod.model.enums.OperationTypeEnum;
import com.sm.pfprod.service.biz.kb.PfCaseHistoryService;
import com.sm.pfprod.service.biz.tests.PfTestPaperService;
import com.sm.pfprod.web.security.CurrentUserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @ClassName: PfTestPaperRestController
 * @Description: 试卷定义rest服务
 * @Author yangtongbin
 * @Date 2018/10/31
 */
@RestController
@RequestMapping(value = "/pf/r/tests/paper")
public class PfTestPaperRestController {

    @Resource
    private PfCaseHistoryService pfCaseHistoryService;

    @Resource
    private PfTestPaperService pfTestPaperService;

    /**
     * 分类树
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0030','ROLE_SUPER')")
    @PostMapping(value = "/classify/tree")
    public ResultObject listPaperClassifyTree() {
        return ResultObject.createSuccess("listPaperClassifyTree", ResultObject.DATA_TYPE_LIST,
                pfTestPaperService.listPaperClassifyTree(CurrentUserUtils.getCurrentUser().getIdOrg()));
    }

    /**
     * 新增分类信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @PostMapping(value = "/classify/add")
    public ResultObject addPaperClassify(@RequestBody ExmTestpaperCa dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setIdOrg(CurrentUserUtils.getCurrentUser().getIdOrg());
        return ResultObject.createSuccess("addPaperClassify", ResultObject.DATA_TYPE_OBJECT,
                pfTestPaperService.savePaperClassify(dto));
    }

    /**
     * 编辑分类信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010', 'ROLE_SUPER')")
    @PostMapping(value = "/classify/edit")
    public ResultObject editPaperClassify(@RequestBody ExmTestpaperCa dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestpaperca() != null, "idTestpaperca");
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("editPaperClassify", ResultObject.DATA_TYPE_OBJECT,
                pfTestPaperService.savePaperClassify(dto));

    }

    /**
     * 删除分类信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @PostMapping(value = "/classify/del")
    public ResultObject delPaperClassify(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfTestPaperService.delPaperClassify(dto) ? ResultObject.createSuccess("delPaperClassify", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delPaperClassify", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 新增信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addPaper(@RequestBody ExmTestpaper dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestpaperca() != null, "idTestpaperca");
        Assert.isTrue(StringUtils.isNotBlank(dto.getNaTestpaper()), "naTestpaper");
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("addPaper", ResultObject.DATA_TYPE_OBJECT,
                pfTestPaperService.savePaper(dto));
    }

    /**
     * 编辑信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010', 'ROLE_SUPER')")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editPaper(@RequestBody ExmTestpaper dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestpaper() != null, "idTestpaper");
        Assert.isTrue(dto.getIdTestpaperca() != null, "idTestpaperca");
        Assert.isTrue(StringUtils.isNotBlank(dto.getNaTestpaper()), "naTestpaper");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("editPaper", ResultObject.DATA_TYPE_OBJECT,
                pfTestPaperService.savePaper(dto));
    }

    /**
     * 删除信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delPaper(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setStatus(YesOrNoNum.YES.getCode());
        return pfTestPaperService.delPaper(dto) ? ResultObject.createSuccess("delPaper", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delPaper", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
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
    public ResultObject updatePaperStatus(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setOperationType(OperationTypeEnum.UPDATE_STATUS.getCode());
        return pfTestPaperService.delPaper(dto) ? ResultObject.createSuccess("updatePaperStatus", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("updatePaperStatus", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 试题病例tree
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @PostMapping(value = "/tree")
    public ResultObject listCaseTree(@RequestBody PfCatalogueTreeDto dto) {
        return ResultObject.createSuccess("listCaseTree", ResultObject.DATA_TYPE_LIST,
                pfTestPaperService.listCaseTree(dto));
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
    public ResultObject addPaperInfo(@RequestBody PfAddCaseDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        Assert.isTrue(dto.getIdTestpaper() != null, "idTestpaper");
        return ResultObject.createSuccess("addPaperInfo", ResultObject.DATA_TYPE_OBJECT,
                pfTestPaperService.addPaperItem(dto));
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
    public ResultObject delPaperItem(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "list");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setStatus(YesOrNoNum.YES.getCode());
        return pfTestPaperService.delPaperItem(dto) ? ResultObject.createSuccess("delPaperItem", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delPaperItem", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
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
    public ResultObject updatePaperItemSort(@RequestBody ExmTestpaperMedicalrec dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdTestpaperMedicalrec() != null, "idTestpaperMedicalrec");
        Assert.isTrue(dto.getSort() != null, "sort");
        return ResultObject.createSuccess("updatePaperItemSort", ResultObject.DATA_TYPE_OBJECT,
                pfTestPaperService.updatePaperItemSort(dto));
    }

}
