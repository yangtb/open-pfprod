package com.sm.pfprod.web.portal.biz.kb;

import com.alibaba.fastjson.JSON;
import com.sm.open.care.core.enums.YesOrNoNum;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.biz.kb.part.PfMedCaseDto;
import com.sm.pfprod.model.dto.biz.kb.part.PfPartCommonDto;
import com.sm.pfprod.model.dto.biz.kb.part.PfPartGetCommonDto;
import com.sm.pfprod.model.dto.common.PfCommonSearchDto;
import com.sm.pfprod.model.entity.FaqMedCaseInquesList;
import com.sm.pfprod.model.enums.SysDicGroupEnum;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.check.PfCheckService;
import com.sm.pfprod.service.biz.clinic.PfClinicPartsService;
import com.sm.pfprod.service.biz.exam.PfExamService;
import com.sm.pfprod.service.biz.inquisition.PfInquisitionService;
import com.sm.pfprod.service.biz.kb.PfKbPartService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.security.CurrentUserUtils;
import com.sm.pfprod.web.security.User;
import com.sm.pfprod.web.util.EnumUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfCaseHistoryPartController
 * @Description: 病例组件用例
 * @Author yangtongbin
 * @Date 2018/10/16
 */
@Controller
@RequestMapping(value = "/pf/p/kb/part")
public class PfKbPartController extends BaseController {

    @Resource
    private PfClinicPartsService pfClinicPartsService;

    @Resource
    private PfKbPartService pfKbPartService;

    @Resource
    private PfInquisitionService pfInquisitionService;

    @Resource
    private PfExamService pfExamService;

    @Resource
    private PfCheckService pfCheckService;

