package com.sm.pfprod.web.portal.user.role;

import com.sm.pfprod.facade.role.PfRoleFacade;
import com.sm.pfprod.model.dto.user.role.PfRoleDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.web.portal.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfRoleRestController
 * @Description: 角色模块
 * @Author yangtongbin
 * @Date 2017/9/17 23:13
 */
@Controller
@RequestMapping(value = "/pf/p/role")
public class PfRoleController extends BaseController {

    @Resource
    private PfRoleFacade pfRoleFacade;

    @PreAuthorize("hasAnyRole('ROLE_ROLE_MG','ROLE_SUPER')")
    @RequestMapping("/page")
    public String page() {
        return "pages/role/role";
    }

    @PreAuthorize("hasAnyRole('ROLE_ROLE_MG','ROLE_SUPER')")
    @RequestMapping("/form")
    public String form(String formType, Model model) {
        model.addAttribute("formType", formType);
        return "pages/role/roleForm";
    }

    /**
     * 获取所有角色
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ROLE_MG','ROLE_SUPER')")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listRoles(PfRoleDto dto) {
        return pfRoleFacade.listRoles(dto);
    }

}
