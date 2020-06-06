package com.sm.pfprod.web.portal.biz.ig;

import com.sm.pfprod.web.portal.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: PfIntelligentGuidanceController
 * @Description: 智能导诊
 * @Author yangtongbin
 * @Date 2018/9/11 09:09
 */
@Controller
@RequestMapping(value = "/pf/p/biz/ig")
public class PfIntelligentGuidanceController extends BaseController {


    @PreAuthorize("hasAnyRole('ROLE_IG_MG','ROLE_SUPER')")
    @RequestMapping("/page")
    public String page(Model model) {
        return "pages/biz/ig/ig";
    }


}
