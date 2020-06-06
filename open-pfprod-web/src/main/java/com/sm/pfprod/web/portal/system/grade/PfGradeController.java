package com.sm.pfprod.web.portal.system.grade;

import com.sm.pfprod.model.dto.system.grade.PfGradeDto;
import com.sm.pfprod.model.dto.user.PfUserDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.system.grade.PfGradeService;
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
 * @ClassName: PfGradeController
 * @Description: 班级rest
 * @Author yangtongbin
 * @Date 2018/11/3
 */
@Controller
@RequestMapping(value = "/pf/p/class")
public class PfGradeController extends BaseController {

    @Resource
    private PfGradeService pfGradeService;

    @Resource
    private EnumUtil enumUtil;

    @PreAuthorize("hasAnyRole('ROLE_SYSTEM_CLASS_MG','ROLE_SUPER')")
    @RequestMapping("/page")
    public String gradePage(Model model) {
        return "pages/system/grade/classPage";
    }

    @PreAuthorize("hasAnyRole('ROLE_SYSTEM_CLASS_MG','ROLE_SUPER')")
    @RequestMapping("/form")
    public String gradeForm(Model model, String formType) {
        model.addAttribute("formType", formType);
        return "pages/system/grade/gradeForm";
    }

    @PreAuthorize("hasAnyRole('ROLE_SYSTEM_CLASS_MG','ROLE_SUPER')")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listGrades(PfGradeDto dto) {
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        return pfGradeService.listGrades(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_SYSTEM_CLASS_MG','ROLE_SUPER')")
    @RequestMapping(value = "/student/list")
    @ResponseBody
    public PageResult listStudents(PfGradeDto dto) {
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        return pfGradeService.listStudents(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_SYSTEM_CLASS_MG','ROLE_SUPER')")
    @RequestMapping(value = "/student/search/list")
    @ResponseBody
    public PageResult listUsStudents(PfUserDto dto) {
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        return pfGradeService.listUsStudents(dto);
    }


}
