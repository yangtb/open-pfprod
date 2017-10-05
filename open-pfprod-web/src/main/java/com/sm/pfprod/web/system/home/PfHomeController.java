package com.sm.pfprod.web.system.home;

import com.alibaba.fastjson.JSON;
import com.sm.pfprod.facade.menu.PfMenuFacade;
import com.sm.pfprod.web.security.CurrentUserUtils;
import com.sm.pfprod.web.system.BaseController;
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
    private PfMenuFacade pfMenuFacade;

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("menus", pfMenuFacade.listMyMenus(CurrentUserUtils.getCurrentUserId()));
        model.addAttribute("username", CurrentUserUtils.getCurrentUser().getUsername());
        return "/home/index";
    }

    @RequestMapping("/main")
    public String main() {
        return "/pages/main";
    }
}
