package com.sm.pfprod.web.portal.dic;

import com.sm.pfprod.facade.dic.PfDicFacade;
import com.sm.pfprod.model.dto.system.dic.PfDicDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.web.portal.BaseController;
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
@RequestMapping(value = "/pf/p/dic")
public class PfDicController extends BaseController {

    @Resource
    private PfDicFacade pfDicFacade;

    @RequestMapping("/page")
    public String page() {
        return "pages/dic/dicPage";
    }

    @RequestMapping("/form")
    public String form(String formType, Model model) {
        model.addAttribute("formType", formType);
        return "pages/dic/dicForm";
    }

    @RequestMapping("/enum/form")
    public String enumForm(String formType, Model model) {
        model.addAttribute("formType", formType);
        return "pages/dic/enumForm";
    }

    /**
     * 获取字典分组
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listDicGroups(PfDicDto dto) {
        return pfDicFacade.listDicGroups(dto);
    }

    /**
     * 获取字典枚举
     */
    @RequestMapping(value = "/enum/list")
    @ResponseBody
    public PageResult listEnums(PfDicDto dto) {
        return pfDicFacade.listEnums(dto);
    }
}
