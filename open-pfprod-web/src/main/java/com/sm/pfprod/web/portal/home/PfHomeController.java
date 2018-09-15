package com.sm.pfprod.web.portal.home;

import com.sm.pfprod.service.user.menu.PfMenuService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.security.CurrentUserUtils;
import com.sm.pfprod.web.security.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @ClassName: PfHomeController
 * @Description: 主页
 * @Author yangtongbin
 * @Date 2017/9/4 15:20
 */
@Controller
public class PfHomeController extends BaseController {

    @Resource
    private PfMenuService pfMenuService;

    @RequestMapping("/index")
    public String index(Model model) {
        boolean isSuper = SecurityContext.hasRole("ROLE_SUPER");
        model.addAttribute("menus", pfMenuService.listMyMenus(isSuper, CurrentUserUtils.getCurrentUserId()));
        model.addAttribute("username", CurrentUserUtils.getCurrentUser().getUsername());
        return "/home/index";
    }

    @RequestMapping("/main")
    public String main() {
        return "/pages/main";
    }
}
