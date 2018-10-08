package com.sm.pfprod.web.portal.biz.exam;

import com.sm.pfprod.model.dto.biz.exam.PfExamQuestionDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.exam.PfExamService;
import com.sm.pfprod.web.portal.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfExamController
 * @Description: 检验题库
 * @Author yangtongbin
 * @Date 2018/10/7
 */
@Controller
@RequestMapping(value = "/pf/p/exam")
public class PfExamController extends BaseController {

    @Resource
    private PfExamService pfExamService;

    @PreAuthorize("hasAnyRole('ROLE_STD0050','ROLE_SUPER')")
    @RequestMapping("/question/page")
    public String cataloguePage(Model model) {
        return "pages/biz/exam/question";
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0050','ROLE_SUPER')")
    @RequestMapping(value = "/question/list")
    @ResponseBody
    public PageResult listQuestion(PfExamQuestionDto dto) {
        return pfExamService.listQuestion(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0050', 'ROLE_SUPER')")
    @RequestMapping("/question/form")
    public String form(String formType, Model model) {
        model.addAttribute("formType", formType);
        return "pages/biz/exam/questionForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0050', 'ROLE_SUPER')")
    @RequestMapping("/question/answer/form")
    public String answerForm(Long idInspectItem, Model model) {
        model.addAttribute("idInspectItem", idInspectItem);
        return "pages/biz/exam/answerForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0050', 'ROLE_SUPER')")
    @RequestMapping("/question/answer/list")
    @ResponseBody
    public PageResult answerList(PfExamQuestionDto dto) {
        return pfExamService.listAnswer(dto);
    }
}
