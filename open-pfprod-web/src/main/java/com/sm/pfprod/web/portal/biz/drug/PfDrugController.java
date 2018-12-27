package com.sm.pfprod.web.portal.biz.drug;

import com.sm.pfprod.model.dto.biz.drug.PfDrugInfoDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.drug.PfDrugService;
import com.sm.pfprod.web.portal.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfDiseaseController
 * @Description: 药品相关
 * @Author yangtongbin
 * @Date 2018/9/28
 */
@Controller
@RequestMapping(value = "/pf/p/drug")
public class PfDrugController extends BaseController {

    @Resource
    private PfDrugService pfDrugService;

    @PreAuthorize("hasAnyRole('ROLE_BAS0060','ROLE_SUPER')")
    @RequestMapping("/catalogue/page")
    public String cataloguePage(Model model) {
        return "pages/biz/drug/drugCatalogue";
    }

    @PreAuthorize("hasAnyRole('ROLE_BAS0060', 'ROLE_SUPER')")
    @RequestMapping("/catalogue/form")
    public String catalogueForm(String formType,  Model model) {
        model.addAttribute("formType", formType);
        return "pages/biz/drug/drugCatalogue";
    }

    @PreAuthorize("hasAnyRole('ROLE_BAS0060','ROLE_SUPER')")
    @RequestMapping("/info/page")
    public String infoPage(Model model) {
        return "pages/biz/drug/drugInfo";
    }

    @PreAuthorize("hasAnyRole('ROLE_BAS0060', 'ROLE_SUPER')")
    @RequestMapping("/info/form")
    public String form(String formType,  Model model) {
        model.addAttribute("formType", formType);
        return "pages/biz/drug/drugInfoForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_BAS0060','ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/info/list")
    @ResponseBody
    public PageResult listDrugInfo(PfDrugInfoDto dto) {
        if (dto.getType() == 1) {
            dto.setName(dto.getQueryCondition());
        } else if (dto.getType() == 2) {
            dto.setPinyin(dto.getQueryCondition());
        }
        return pfDrugService.listDrugInfo(dto);
    }

}
