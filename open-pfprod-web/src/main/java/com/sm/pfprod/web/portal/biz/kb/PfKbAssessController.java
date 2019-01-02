package com.sm.pfprod.web.portal.biz.kb;

import com.alibaba.fastjson.JSON;
import com.sm.open.care.core.enums.YesOrNoNum;
import com.sm.pfprod.model.dto.biz.kb.assess.PfAssessCommonDto;
import com.sm.pfprod.model.dto.biz.kb.assess.PfAssessGetCommonDto;
import com.sm.pfprod.model.dto.biz.kb.assess.PfEvaCaseDto;
import com.sm.pfprod.model.dto.common.PfCatalogueTreeDto;
import com.sm.pfprod.model.enums.SysDicGroupEnum;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.disease.PfDiseaseZtreeVo;
import com.sm.pfprod.service.biz.clinic.PfClinicPartsService;
import com.sm.pfprod.service.biz.disease.PfDiseaseService;
import com.sm.pfprod.service.biz.kb.PfKbAssessService;
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
import java.util.List;

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

    @Resource
    private PfDiseaseService pfDiseaseService;

    @Resource
    private EnumUtil enumUtil;


    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping("/page")
    public String partPage(Model model) {
        model.addAttribute("currentIdOrg", CurrentUserUtils.getCurrentUser().getIdOrg());
        model.addAttribute("assesses", pfClinicPartsService.listAllSheet());
        return "pages/biz/kb/assess/assessTemplate";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listKbAssess(PfEvaCaseDto dto) {
        User user = CurrentUserUtils.getCurrentUser();
        if (user.getFgPlat().equals(YesOrNoNum.NO.getCode())) {
            dto.setIdOrg(user.getIdOrg());
        }
        return pfKbAssessService.listKbAssess(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping("/form")
    public String form(Model model, String formType, String previewFlag) {
        model.addAttribute("formType", formType);
        model.addAttribute("previewFlag", previewFlag);
        model.addAttribute("assesses", pfClinicPartsService.listAllSheet());
        return "pages/biz/kb/assess/assessForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping("/useCase/form")
    public String partForm(Model model, String cdEvaAsse, Long idEvaCase) {
        model.addAttribute("cdEvaAsse", cdEvaAsse);
        model.addAttribute("idEvaCase", idEvaCase);
        model.addAttribute("sheets", JSON.parseArray(JSON.toJSONString(pfClinicPartsService.listAllSheet())));
        return "pages/biz/kb/assess/assessUseCaseForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping("/common/page")
    public String commonPage(Model model, String cdEvaAsse, Long idEvaCase, Integer showForm) {
        model.addAttribute("showForm", showForm);
        model.addAttribute("cdEvaAsse", cdEvaAsse);
        model.addAttribute("idEvaCase", idEvaCase);
        model.addAttribute("sdEva", enumUtil.getEnumList(SysDicGroupEnum.SD_EVA.getCode()));
        model.addAttribute("sheets", JSON.parseArray(JSON.toJSONString(pfClinicPartsService.listAllSheet())));
        return "pages/biz/kb/assess/define/assessCommon";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping("/referral/page")
    public String referralPage(Model model, PfAssessGetCommonDto dto) {
        model.addAttribute("showBtn", dto.getShowBtn());
        model.addAttribute("previewFlag", dto.getPreviewFlag());
        model.addAttribute("showForm", dto.getShowForm());
        model.addAttribute("cdEvaAsse", dto.getCdEvaAsse());
        model.addAttribute("idEvaCase", dto.getIdEvaCase());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTag", dto.getIdTag());
        model.addAttribute("tagFlag", dto.getTagFlag());
        model.addAttribute("caseName", dto.getCaseName());
        model.addAttribute("sdEva", enumUtil.getEnumList(SysDicGroupEnum.SD_EVA.getCode()));
        model.addAttribute("sheets", JSON.parseArray(JSON.toJSONString(pfClinicPartsService.listAllSheet())));
        return "pages/biz/kb/assess/define/assessReferral";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping("/common/answer/page")
    public String commonAnswerPage(Model model, String cdEvaAsse, Long idEvaCase, String sdEva) {
        model.addAttribute("cdEvaAsse", cdEvaAsse);
        model.addAttribute("idEvaCase", idEvaCase);
        model.addAttribute("sdEva", sdEva);
        model.addAttribute("sheets", JSON.parseArray(JSON.toJSONString(pfClinicPartsService.listAllSheet())));
        return "pages/biz/kb/assess/define/addCommonAnswer";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping("/diagnosis/page")
    public String diagnosisPage(Model model, PfAssessGetCommonDto dto) {
        model.addAttribute("showBtn", dto.getShowBtn());
        model.addAttribute("previewFlag", dto.getPreviewFlag());
        model.addAttribute("showForm", dto.getShowForm());
        model.addAttribute("cdEvaAsse", dto.getCdEvaAsse());
        model.addAttribute("idEvaCase", dto.getIdEvaCase());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTag", dto.getIdTag());
        model.addAttribute("tagFlag", dto.getTagFlag());
        model.addAttribute("caseName", dto.getCaseName());
        model.addAttribute("sdEva", enumUtil.getEnumList(SysDicGroupEnum.SD_EVA.getCode()));
        model.addAttribute("sheets", JSON.parseArray(JSON.toJSONString(pfClinicPartsService.listAllSheet())));
        return "pages/biz/kb/assess/define/assessDiagnosis";
    }


    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping("/reason/page")
    public String reasonPage(Model model, PfAssessGetCommonDto dto) {
        model.addAttribute("showBtn", dto.getShowBtn());
        model.addAttribute("previewFlag", dto.getPreviewFlag());
        model.addAttribute("showForm", dto.getShowForm());
        model.addAttribute("cdEvaAsse", dto.getCdEvaAsse());
        model.addAttribute("idEvaCase", dto.getIdEvaCase());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTag", dto.getIdTag());
        model.addAttribute("tagFlag", dto.getTagFlag());
        model.addAttribute("caseName", dto.getCaseName());
        model.addAttribute("sdEva", enumUtil.getEnumList(SysDicGroupEnum.SD_EVA.getCode()));
        model.addAttribute("sheets", JSON.parseArray(JSON.toJSONString(pfClinicPartsService.listAllSheet())));
        return "pages/biz/kb/assess/define/assessReason";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping("/cover/page")
    public String coverPage(Model model, PfAssessGetCommonDto dto) {
        model.addAttribute("showBtn", dto.getShowBtn());
        model.addAttribute("previewFlag", dto.getPreviewFlag());
        model.addAttribute("showForm", dto.getShowForm());
        model.addAttribute("cdEvaAsse", dto.getCdEvaAsse());
        model.addAttribute("idEvaCase", dto.getIdEvaCase());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTag", dto.getIdTag());
        model.addAttribute("tagFlag", dto.getTagFlag());
        model.addAttribute("caseName", dto.getCaseName());
        model.addAttribute("sdEva", enumUtil.getEnumList(SysDicGroupEnum.SD_EVA.getCode()));
        model.addAttribute("sheets", JSON.parseArray(JSON.toJSONString(pfClinicPartsService.listAllSheet())));
        return "pages/biz/kb/assess/define/assessCover";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping("/must/page")
    public String mustPage(Model model, PfAssessGetCommonDto dto) {
        model.addAttribute("showBtn", dto.getShowBtn());
        model.addAttribute("previewFlag", dto.getPreviewFlag());
        model.addAttribute("showForm", dto.getShowForm());
        model.addAttribute("cdEvaAsse", dto.getCdEvaAsse());
        model.addAttribute("idEvaCase", dto.getIdEvaCase());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTag", dto.getIdTag());
        model.addAttribute("tagFlag", dto.getTagFlag());
        model.addAttribute("caseName", dto.getCaseName());
        model.addAttribute("sdEva", enumUtil.getEnumList(SysDicGroupEnum.SD_EVA.getCode()));
        model.addAttribute("sheets", JSON.parseArray(JSON.toJSONString(pfClinicPartsService.listAllSheet())));
        return "pages/biz/kb/assess/define/assessMust";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping("/effciency/page")
    public String effciencyPage(Model model, PfAssessGetCommonDto dto) {
        model.addAttribute("showBtn", dto.getShowBtn());
        model.addAttribute("previewFlag", dto.getPreviewFlag());
        model.addAttribute("showForm", dto.getShowForm());
        model.addAttribute("cdEvaAsse", dto.getCdEvaAsse());
        model.addAttribute("idEvaCase", dto.getIdEvaCase());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTag", dto.getIdTag());
        model.addAttribute("tagFlag", dto.getTagFlag());
        model.addAttribute("caseName", dto.getCaseName());
        model.addAttribute("sdEva", enumUtil.getEnumList(SysDicGroupEnum.SD_EVA.getCode()));
        model.addAttribute("sheets", JSON.parseArray(JSON.toJSONString(pfClinicPartsService.listAllSheet())));
        return "pages/biz/kb/assess/define/assessEffciency";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping("/order/page")
    public String orderPage(Model model, PfAssessGetCommonDto dto) {
        model.addAttribute("showBtn", dto.getShowBtn());
        model.addAttribute("previewFlag", dto.getPreviewFlag());
        model.addAttribute("showForm", dto.getShowForm());
        model.addAttribute("cdEvaAsse", dto.getCdEvaAsse());
        model.addAttribute("idEvaCase", dto.getIdEvaCase());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTag", dto.getIdTag());
        model.addAttribute("tagFlag", dto.getTagFlag());
        model.addAttribute("caseName", dto.getCaseName());
        model.addAttribute("sdEvaOrder", enumUtil.getEnumList(SysDicGroupEnum.SD_EVA_ORDER.getCode()));
        model.addAttribute("sdNursRout", JSON.parseArray(JSON.toJSONString(enumUtil.getEnumList(SysDicGroupEnum.SD_NURS_ROUT.getCode()))));
        model.addAttribute("cdNursLevel", JSON.parseArray(JSON.toJSONString(enumUtil.getEnumList(SysDicGroupEnum.CD_NURS_LEVEL.getCode()))));
        model.addAttribute("sdDiet", JSON.parseArray(JSON.toJSONString(enumUtil.getEnumList(SysDicGroupEnum.SD_DIET.getCode()))));
        model.addAttribute("sdPosition", JSON.parseArray(JSON.toJSONString(enumUtil.getEnumList(SysDicGroupEnum.SD_POSITION.getCode()))));
        model.addAttribute("sheets", JSON.parseArray(JSON.toJSONString(pfClinicPartsService.listAllSheet())));
        return "pages/biz/kb/assess/define/assessOrder";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping("/referral/answer/page")
    public String referralPage1(Model model, Long idEvaCaseItem, String sdEva) {
        model.addAttribute("idEvaCaseItem", idEvaCaseItem);
        model.addAttribute("sdEva", sdEva);
        PfCatalogueTreeDto dto = new PfCatalogueTreeDto();
        dto.setAsync(true);
        List<PfDiseaseZtreeVo> list = pfDiseaseService.listDiseaseCatalogueTree(dto);
        model.addAttribute("diseaseCatalogue", JSON.toJSONString(list));
        return "pages/biz/kb/assess/define/addReferralAnswer";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/referral/list")
    @ResponseBody
    public PageResult listKbReferral(PfAssessCommonDto dto) {
        return pfKbAssessService.listKbReferral(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/diagnosis/list")
    @ResponseBody
    public PageResult listKbDiagnosis(PfAssessCommonDto dto) {
        return pfKbAssessService.listKbDiagnosis(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/reason/list")
    @ResponseBody
    public PageResult listKbReason(PfAssessCommonDto dto) {
        return pfKbAssessService.listKbReason(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/cover/list")
    @ResponseBody
    public PageResult listKbCover(PfAssessCommonDto dto) {
        return pfKbAssessService.listKbCover(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/must/list")
    @ResponseBody
    public PageResult listKbMust(PfAssessCommonDto dto) {
        return pfKbAssessService.listKbMust(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/effciency/list")
    @ResponseBody
    public PageResult listKbEffciency(PfAssessCommonDto dto) {
        return pfKbAssessService.listKbEffciency(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/order/list")
    @ResponseBody
    public PageResult listKbOrder(PfAssessCommonDto dto) {
        return pfKbAssessService.listKbOrder(dto);
    }

}
