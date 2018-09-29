package com.sm.pfprod.web.rest.biz.drug;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.BasDrugs;
import com.sm.pfprod.service.biz.drug.PfDrugService;
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
 * @Description: 药品rest服务
 * @Author yangtongbin
 * @Date 2018/9/28
 */
@Controller
@RequestMapping(value = "/pf/r/drug")
public class PfDrugRestController {

    @Resource
    private PfDrugService pfDrugService;

    /**
     * 新增药品信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_DRUG_INFO_ADD','ROLE_SUPER')")
    @RequestMapping(value = "/info/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addDrugInfo(@RequestBody BasDrugs dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfDrugService.addDrugInfo(dto) ? ResultObject.createSuccess("addDrugInfo", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("addDrugInfo", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 编辑药品信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_DRUG_INFO_EDIT', 'ROLE_SUPER')")
    @RequestMapping(value = "/info/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editDrugInfo(@RequestBody BasDrugs dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdDrugs() != null, "idDrug");
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfDrugService.editDrugInfo(dto) ? ResultObject.createSuccess("editDrugInfo", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("editDrugInfo", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);

    }

    /**
     * 删除药品信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_DRUG_INFO_DEL','ROLE_SUPER')")
    @RequestMapping(value = "/info/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delDrugInfo(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "入参不能为空");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfDrugService.delDrugInfo(dto) ? ResultObject.createSuccess("delDrugInfo", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delDrugInfo", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }
    

}
