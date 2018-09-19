package com.sm.pfprod.web.rest.system.param;

import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.system.param.PfParamListDto;
import com.sm.pfprod.model.entity.SysParam;
import com.sm.pfprod.service.system.param.PfParamService;
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
 * @ClassName: PfParamRestController
 * @Description: 参数管理
 * @Author yangtongbin
 * @Date 2017/10/9 11:05
 */
@Controller
@RequestMapping(value = "/pf/r/param")
public class PfParamRestController {

    @Resource
    private PfParamService pfParamService;

    /**
     * 新增参数
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_PARAM_ADD','ROLE_SUPER')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addParam(@RequestBody SysParam dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getParamCode()), "paramCode");
        Assert.isTrue(StringUtils.isNotBlank(dto.getParamName()), "paramName");
        Assert.isTrue(StringUtils.isNotBlank(dto.getSysType()), "sysType");
        Assert.isTrue(StringUtils.isNotBlank(dto.getBizModule()), "bizModule");
        Assert.isTrue(StringUtils.isNotBlank(dto.getDataType()), "dataType");
        Assert.isTrue(StringUtils.isNotBlank(dto.getDefaultValue()), "defaultValue");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.create("addParam", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfParamService.addParam(dto));
    }

    /**
     * 编辑参数
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_PARAM_EDIT','ROLE_SUPER')")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editParam(@RequestBody SysParam dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getParamCode()), "paramCode");
        Assert.isTrue(StringUtils.isNotBlank(dto.getParamName()), "paramName");
        Assert.isTrue(StringUtils.isNotBlank(dto.getSysType()), "sysType");
        Assert.isTrue(StringUtils.isNotBlank(dto.getBizModule()), "bizModule");
        Assert.isTrue(StringUtils.isNotBlank(dto.getDataType()), "dataType");
        Assert.isTrue(StringUtils.isNotBlank(dto.getDefaultValue()), "defaultValue");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.create("editParam", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfParamService.editParam(dto));
    }

    /**
     * 停用、启用参数
     *
     * @param dto 参数id集合
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_PARAM_CHANGESTATUS','ROLE_SUPER')")
    @RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject changeStatus(@RequestBody PfParamListDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "入参不能为空");
        return ResultObject.create("changeStatus", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfParamService.changeStatus(dto));
    }

}
