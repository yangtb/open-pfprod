package com.sm.pfprod.web.portal.biz.casehistory;

import com.sm.pfprod.model.dto.biz.clinic.PfClinicTemplateDto;
import com.sm.pfprod.model.enums.SysDicGroupEnum;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.clinic.PfClinicTemplateService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.util.EnumUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfCaseHistoryController
 * @Description: 临床知识库
 * @Author yangtongbin
 * @Date 2018/10/9
 */
@Controller
@RequestMapping(value = "/pf/p/case/history")
public class PfCaseHistoryController extends BaseController {

    @Resource
    private PfClinicTemplateService pfClinicTemplateService;

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/page")
    public String cataloguePage(Model model) {
        return "pages/biz/casehistory/caseHistoryTemplate";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listTemplate(PfClinicTemplateDto dto) {
        return pfClinicTemplateService.listTemplate(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @RequestMapping("/form")
    public String form(String formType, Model model) {
        model.addAttribute("formType", formType);
        return "pages/biz/casehistory/caseHistoryTemplateForm";
    }


}
