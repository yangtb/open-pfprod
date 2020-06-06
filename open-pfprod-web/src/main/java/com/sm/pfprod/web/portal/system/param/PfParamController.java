package com.sm.pfprod.web.portal.system.param;

import com.sm.pfprod.model.dto.system.param.ParamDto;
import com.sm.pfprod.model.enums.SysDicGroupEnum;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.system.param.PfParamService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.util.EnumUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfParamRestController
 * @Description: 参数管理
 * @Author yangtongbin
 * @Date 2017/10/9 11:05
 */
@Controller
@RequestMapping(value = "/pf/p/param")
public class PfParamController extends BaseController {

    @Resource
    private PfParamService pfParamService;
    @Resource
    private EnumUtil enumUtil;

    @PreAuthorize("hasAnyRole('ROLE_PARAM_MG','ROLE_SUPER')")
    @RequestMapping("/page")
    public String page(Model model) {
        model.addAttribute("modelList", enumUtil.getEnumList(SysDicGroupEnum.SYS_PARAM_BIZ_MODUAL.getCode()));
        return "pages/system/param/param";
    }

    @PreAuthorize("hasAnyRole('ROLE_PARAM_MG','ROLE_SUPER')")
    @RequestMapping("/form")
    public String form(String formType, Model model) {
        model.addAttribute("formType", formType);
        model.addAttribute("modelList", enumUtil.getEnumList(SysDicGroupEnum.SYS_PARAM_BIZ_MODUAL.getCode()));
        return "pages/system/param/paramForm";
    }

    /**
     * 获取参数列表
     */
    @PreAuthorize("hasAnyRole('ROLE_PARAM_MG','ROLE_SUPER')")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listDicGroups(ParamDto dto) {
        return pfParamService.listParams(dto);
    }

}
