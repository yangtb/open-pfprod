package com.sm.pfprod.web.system.param;

import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.facade.param.PfParamFacade;
import com.sm.pfprod.model.dto.system.param.ParamDto;
import com.sm.pfprod.model.dto.common.PfCommonListDto;
import com.sm.pfprod.model.dto.system.param.PfParamListDto;
import com.sm.pfprod.model.entity.SysParam;
import com.sm.pfprod.model.enums.SysDicGroupEnum;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.web.security.CurrentUserUtils;
import com.sm.pfprod.web.system.BaseController;
import com.sm.pfprod.web.system.util.EnumUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping(value = "/param")
public class PfParamRestController extends BaseController {

    @Resource
    private PfParamFacade pfParamFacade;

    @RequestMapping("/page")
    public String page(Model model) {
        model.addAttribute("modualMap", EnumUtil.getEnumMap(SysDicGroupEnum.SYS_PARAM_BIZ_MODUAL.getCode()));
        return "pages/param/param";
    }

    @RequestMapping("/form")
    public String form(String formType, Model model) {
        model.addAttribute("formType", formType);
        model.addAttribute("modualMap", EnumUtil.getEnumMap(SysDicGroupEnum.SYS_PARAM_BIZ_MODUAL.getCode()));
        return "pages/param/paramForm";
    }

    /**
     * 获取参数列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listDicGroups(ParamDto dto) {
        return pfParamFacade.listParams(dto);
    }

    /**
     * 新增参数
     *
     * @param dto
     * @return
     */
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
                ResultObject.DATA_TYPE_OBJECT, pfParamFacade.addParam(dto));
    }

    /**
     * 编辑参数
     *
     * @param dto
     * @return
     */
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
                ResultObject.DATA_TYPE_OBJECT, pfParamFacade.editParam(dto));
    }

    /**
     * 停用、启用参数
     *
     * @param dto 参数id集合
     * @return
     */
    @RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject changeStatus(@RequestBody PfParamListDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "入参不能为空");
        return ResultObject.create("changeStatus", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfParamFacade.changeStatus(dto));
    }

}
