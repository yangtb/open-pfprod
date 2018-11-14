package com.sm.pfprod.web.portal.biz.tests;

import com.sm.pfprod.model.dto.biz.tests.PfTestExamDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestExamTagDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestWatingRoomDto;
import com.sm.pfprod.model.entity.ExmMedResultOrder;
import com.sm.pfprod.model.enums.SysDicGroupEnum;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.test.PfWaitingRoomPatVo;
import com.sm.pfprod.model.vo.biz.test.paper.PfTestPaperVo;
import com.sm.pfprod.service.biz.tests.PfTestWaitingRoomService;
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
 * @ClassName: PfTestWaitingRoomController
 * @Description: 候诊室
 * @Author yangtongbin
 * @Date 2018/11/4
 */
@Controller
@RequestMapping(value = "/pf/p/waiting/room")
public class PfTestWaitingRoomController extends BaseController {

    @Resource
    private PfTestWaitingRoomService pfTestWaitingRoomService;

    @Resource
    private EnumUtil enumUtil;

    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping("/page")
    public String paperPage(Model model) {
        model.addAttribute("sexEnum", enumUtil.getEnumList(SysDicGroupEnum.SEX.getCode()));
        return "pages/biz/tests/room/waitingRoomPage";
    }

    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping("/exam/page")
    public String papPage(Model model, PfTestExamDto dto) {
        model.addAttribute("idTestplanDetail", dto.getIdTestplanDetail());
        model.addAttribute("idTestplan", dto.getIdTestplan());
        model.addAttribute("idDemo", dto.getIdDemo());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTestpaper", "idTestpaper");
        dto.setIdStudent(CurrentUserUtils.getCurrentUserId());
        PfTestPaperVo pfTestPaperVo = pfTestWaitingRoomService.selectTestPaperInfo(dto);
        pfTestPaperVo.getStudentInfo().setSex(enumUtil.getEnumTxt(SysDicGroupEnum.SEX.getCode(), pfTestPaperVo.getStudentInfo().getSex()));
        model.addAttribute("examInfo", pfTestPaperVo);
        return "pages/biz/tests/room/testPage";
    }

    /**
     * 页签-患者信息
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping("/test/pat/page")
    public String patPage(Model model, PfTestExamTagDto dto) {
        PfWaitingRoomPatVo pfWaitingRoomPatVo = pfTestWaitingRoomService.selectPatInfo(dto);
        pfWaitingRoomPatVo.setSexStr(enumUtil.getEnumTxt(SysDicGroupEnum.SEX.getCode(), pfWaitingRoomPatVo.getSex()));
        model.addAttribute("patInfo", pfWaitingRoomPatVo);
        return "pages/biz/tests/room/exec/patPage";
    }

    /**
     * 页签-问诊
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping("/test/cons/page")
    public String consPage(Model model, PfTestExamTagDto dto) {
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("cdMedAsse", dto.getCdMedAsse());
        model.addAttribute("idTestexecResult", dto.getIdTestexecResult());
        return "pages/biz/tests/room/exec/consPage";
    }

    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping(value = "/test/cons/list")
    @ResponseBody
    public PageResult listTestCons(PfTestExamTagDto dto) {
        return pfTestWaitingRoomService.listTestCons(dto);
    }


    /**
     * 页签-检查
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping("/test/check/page")
    public String checkPage(Model model, PfTestExamTagDto dto) {
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("cdMedAsse", dto.getCdMedAsse());
        model.addAttribute("idTestexecResult", dto.getIdTestexecResult());
        // 位置
        model.addAttribute("bodyPosition", enumUtil.getEnumList(SysDicGroupEnum.BODY_POSITION.getCode()));
        return "pages/biz/tests/room/exec/checkPage";
    }

    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping(value = "/test/check/list")
    @ResponseBody
    public PageResult listTestCheck(PfTestExamTagDto dto) {
        return pfTestWaitingRoomService.listTestCheck(dto);
    }


    /**
     * 页签-检验
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping("/test/exam/page")
    public String examPage(Model model, PfTestExamTagDto dto) {
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("cdMedAsse", dto.getCdMedAsse());
        model.addAttribute("idTestexecResult", dto.getIdTestexecResult());
        return "pages/biz/tests/room/exec/examPage";
    }

    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping(value = "/test/exam/list")
    @ResponseBody
    public PageResult listTestExam(PfTestExamTagDto dto) {
        return pfTestWaitingRoomService.listTestExam(dto);
    }

    /**
     * 页签-拟诊
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping("/test/referral/page")
    public String referralPage(Model model, PfTestExamTagDto dto) {
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("cdMedAsse", dto.getCdMedAsse());
        model.addAttribute("idTestexecResult", dto.getIdTestexecResult());
        return "pages/biz/tests/room/exec/referralPage";
    }

    /**
     * 页签-拟诊form
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping("/test/referral/form")
    public String referralForm(Model model, PfTestExamTagDto dto) {
        model.addAttribute("idTestexecResult", dto.getIdTestexecResult());
        return "pages/biz/tests/room/exec/referralForm";
    }

    /**
     * 页签-病史小结
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping("/test/summary/page")
    public String summaryPage(Model model, PfTestExamTagDto dto) {
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("cdMedAsse", dto.getCdMedAsse());
        model.addAttribute("idTestexecResult", dto.getIdTestexecResult());
        return "pages/biz/tests/room/exec/summaryPage";
    }

    /**
     * 页签-医嘱
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping("/test/orders/page")
    public String ordersPage(Model model, PfTestExamTagDto dto) {
        model.addAttribute("idTestexecResult", dto.getIdTestexecResult());
        model.addAttribute("sdNursRout", enumUtil.getEnumList(SysDicGroupEnum.SD_NURS_ROUT.getCode()));
        model.addAttribute("cdNursLevel", enumUtil.getEnumList(SysDicGroupEnum.CD_NURS_LEVEL.getCode()));
        model.addAttribute("sdDiet", enumUtil.getEnumList(SysDicGroupEnum.SD_DIET.getCode()));
        model.addAttribute("sdPosition", enumUtil.getEnumList(SysDicGroupEnum.BODY_POSITION.getCode()));
        return "pages/biz/tests/room/exec/ordersPage";
    }

    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping(value = "/test/orders/drug/long/list")
    @ResponseBody
    public PageResult listLongDrugs(Long idTestexecResultOrder) {
        return pfTestWaitingRoomService.listLongDrugs(idTestexecResultOrder);
    }

    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping(value = "/test/orders/drug/short/list")
    @ResponseBody
    public PageResult listShortDrugs(Long idTestexecResultOrder) {
        return pfTestWaitingRoomService.listShortDrugs(idTestexecResultOrder);
    }

    /**
     * 模拟评估
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping("/test/assess/page")
    public String testAssessPage(Model model, PfTestExamTagDto dto) {
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("cdMedAsse", dto.getCdMedAsse());
        model.addAttribute("idTestexecResult", dto.getIdTestexecResult());
        return "pages/biz/tests/room/assess/testAssessPage";
    }


    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listWaitingRoom(PfTestWatingRoomDto dto) {
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        return pfTestWaitingRoomService.listWaitingRoom(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping(value = "/receive/list")
    @ResponseBody
    public PageResult listReceivePat(PfTestWatingRoomDto dto) {
        return pfTestWaitingRoomService.listReceivePat(dto);
    }

}
