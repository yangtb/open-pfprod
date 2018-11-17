package com.sm.pfprod.web.portal.biz.inquisition;

import com.sm.pfprod.model.dto.biz.inquisition.PfInquisitionQuestionDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.inquisition.PfInquisitionService;
import com.sm.pfprod.web.portal.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfInquisitionController
 * @Description: 问诊相关
 * @Author yangtongbin
 * @Date 2018/10/1
 */
@Controller
@RequestMapping(value = "/pf/p/inquisition")
public class PfInquisitionController extends BaseController {

    @Resource
    private PfInquisitionService pfInquisitionService;

    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @RequestMapping("/question/page")
    public String cataloguePage(Model model) {
        return "pages/biz/inquisition/question";
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_FAQ0020','ROLE_SUPER')")
    @RequestMapping(value = "/question/list")
    @ResponseBody
    public PageResult listQuestion(PfInquisitionQuestionDto dto) {
        return pfInquisitionService.listQuestion(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0010', 'ROLE_SUPER')")
    @RequestMapping("/question/form")
    public String form(String formType,  Model model) {
        model.addAttribute("formType", formType);
        return "pages/biz/inquisition/questionForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0010', 'ROLE_SUPER')")
    @RequestMapping("/question/answer/form")
    public String answerForm(Long idInques,  Model model) {
        model.addAttribute("idInques", idInques);
        return "pages/biz/inquisition/answerForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0010', 'ROLE_SUPER')")
    @RequestMapping("/question/answer/list")
    @ResponseBody
    public PageResult answerList(PfInquisitionQuestionDto dto) {
        return pfInquisitionService.listAnswer(dto);
    }
}
