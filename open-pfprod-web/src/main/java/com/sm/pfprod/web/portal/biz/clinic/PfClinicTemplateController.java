package com.sm.pfprod.web.portal.biz.clinic;

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
 * @ClassName: PfClinicTemplateController
 * @Description: 临床模板定义controller
 * @Author yangtongbin
 * @Date 2018/10/8
 */
@Controller
@RequestMapping(value = "/pf/p/clinic")
public class PfClinicTemplateController extends BaseController {

    @Resource
    private PfClinicTemplateService pfClinicTemplateService;

    @Resource
    private EnumUtil enumUtil;

    @PreAuthorize("hasAnyRole('ROLE_STD0020','ROLE_SUPER')")
    @RequestMapping("/template/page")
    public String cataloguePage(Model model) {
        return "pages/biz/clinic/clinicTemplate";
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0020','ROLE_SUPER')")
    @RequestMapping(value = "/template/list")
    @ResponseBody
    public PageResult listTemplate(PfClinicTemplateDto dto) {
        return pfClinicTemplateService.listTemplate(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0020', 'ROLE_SUPER')")
    @RequestMapping("/template/form")
    public String form(String formType, Model model) {
        model.addAttribute("formType", formType);
        return "pages/biz/clinic/clinicTemplateForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0020', 'ROLE_SUPER')")
    @RequestMapping("/template/tag/form")
    public String tagForm(Long idDemo, Model model) {
        model.addAttribute("idDemo", idDemo);
        model.addAttribute("tags", enumUtil.getEnumMap(SysDicGroupEnum.CLINIC_TEMPLATE_TAG.getCode()));
        return "pages/biz/clinic/templateTagForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0020', 'ROLE_SUPER')")
    @RequestMapping("/template/tag/list")
    @ResponseBody
    public PageResult listTag(PfClinicTemplateDto dto) {
        return pfClinicTemplateService.listTag(dto);
    }
}
