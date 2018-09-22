package com.sm.pfprod.web.portal.system.org;

import com.sm.pfprod.model.dto.system.org.PfOrgDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.system.org.PfOrgService;
import com.sm.pfprod.web.portal.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfOrgController
 * @Description: 机构管理
 * @Author yangtongbin
 * @Date 2018/9/20 10:47
 */
@Controller
@RequestMapping(value = "/pf/p/org")
public class PfOrgController extends BaseController {

    @Resource
    private PfOrgService pfOrgService;

    @PreAuthorize("hasAnyRole('ROLE_ORG_MG','ROLE_SUPER')")
    @RequestMapping("/page")
    public String page() {
        return "pages/system/org/org";
    }

    @PreAuthorize("hasAnyRole('ROLE_ORG_MG','ROLE_SUPER')")
    @RequestMapping("/form")
    public String form(String formType, Model model) {
        model.addAttribute("formType", formType);
        return "pages/system/org/orgForm";
    }

    /**
     * 获取结构列表
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ORG_MG','ROLE_SUPER')")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listOrgs(PfOrgDto dto) {
        return pfOrgService.listOrgs(dto);
    }

}
