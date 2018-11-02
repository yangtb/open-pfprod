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


}
