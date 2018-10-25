package com.sm.pfprod.web.portal.biz.kb;

import com.alibaba.fastjson.JSON;
import com.sm.pfprod.model.dto.biz.kb.assess.PfEvaCaseDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.clinic.PfClinicPartsService;
import com.sm.pfprod.service.biz.kb.PfKbAssessService;
import com.sm.pfprod.web.portal.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfKbAssessController
 * @Description: 评估组件用例
 * @Author yangtongbin
 * @Date 2018/10/16
 */
@Controller
@RequestMapping(value = "/pf/p/kb/assess")
public class PfKbAssessController extends BaseController {
    @Resource
    private PfClinicPartsService pfClinicPartsService;

    @Resource
    private PfKbAssessService pfKbAssessService;


    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/page")
    public String partPage(Model model) {
        model.addAttribute("assesses", pfClinicPartsService.listAllSheet());
        return "pages/biz/kb/assess/assessTemplate";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listKbAssess(PfEvaCaseDto dto) {
        return pfKbAssessService.listKbAssess(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/form")
    public String form(Model model, String formType) {
        model.addAttribute("formType", formType);
        model.addAttribute("assesses", pfClinicPartsService.listAllSheet());
        return "pages/biz/kb/assess/assessForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/useCase/form")
    public String partForm(Model model, String cdMedAsse, Long idMedCase) {
        model.addAttribute("cdMedAsse", cdMedAsse);
        model.addAttribute("idMedCase", idMedCase);
        model.addAttribute("parts", JSON.parseArray(JSON.toJSONString(pfClinicPartsService.listAllPart())));
        return "pages/biz/kb/assess/assessUseCaseForm";
    }

}
