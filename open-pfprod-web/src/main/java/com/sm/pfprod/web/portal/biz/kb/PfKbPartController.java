package com.sm.pfprod.web.portal.biz.kb;

import com.alibaba.fastjson.JSON;
import com.sm.pfprod.model.dto.biz.kb.part.PfMedCaseDto;
import com.sm.pfprod.model.dto.biz.kb.part.PfPartCommonDto;
import com.sm.pfprod.model.dto.common.PfCommonSearchDto;
import com.sm.pfprod.model.enums.SysDicGroupEnum;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.clinic.PfClinicPartsService;
import com.sm.pfprod.service.biz.inquisition.PfInquisitionService;
import com.sm.pfprod.service.biz.kb.PfKbPartService;
import com.sm.pfprod.web.portal.BaseController;
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
    private EnumUtil enumUtil;

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/page")
    public String partPage(Model model) {
        model.addAttribute("parts", pfClinicPartsService.listAllPart());
        return "pages/biz/kb/part/partTemplate";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listKbPart(PfMedCaseDto dto) {
        return pfKbPartService.listKbPart(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/form")
    public String form(Model model, String formType, String cdMedAsse) {
        model.addAttribute("formType", formType);
        model.addAttribute("cdMedAsse", cdMedAsse);
        model.addAttribute("parts", pfClinicPartsService.listAllPart());
        return "pages/biz/kb/part/partForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/useCase/form")
    public String partForm(Model model, String cdMedAsse, Long idMedCase) {
        model.addAttribute("cdMedAsse", cdMedAsse);
        model.addAttribute("idMedCase", idMedCase);
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
    public String textForm(Model model, String idMedCase) {
        model.addAttribute("idMedCase", idMedCase);
        return "pages/biz/kb/part/define/textForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/define/pic/form")
    public String picForm(Model model, String idMedCase) {
        model.addAttribute("idMedCase", idMedCase);
        return "pages/biz/kb/part/define/picForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/define/pat/form")
    public String patForm(Model model, String idMedCase) {
        model.addAttribute("idMedCase", idMedCase);
        model.addAttribute("sexList", enumUtil.getEnumList(SysDicGroupEnum.SEX.getCode()));
        return "pages/biz/kb/part/define/patForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/define/cons/form")
    public String consForm(Model model, String idMedCase) {
        model.addAttribute("idMedCase", idMedCase);
        return "pages/biz/kb/part/define/consForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/define/check/form")
    public String checkForm(Model model, String idMedCase) {
        model.addAttribute("idMedCase", idMedCase);
        return "pages/biz/kb/part/define/checkForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/define/exam/form")
    public String examForm(Model model, String idMedCase) {
        model.addAttribute("idMedCase", idMedCase);
        return "pages/biz/kb/part/define/examForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping(value = "/cons/search")
    @ResponseBody
    public PageResult searchQuestion(PfCommonSearchDto dto) {
        return pfInquisitionService.searchQuestion(dto);
    }

}
