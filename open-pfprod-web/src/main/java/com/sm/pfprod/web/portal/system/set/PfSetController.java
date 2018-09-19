package com.sm.pfprod.web.portal.system.set;

import com.alibaba.fastjson.JSON;
import com.sm.pfprod.service.system.set.PfSetService;
import com.sm.pfprod.web.portal.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @ClassName: PfSetController
 * @Description: 站点设置
 * @Author yangtongbin
 * @Date 2018/9/16 00:12
 */
@Controller
@RequestMapping(value = "/pf/p/set")
public class PfSetController extends BaseController {

    @Value("${website.name}")
    private String websiteName;

    @Resource
    private PfSetService pfSetService;

    @PreAuthorize("hasAnyRole('ROLE_EMAIL_MG', 'ROLE_SET_MG' ,'ROLE_SUPER')")
    @RequestMapping("/email/page")
    public String emailForm(Model model) {
        model.addAttribute("websiteName", websiteName);
        model.addAttribute("emailInfo", JSON.toJSONString(pfSetService.selectEmailSet()));
        return "pages/system/set/emailSetForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_SMS_MG', 'ROLE_SET_MG' ,'ROLE_SUPER')")
    @RequestMapping("/sms/page")
    public String smsForm() {
        return "pages/system/set/smsSetForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_WEBSITE_MG', 'ROLE_SET_MG' ,'ROLE_SUPER')")
    @RequestMapping("/website/page")
    public String websiteForm(Model model) {
        model.addAttribute("websiteInfo", JSON.toJSONString(pfSetService.selectWebsiteSet()));
        return "pages/system/set/websiteSetForm";
    }
}
