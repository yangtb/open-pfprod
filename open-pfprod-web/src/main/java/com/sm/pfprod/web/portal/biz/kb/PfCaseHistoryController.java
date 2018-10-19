package com.sm.pfprod.web.portal.biz.kb;

import com.sm.pfprod.model.dto.biz.kb.casehistory.PfCaseHistoryDto;
import com.sm.pfprod.model.enums.SysDicGroupEnum;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.kb.PfCaseHistoryService;
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
    private PfCaseHistoryService pfCaseHistoryService;

    @Resource
    private EnumUtil enumUtil;

    @Resource
    private PfClinicTemplateService pfClinicTemplateService;

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/page")
    public String cataloguePage(Model model) {
        model.addAttribute("caseHistoryLevel", enumUtil.getEnumList(SysDicGroupEnum.CASE_HISTORY_LEVEL.getCode()));
        model.addAttribute("caseHistoryUse", enumUtil.getEnumList(SysDicGroupEnum.CASE_HISTORY_USE.getCode()));
        return "pages/biz/kb/casehistory/caseHistoryTemplate";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/template/list")
    @ResponseBody
    public PageResult listTemplate(PfCaseHistoryDto dto) {
        return pfCaseHistoryService.listTemplate(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @RequestMapping("/template/form")
    public String form(String formType, Model model) {
        model.addAttribute("formType", formType);
        model.addAttribute("baseDemo", pfClinicTemplateService.listAllBasDemo());
        model.addAttribute("caseHistoryLevel", enumUtil.getEnumList(SysDicGroupEnum.CASE_HISTORY_LEVEL.getCode()));
        model.addAttribute("caseHistoryUse", enumUtil.getEnumList(SysDicGroupEnum.CASE_HISTORY_USE.getCode()));
        return "pages/biz/kb/casehistory/caseHistoryTemplateForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @RequestMapping("/form")
    public String tagForm(Long idMedicalrec, Long idDemo, Model model) {
        model.addAttribute("idMedicalrec", idMedicalrec);
        model.addAttribute("idDemo", idDemo);
        model.addAttribute("tags", pfClinicTemplateService.listTagByIdDemo(idDemo));
        return "pages/biz/kb/casehistory/templateTagForm";
    }

}
