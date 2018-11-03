package com.sm.pfprod.web.portal.biz.tests;

import com.alibaba.fastjson.JSON;
import com.sm.pfprod.model.dto.biz.tests.PfTestPaperDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestPlanDto;
import com.sm.pfprod.model.entity.ExmTestpaper;
import com.sm.pfprod.model.enums.SysDicGroupEnum;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.tests.PfTestPaperService;
import com.sm.pfprod.service.biz.tests.PfTestPlanService;
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
import java.util.List;

/**
 * @ClassName: PfTestPlanController
 * @Description: 测试计划
 * @Author yangtongbin
 * @Date 2018/11/2
 */
@Controller
@RequestMapping(value = "/pf/p/test/plan")
public class PfTestPlanController extends BaseController {

    @Resource
    private PfOrgService pfOrgService;

    @Resource
    private PfTestPlanService pfTestPlanService;

    @Resource
    private PfTestPaperService pfTestPaperService;

    @Resource
    private EnumUtil enumUtil;

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/page")
    public String planPage(Model model) {
        model.addAttribute("sdTestPlan", enumUtil.getEnumList(SysDicGroupEnum.SD_TESTPLAN.getCode()));
        return "pages/biz/tests/plan/planPage";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/form")
    public String planForm(Model model, String formType) {
        model.addAttribute("formType", formType);
        model.addAttribute("idOrg", CurrentUserUtils.getCurrentUser().getIdOrg());
        model.addAttribute("allOrg", pfOrgService.listAllOrg());

        PfTestPaperDto dto = new PfTestPaperDto();
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        List<ExmTestpaper> testpapers = pfTestPaperService.listAllPaper(dto);
        model.addAttribute("papers", JSON.parseArray(JSON.toJSONString(testpapers)));
        return "pages/biz/tests/plan/planForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0030','ROLE_SUPER')")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listPlan(PfTestPlanDto dto) {
        return pfTestPlanService.listPlans(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0020', 'ROLE_SUPER')")
    @RequestMapping("/tag/form")
    public String tagForm(Long idTestplan, Model model) {
        model.addAttribute("idTestplan", idTestplan);
        return "pages/biz/tests/plan/planTagForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/item/page")
    public String testPlanItem(Model model, Long idTestplan) {
        model.addAttribute("idTestplan", idTestplan);
        model.addAttribute("caseHistoryLevel", enumUtil.getEnumList(SysDicGroupEnum.CASE_HISTORY_LEVEL.getCode()));
        model.addAttribute("caseHistoryUse", enumUtil.getEnumList(SysDicGroupEnum.CASE_HISTORY_USE.getCode()));
        return "pages/biz/tests/plan/tagTestPlanItem";
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0020', 'ROLE_SUPER')")
    @RequestMapping("/tag/student/page")
    public String tagStudentPage(Long idTestplan, Model model) {
        model.addAttribute("idTestplan", idTestplan);
        model.addAttribute("sdTestPlan", enumUtil.getEnumList(SysDicGroupEnum.SD_TESTPLAN.getCode()));
        return "pages/biz/tests/plan/tagStudentForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0020', 'ROLE_SUPER')")
    @RequestMapping("/tag/detail/page")
    public String tagDetailPage(Long idTestplan, Model model) {
        model.addAttribute("idTestplan", idTestplan);
        model.addAttribute("caseHistoryLevel", enumUtil.getEnumList(SysDicGroupEnum.CASE_HISTORY_LEVEL.getCode()));
        model.addAttribute("sdTestPlan", enumUtil.getEnumList(SysDicGroupEnum.SD_TESTPLAN.getCode()));
        return "pages/biz/tests/plan/tagDetailForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0030','ROLE_SUPER')")
    @RequestMapping(value = "/item/list")
    @ResponseBody
    public PageResult listPlanItem(PfTestPlanDto dto) {
        return pfTestPlanService.listPlanItem(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0030','ROLE_SUPER')")
    @RequestMapping(value = "/student/list")
    @ResponseBody
    public PageResult listPlanStudent(PfTestPlanDto dto) {
        return pfTestPlanService.listPlanStudent(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0030','ROLE_SUPER')")
    @RequestMapping(value = "/detail/list")
    @ResponseBody
    public PageResult listPlanDetail(PfTestPlanDto dto) {
        return pfTestPlanService.listPlanDetail(dto);
    }


}
