package com.sm.pfprod.web.portal.common;

import com.sm.pfprod.web.portal.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: PfCommonController
 * @Description:
 * @Author yangtongbin
 * @Date 2018/9/28
 */
@Controller
public class PfCommonController extends BaseController {

    @RequestMapping("/403")
    public String forbidden() {
        return "error/403";
    }

    @RequestMapping("/404")
    public String notFound() {
        return "error/404";
    }

    @RequestMapping("/500")
    public String internalServerError() {
        return "error/500";
    }

    @RequestMapping("/video/form")
    public String videoForm(Model model, String path) {
        model.addAttribute("path", path);
        return "pages/common/videoForm";
    }

    @RequestMapping("/empty/page")
    public String emptyPage(Model model, String path) {
        return "pages/common/emptyPage";
    }
}