    @Resource
    private EnumUtil enumUtil;

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/page")
    public String partPage(Model model) {
        model.addAttribute("currentIdOrg", CurrentUserUtils.getCurrentUser().getIdOrg());
        model.addAttribute("parts", pfClinicPartsService.listAllPart());
        return "pages/biz/kb/part/partTemplate";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listKbPart(PfMedCaseDto dto) {
        User user = CurrentUserUtils.getCurrentUser();
        if (user.getFgPlat().equals(YesOrNoNum.NO.getCode())) {
            dto.setIdOrg(user.getIdOrg());
        }
        return pfKbPartService.listKbPart(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/form")
    public String form(Model model, String formType, String cdMedAsse, String previewFlag) {
        model.addAttribute("formType", formType);
        model.addAttribute("cdMedAsse", cdMedAsse);
        model.addAttribute("previewFlag", previewFlag);
        model.addAttribute("parts", pfClinicPartsService.listAllPart());
        return "pages/biz/kb/part/partForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/useCase/form")
    public String partForm(Model model, String cdMedAsse, Long idMedCase, String previewFlag) {
        model.addAttribute("cdMedAsse", cdMedAsse);
        model.addAttribute("idMedCase", idMedCase);
        model.addAttribute("previewFlag", previewFlag);
        model.addAttribute("parts", JSON.parseArray(JSON.toJSONString(pfClinicPartsService.listAllPart())));
        return "pages/biz/kb/part/partUseCaseForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/cons/list")
    @ResponseBody
    public PageResult listFaqMedCaseInques(PfPartCommonDto dto) {
        return pfKbPartService.listFaqMedCaseInques(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/define/text/form")
    public String textForm(Model model, PfPartGetCommonDto dto) {
        model.addAttribute("showBtn", dto.getShowBtn());
        model.addAttribute("previewFlag", dto.getPreviewFlag());
        model.addAttribute("idMedCase", dto.getIdMedCase());
        model.addAttribute("tagFlag", dto.getTagFlag());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTag", dto.getIdTag());
        model.addAttribute("caseName", dto.getCaseName());
        return "pages/biz/kb/part/define/textForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/define/pic/form")
    public String picForm(Model model, PfPartGetCommonDto dto) {
        model.addAttribute("showBtn", dto.getShowBtn());
        model.addAttribute("previewFlag", dto.getPreviewFlag());
        model.addAttribute("idMedCase", dto.getIdMedCase());
        model.addAttribute("tagFlag", dto.getTagFlag());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTag", dto.getIdTag());
        model.addAttribute("caseName", dto.getCaseName());
        return "pages/biz/kb/part/define/picForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/define/pat/form")
    public String patForm(Model model, PfPartGetCommonDto dto) {
        model.addAttribute("showBtn", dto.getShowBtn());
        model.addAttribute("previewFlag", dto.getPreviewFlag());
        model.addAttribute("idMedCase", dto.getIdMedCase());
        model.addAttribute("tagFlag", dto.getTagFlag());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTag", dto.getIdTag());
        model.addAttribute("caseName", dto.getCaseName());
        model.addAttribute("sexList", enumUtil.getEnumList(SysDicGroupEnum.SEX.getCode()));
        return "pages/biz/kb/part/define/patForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/define/cons/form")
    public String consForm(Model model, PfPartGetCommonDto dto) {
        model.addAttribute("showBtn", dto.getShowBtn());
        model.addAttribute("previewFlag", dto.getPreviewFlag());
        model.addAttribute("idMedCase", dto.getIdMedCase());
        model.addAttribute("tagFlag", dto.getTagFlag());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTag", dto.getIdTag());
        model.addAttribute("caseName", dto.getCaseName());
        return "pages/biz/kb/part/define/consForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/define/check/form")
    public String checkForm(Model model, PfPartGetCommonDto dto) {
        model.addAttribute("showBtn", dto.getShowBtn());
        model.addAttribute("previewFlag", dto.getPreviewFlag());
        model.addAttribute("idMedCase", dto.getIdMedCase());
        model.addAttribute("tagFlag", dto.getTagFlag());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTag", dto.getIdTag());
        model.addAttribute("caseName", dto.getCaseName());
        model.addAttribute("bodyPosition", enumUtil.getEnumList(SysDicGroupEnum.BODY_POSITION.getCode()));
        return "pages/biz/kb/part/define/checkForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/define/exam/form")
    public String examForm(Model model, PfPartGetCommonDto dto) {
        model.addAttribute("showBtn", dto.getShowBtn());
        model.addAttribute("previewFlag", dto.getPreviewFlag());
        model.addAttribute("idMedCase", dto.getIdMedCase());
        model.addAttribute("tagFlag", dto.getTagFlag());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTag", dto.getIdTag());
        model.addAttribute("caseName", dto.getCaseName());
        return "pages/biz/kb/part/define/examForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/define/guide/form")
    public String guideForm(Model model, PfPartGetCommonDto dto) {
        model.addAttribute("showBtn", dto.getShowBtn());
        model.addAttribute("previewFlag", dto.getPreviewFlag());
        model.addAttribute("idMedCase", dto.getIdMedCase());
        model.addAttribute("tagFlag", dto.getTagFlag());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTag", dto.getIdTag());
        model.addAttribute("caseName", dto.getCaseName());
        return "pages/biz/kb/part/define/guideForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/cons/search")
    @ResponseBody
    public PageResult searchQuestion(PfCommonSearchDto dto) {
        return pfInquisitionService.searchQuestion(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/exam/list")
    @ResponseBody
    public PageResult listExams(PfPartCommonDto dto) {
        return pfKbPartService.listExams(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/exam/search")
    @ResponseBody
    public PageResult searchExam(PfCommonSearchDto dto) {
        return pfExamService.searchExam(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/check/list")
    @ResponseBody
    public PageResult listChecks(PfPartCommonDto dto) {
        return pfKbPartService.listChecks(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/check/search")
    @ResponseBody
    public PageResult searchCheck(PfCommonSearchDto dto) {
        return pfCheckService.searchCheck(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/define/cons/bach/add/page")
    public String consBachAdd(Model model, PfPartGetCommonDto dto) {
        model.addAttribute("idMedCase", dto.getIdMedCase());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTag", dto.getIdTag());
        model.addAttribute("tagFlag", dto.getTagFlag());
        model.addAttribute("caseName", dto.getCaseName());
        return "pages/biz/kb/part/define/consBachAdd";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/define/check/bach/add/page")
    public String checkBachAdd(Model model, PfPartGetCommonDto dto) {
        model.addAttribute("idMedCase", dto.getIdMedCase());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTag", dto.getIdTag());
        model.addAttribute("tagFlag", dto.getTagFlag());
        model.addAttribute("caseName", dto.getCaseName());
        model.addAttribute("bodyPosition", enumUtil.getEnumList(SysDicGroupEnum.BODY_POSITION.getCode()));
        return "pages/biz/kb/part/define/checkBachAdd";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/define/exam/bach/add/page")
    public String examBachAdd(Model model, PfPartGetCommonDto dto) {
        model.addAttribute("idMedCase", dto.getIdMedCase());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTag", dto.getIdTag());
        model.addAttribute("tagFlag", dto.getTagFlag());
        model.addAttribute("caseName", dto.getCaseName());
        return "pages/biz/kb/part/define/examBachAdd";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/question/pre/page")
    public String preQuestionPage(Model model, Long idMedCase) {
        model.addAttribute("idMedCase", idMedCase);
        return "pages/biz/kb/part/define/preQuestion";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010', 'ROLE_SUPER')")
    @RequestMapping(value = "/question/pre/list")
    @ResponseBody
    public PageResult listPreQuestion(FaqMedCaseInquesList dto) {
        Assert.isTrue(dto.getIdMedCaseList() != null, "idMedCaseList");
        return pfKbPartService.listPreQuestion(dto);
    }
}
