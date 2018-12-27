package com.sm.pfprod.web.portal.biz.tests;

import com.sm.pfprod.model.dto.biz.tests.PfTestPaperDto;
import com.sm.pfprod.model.enums.SysDicGroupEnum;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.tests.PfTestPaperService;
import com.sm.pfprod.service.system.org.PfOrgService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.security.CurrentUserUtils;
import com.sm.pfprod.web.util.EnumUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfTestPaperController
 * @Description: 试卷定义
 * @Author yangtongbin
 * @Date 2018/10/31
 */
@Controller
@RequestMapping(value = "/pf/p/tests/paper")
public class PfTestPaperController extends BaseController {

    @Resource
    private PfOrgService pfOrgService;

    @Resource
    private PfTestPaperService pfTestPaperService;

    @Resource
    private EnumUtil enumUtil;

    @PreAuthorize("hasAnyRole('ROLE_EXM0010','ROLE_SUPER')")
    @RequestMapping("/page")
    public String paperPage() {
        return "pages/biz/tests/paper/testPaper";
    }

    @PreAuthorize("hasAnyRole('ROLE_EXM0010','ROLE_SUPER')")
    @RequestMapping("/form")
    public String paperForm(Model model, String formType) {
        model.addAttribute("formType", formType);
        model.addAttribute("idOrg", CurrentUserUtils.getCurrentUser().getIdOrg());
        model.addAttribute("allOrg", pfOrgService.listAllOrg());
        return "pages/biz/tests/paper/testPaperForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_EXM0010','ROLE_SUPER')")
    @RequestMapping("/item/page")
    public String testPaperItem(Model model, Long idTestpaper) {
        model.addAttribute("idTestpaper", idTestpaper);
        model.addAttribute("caseHistoryLevel", enumUtil.getEnumList(SysDicGroupEnum.CASE_HISTORY_LEVEL.getCode()));
        model.addAttribute("caseHistoryUse", enumUtil.getEnumList(SysDicGroupEnum.CASE_HISTORY_USE.getCode()));
        return "pages/biz/tests/paper/testPaperItem";
    }

    @PreAuthorize("hasAnyRole('ROLE_EXM0010','ROLE_SUPER')")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listQuestion(PfTestPaperDto dto) {
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        return pfTestPaperService.listPaper(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_EXM0010','ROLE_SUPER')")
    @RequestMapping(value = "/item/list")
    @ResponseBody
    public PageResult listPaperItem(PfTestPaperDto dto) {
        return pfTestPaperService.listPaperItem(dto);
    }

}
