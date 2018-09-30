package com.sm.pfprod.web.portal.biz.disease;

import com.sm.pfprod.model.dto.biz.disease.PfDiseaseInfoDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.disease.PfDiseaseService;
import com.sm.pfprod.web.portal.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfDiseaseController
 * @Description: 疾病相关
 * @Author yangtongbin
 * @Date 2018/9/28
 */
@Controller
@RequestMapping(value = "/pf/p/disease/")
public class PfDiseaseController extends BaseController {

    @Resource
    private PfDiseaseService pfDiseaseService;

    @PreAuthorize("hasAnyRole('ROLE_BAS0030','ROLE_SUPER')")
    @RequestMapping("/catalogue/page")
    public String cataloguePage(Model model) {
        return "pages/biz/disease/diseaseCatalogue";
    }

    @PreAuthorize("hasAnyRole('ROLE_BAS0030', 'ROLE_SUPER')")
    @RequestMapping("/catalogue/form")
    public String catalogueForm(String formType,  Model model) {
        model.addAttribute("formType", formType);
        return "pages/biz/disease/diseaseCatalogue";
    }

    @PreAuthorize("hasAnyRole('ROLE_BAS0040','ROLE_SUPER')")
    @RequestMapping("/info/page")
    public String infoPage(Model model) {
        return "pages/biz/disease/diseaseInfo";
    }

    @PreAuthorize("hasAnyRole('ROLE_BAS0040', 'ROLE_SUPER')")
    @RequestMapping("/info/form")
    public String infoForm(String formType,  Model model) {
        model.addAttribute("formType", formType);
        return "pages/biz/disease/diseaseInfoForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_BAS0040','ROLE_SUPER')")
    @RequestMapping(value = "/info/list")
    @ResponseBody
    public PageResult listDiseaseInfo(PfDiseaseInfoDto dto) {
        if (dto.getType() == 1) {
            dto.setName(dto.getQueryCondition());
        } else if (dto.getType() == 2) {
            dto.setIcd(dto.getQueryCondition());
        } else if (dto.getType() == 3) {
            dto.setPinyin(dto.getQueryCondition());
        }
        return pfDiseaseService.listDiseaseInfo(dto);
    }

}
