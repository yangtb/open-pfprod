package com.sm.pfprod.web.portal.biz.kb;

import com.alibaba.fastjson.JSON;
import com.sm.open.core.facade.model.result.pf.biz.clinic.BasEvaTagVoResult;
import com.sm.pfprod.model.dto.biz.clinic.PfClinicTemplateDto;
import com.sm.pfprod.model.dto.biz.kb.casehistory.PfCaseHistoryDto;
import com.sm.pfprod.model.enums.SysDicGroupEnum;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.clinic.PfClinicTemplateService;
import com.sm.pfprod.service.biz.kb.PfCaseHistoryService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.security.CurrentUserUtils;
import com.sm.pfprod.web.util.EnumUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

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

    @PreAuthorize("hasAnyRole('ROLE_FAQ0040','ROLE_SUPER')")
    @RequestMapping("/page")
    public String cataloguePage(Model model, String previewFlag) {
        model.addAttribute("currentIdOrg", CurrentUserUtils.getCurrentUser().getIdOrg());
        model.addAttribute("caseHistoryLevel", enumUtil.getEnumList(SysDicGroupEnum.CASE_HISTORY_LEVEL.getCode()));
        model.addAttribute("caseHistoryUse", enumUtil.getEnumList(SysDicGroupEnum.CASE_HISTORY_USE.getCode()));
        return "pages/biz/kb/casehistory/caseHistoryTemplate";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0040','ROLE_SUPER')")
    @RequestMapping(value = "/template/list")
    @ResponseBody
    public PageResult listTemplate(PfCaseHistoryDto dto) {
        return pfCaseHistoryService.listTemplate(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0040', 'ROLE_SUPER')")
    @RequestMapping("/template/form")
    public String form(String formType, Model model) {
        model.addAttribute("formType", formType);
        model.addAttribute("baseDemo", pfClinicTemplateService.listAllBasDemo());
        model.addAttribute("caseHistoryLevel", enumUtil.getEnumList(SysDicGroupEnum.CASE_HISTORY_LEVEL.getCode()));
        model.addAttribute("caseHistoryUse", enumUtil.getEnumList(SysDicGroupEnum.CASE_HISTORY_USE.getCode()));
        return "pages/biz/kb/casehistory/caseHistoryTemplateForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0040', 'ROLE_SUPER')")
    @RequestMapping("/form")
    public String tagForm(Long idMedicalrec, Long idDemo, Model model, String caseName, String previewFlag) {
        model.addAttribute("idMedicalrec", idMedicalrec);
        model.addAttribute("idDemo", idDemo);
        model.addAttribute("caseName", caseName);
        model.addAttribute("previewFlag", previewFlag);
        model.addAttribute("tags", JSON.parseArray(JSON.toJSONString(pfCaseHistoryService.listAllCaseHistoryTag(idDemo, idMedicalrec))));
        model.addAttribute("assessTags", JSON.parseArray(JSON.toJSONString(pfCaseHistoryService.listAllAssessTag(idDemo, idMedicalrec))));
        return "pages/biz/kb/casehistory/casehistoryTagForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0040', 'ROLE_SUPER')")
    @RequestMapping("/tag/caseHistory/form")
    public String tagCaseHistoryForm(Long idMedicalrec, Long idDemo, Model model) {
        model.addAttribute("idMedicalrec", idMedicalrec);
        model.addAttribute("idDemo", idDemo);
        model.addAttribute("tags", JSON.parseArray(JSON.toJSONString(pfClinicTemplateService.listAllCaseHistoryTag(idDemo))));
        return "pages/biz/kb/casehistory/tagCaseHistoryForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0040', 'ROLE_SUPER')")
    @RequestMapping("/tag/assess/form")
    public String tagSheetForm(Long idMedicalrec, Long idDemo, Model model) {
        model.addAttribute("idMedicalrec", idMedicalrec);
        model.addAttribute("idDemo", idDemo);
        PfClinicTemplateDto dto = new PfClinicTemplateDto();
        dto.setIdDemo(idDemo);
        dto.setPage(1);
        dto.setLimit(10);
        PageResult pageResult = pfClinicTemplateService.listSheetTag(dto);
        List<BasEvaTagVoResult> list = pageResult.getData();
        model.addAttribute("tags", JSON.parseArray(JSON.toJSONString(list)));
        return "pages/biz/kb/casehistory/tagAssessForm";
    }

}
