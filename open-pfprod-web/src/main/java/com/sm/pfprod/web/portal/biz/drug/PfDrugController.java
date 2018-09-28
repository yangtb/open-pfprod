package com.sm.pfprod.web.portal.biz.drug;

import com.sm.pfprod.web.portal.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: PfDiseaseController
 * @Description: 药品相关
 * @Author yangtongbin
 * @Date 2018/9/28
 */
@Controller
@RequestMapping(value = "/pf/p/drug/")
public class PfDrugController extends BaseController {

    @PreAuthorize("hasAnyRole('ROLE_BAS0040','ROLE_SUPER')")
    @RequestMapping("/info/page")
    public String page(Model model) {
        return "pages/biz/ig/ig";
    }


}
