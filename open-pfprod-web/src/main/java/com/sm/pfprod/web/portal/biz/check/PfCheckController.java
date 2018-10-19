package com.sm.pfprod.web.portal.biz.check;

import com.sm.pfprod.model.dto.biz.check.PfCheckQuestionDto;
import com.sm.pfprod.model.enums.SysDicGroupEnum;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.check.PfCheckService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.util.EnumUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfInquisitionController
 * @Description: 检查题库
 * @Author yangtongbin
 * @Date 2018/10/7
 */
@Controller
@RequestMapping(value = "/pf/p/check")
public class PfCheckController extends BaseController {

    @Resource
    private PfCheckService pfCheckService;

    @Resource
    private EnumUtil enumUtil;

    @PreAuthorize("hasAnyRole('ROLE_STD0030','ROLE_SUPER')")
    @RequestMapping("/question/page")
    public String cataloguePage(Model model) {
        model.addAttribute("examWays", enumUtil.getEnumList(SysDicGroupEnum.EXAM_WAYS.getCode()));
        model.addAttribute("bodyPosition", enumUtil.getEnumList(SysDicGroupEnum.BODY_POSITION.getCode()));
        return "pages/biz/check/checkQuestion";
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0030','ROLE_SUPER')")
    @RequestMapping(value = "/question/list")
    @ResponseBody
    public PageResult listQuestion(PfCheckQuestionDto dto) {
        return pfCheckService.listQuestion(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0030', 'ROLE_SUPER')")
    @RequestMapping("/question/form")
    public String form(String formType, Model model) {
        model.addAttribute("examWays", enumUtil.getEnumList(SysDicGroupEnum.EXAM_WAYS.getCode()));
        model.addAttribute("bodyPosition", enumUtil.getEnumList(SysDicGroupEnum.BODY_POSITION.getCode()));
        model.addAttribute("formType", formType);
        return "pages/biz/check/checkQuestionForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0030', 'ROLE_SUPER')")
    @RequestMapping("/question/answer/form")
    public String answerForm(Long idBody, Model model) {
        model.addAttribute("idBody", idBody);
        return "pages/biz/check/checkAnswerForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0030', 'ROLE_SUPER')")
    @RequestMapping("/question/answer/list")
    @ResponseBody
    public PageResult answerList(PfCheckQuestionDto dto) {
        return pfCheckService.listAnswer(dto);
    }
}
