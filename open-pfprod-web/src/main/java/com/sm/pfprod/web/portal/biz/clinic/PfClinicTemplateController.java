package com.sm.pfprod.web.portal.biz.clinic;

import com.alibaba.fastjson.JSON;
import com.sm.pfprod.model.dto.biz.clinic.PfClinicTemplateDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.clinic.PfClinicPartsService;
import com.sm.pfprod.service.biz.clinic.PfClinicTemplateService;
import com.sm.pfprod.web.portal.BaseController;
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
    private PfClinicPartsService pfClinicPartsService;


    @PreAuthorize("hasAnyRole('ROLE_CLINIC0040','ROLE_SUPER')")
    @RequestMapping("/template/page")
    public String cataloguePage(Model model) {
        return "pages/biz/clinic/clinicTemplate";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLINIC0040','ROLE_SUPER')")
    @RequestMapping(value = "/template/list")
    @ResponseBody
    public PageResult listTemplate(PfClinicTemplateDto dto) {
        return pfClinicTemplateService.listTemplate(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLINIC0040', 'ROLE_SUPER')")
    @RequestMapping("/template/form")
    public String form(String formType, Model model) {
        model.addAttribute("formType", formType);
        return "pages/biz/clinic/clinicTemplateForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLINIC0040', 'ROLE_SUPER')")
    @RequestMapping("/template/tag/caseHistory/form")
    public String caseHistoryForm(Long idDemo, Model model) {
        model.addAttribute("idDemo", idDemo);
        model.addAttribute("parts", pfClinicPartsService.listAllPart());
        return "pages/biz/clinic/tagCaseHistoryForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLINIC0040', 'ROLE_SUPER')")
    @RequestMapping("/template/tag/flow/form")
    public String flowForm(Long idDemo, Model model) {
        model.addAttribute("idDemo", idDemo);
        model.addAttribute("tags", JSON.parseArray(JSON.toJSONString(pfClinicTemplateService.listAllCaseHistoryTag(idDemo))));
        return "pages/biz/clinic/tagFlowForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLINIC0040', 'ROLE_SUPER')")
    @RequestMapping("/template/tag/sheet/form")
    public String sheetForm(Long idDemo, Model model) {
        model.addAttribute("idDemo", idDemo);
        model.addAttribute("sheets", pfClinicPartsService.listAllSheet());
        return "pages/biz/clinic/tagSheetForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLINIC0040', 'ROLE_SUPER')")
    @RequestMapping("/template/tag/dimension/form")
    public String dimensionForm(Long idDemo, Model model) {
        model.addAttribute("idDemo", idDemo);
        model.addAttribute("algorithms", pfClinicPartsService.listAllAlgorithm());
        return "pages/biz/clinic/tagDimensionForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLINIC0040', 'ROLE_SUPER')")
    @RequestMapping("/template/tag/form")
    public String tagForm(Long idDemo, Model model) {
        model.addAttribute("idDemo", idDemo);
        return "pages/biz/clinic/clinicTagForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLINIC0040', 'ROLE_SUPER')")
    @RequestMapping("/template/tag/sheet/list")
    @ResponseBody
    public PageResult listSheetTag(PfClinicTemplateDto dto) {
        return pfClinicTemplateService.listSheetTag(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLINIC0040', 'ROLE_SUPER')")
    @RequestMapping("/template/tag/caseHistory/list")
    @ResponseBody
    public PageResult listCaseHistoryTag(PfClinicTemplateDto dto) {
        return pfClinicTemplateService.listCaseHistoryTag(dto);
    }
}
