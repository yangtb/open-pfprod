package com.sm.pfprod.web.portal.user.menu;

import com.sm.pfprod.model.dto.user.menu.MenuDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.user.menu.PfMenuService;
import com.sm.pfprod.web.portal.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfMenuRestController
 * @Description: 菜单
 * @Author yangtongbin
 * @Date 2017/9/8 09:57
 */
@Controller
@RequestMapping(value = "/pf/p/menu")
public class PfMenuController extends BaseController {

    @Resource
    private PfMenuService pfMenuService;

    @PreAuthorize("hasAnyRole('ROLE_MENU_MG','ROLE_SUPER')")
    @RequestMapping("/page")
    public String menu() {
        return "pages/menu/menu";
    }

    @PreAuthorize("hasAnyRole('ROLE_MENU_MG','ROLE_SUPER')")
    @RequestMapping("/form")
    public String form(String formType, Model model) {
        model.addAttribute("formType", formType);
        return "pages/menu/menuForm";
    }

    /**
     * 获取系统菜单
     */
    @PreAuthorize("hasAnyRole('ROLE_MENU_MG','ROLE_SUPER')")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listMenus(MenuDto dto) {
        return pfMenuService.listMenus(dto);
    }

}
