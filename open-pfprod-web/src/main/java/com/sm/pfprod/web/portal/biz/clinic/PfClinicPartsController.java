package com.sm.pfprod.web.portal.biz.clinic;

import com.sm.pfprod.model.dto.biz.clinic.parts.PfClinicPartsDto;
import com.sm.pfprod.model.enums.SysDicGroupEnum;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.clinic.PfClinicPartsService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.util.EnumUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfClinicPartController
 * @Description: 临床模板组件定义
 * @Author yangtongbin
 * @Date 2018/10/12
 */
@Controller
@RequestMapping(value = "/pf/p/clinic")
public class PfClinicPartsController extends BaseController {

    @Resource
    private PfClinicPartsService pfClinicPartsService;

    @Resource
    private EnumUtil enumUtil;

    @PreAuthorize("hasAnyRole('ROLE_CLINIC0010','ROLE_SUPER')")
    @RequestMapping("/part/page")
    public String partPage(Model model) {
        model.addAttribute("importType", enumUtil.getEnumMap(SysDicGroupEnum.IMPORT_TYPE.getCode()));
        return "pages/biz/clinic/parts/partDefine";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLINIC0010','ROLE_SUPER')")
    @RequestMapping(value = "/part/list")
    @ResponseBody
    public PageResult listPart(PfClinicPartsDto dto) {
        return pfClinicPartsService.listParts(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLINIC0010', 'ROLE_SUPER')")
    @RequestMapping("/part/form")
    public String partForm(String formType, Model model) {
        model.addAttribute("formType", formType);
        model.addAttribute("importType", enumUtil.getEnumMap(SysDicGroupEnum.IMPORT_TYPE.getCode()));
        return "pages/biz/clinic/parts/partDefineForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLINIC0020','ROLE_SUPER')")
    @RequestMapping("/sheet/page")
    public String sheetPage(Model model) {
        model.addAttribute("importType", enumUtil.getEnumMap(SysDicGroupEnum.IMPORT_TYPE.getCode()));
        return "pages/biz/clinic/sheet/sheetDefine";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLINIC0020','ROLE_SUPER')")
    @RequestMapping(value = "/sheet/list")
    @ResponseBody
    public PageResult listSheet(PfClinicPartsDto dto) {
        return pfClinicPartsService.listSheet(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLINIC0020', 'ROLE_SUPER')")
    @RequestMapping("/sheet/form")
    public String sheetForm(String formType, Model model) {
        model.addAttribute("formType", formType);
        model.addAttribute("importType", enumUtil.getEnumMap(SysDicGroupEnum.IMPORT_TYPE.getCode()));
        return "pages/biz/clinic/sheet/sheetDefineForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLINIC0020','ROLE_SUPER')")
    @RequestMapping("/algorithm/page")
    public String algorithmPage(Model model) {
        model.addAttribute("algorithmDefine", enumUtil.getEnumMap(SysDicGroupEnum.ALGORITHM_DEFINE.getCode()));
        return "pages/biz/clinic/algorithm/algorithmDefine";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLINIC0020','ROLE_SUPER')")
    @RequestMapping(value = "/algorithm/list")
    @ResponseBody
    public PageResult listAlgorithm(PfClinicPartsDto dto) {
        return pfClinicPartsService.listAlgorithm(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLINIC0020', 'ROLE_SUPER')")
    @RequestMapping("/algorithm/form")
    public String algorithmForm(String formType, Model model) {
        model.addAttribute("formType", formType);
        model.addAttribute("algorithmDefine", enumUtil.getEnumMap(SysDicGroupEnum.ALGORITHM_DEFINE.getCode()));
        return "pages/biz/clinic/algorithm/algorithmDefineForm";
    }
}
