package com.sm.pfprod.web.rest.biz.disease;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.biz.disease.PfDiseaseCatalogueDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCatalogueTreeDto;
import com.sm.pfprod.model.dto.common.PfChangeStatusDto;
import com.sm.pfprod.model.entity.BasDie;
import com.sm.pfprod.model.entity.BasDieClass;
import com.sm.pfprod.service.biz.disease.PfDiseaseService;
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
 * @ClassName: PfDrugRestController
 * @Description: 疾病rest服务
 * @Author yangtongbin
 * @Date 2018/9/28
 */
@Controller
@RequestMapping(value = "/pf/r/disease")
public class PfDiseaseRestController {

    @Resource
    private PfDiseaseService pfDiseaseService;

    /**
     * 疾病目录树
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_BAS0030','ROLE_SUPER')")
    @RequestMapping(value = "/catalogue/tree", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject listDiseaseCatalogueTree(PfCatalogueTreeDto dto) {
        return ResultObject.createSuccess("listDiseaseCatalogueTree", ResultObject.DATA_TYPE_LIST,
                pfDiseaseService.listDiseaseCatalogueTree(dto));
    }

    /**
     * 疾病目录详情
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_BAS0030','ROLE_SUPER')")
    @RequestMapping(value = "/catalogue/detail", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject selectDiseaseDetail(@RequestBody PfDiseaseCatalogueDto dto) {
        Assert.isTrue(dto.getIdDieclass() != null, "idDieClass");
        return ResultObject.createSuccess("selectDiseaseDetail", ResultObject.DATA_TYPE_OBJECT,
                pfDiseaseService.selectDiseaseDetail(dto.getIdDieclass()));
    }

    /**
     * 保存疾病目录
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_DISEASE_CATALOGUE_SAVE','ROLE_SUPER')")
    @RequestMapping(value = "/catalogue/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject saveDiseaseCatalogue(@RequestBody BasDieClass dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        Assert.isTrue(StringUtils.isNotBlank(dto.getCd()), "cd");
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("saveDiseaseCatalogue", ResultObject.DATA_TYPE_OBJECT,
                pfDiseaseService.saveDiseaseCatalogue(dto));
    }

    /**
     * 删除疾病目录
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_DISEASE_CATALOGUE_DEL','ROLE_SUPER')")
    @RequestMapping(value = "/catalogue/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delDiseaseCatalogue(@RequestBody PfChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getIdStr()), "idStr");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfDiseaseService.delDiseaseCatalogue(dto) ? ResultObject.createSuccess("delDiseaseCatalogue", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delDiseaseCatalogue", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 新增疾病信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_DISEASE_INFO_ADD','ROLE_SUPER')")
    @RequestMapping(value = "/info/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addDiseaseInfo(@RequestBody BasDie dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfDiseaseService.addDiseaseInfo(dto) ? ResultObject.createSuccess("addDiseaseInfo", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("addDiseaseInfo", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 编辑疾病信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_DISEASE_INFO_EDIT', 'ROLE_SUPER')")
    @RequestMapping(value = "/info/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editDiseaseInfo(@RequestBody BasDie dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdDie() != null, "idDie");
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfDiseaseService.editDiseaseInfo(dto) ? ResultObject.createSuccess("editDiseaseInfo", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("editDiseaseInfo", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);

    }

    /**
     * 删除疾病信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_DISEASE_INFO_DEL','ROLE_SUPER')")
    @RequestMapping(value = "/info/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delDiseaseInfo(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "入参不能为空");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfDiseaseService.delDiseaseInfo(dto) ? ResultObject.createSuccess("delDiseaseInfo", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delDiseaseInfo", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }


}
