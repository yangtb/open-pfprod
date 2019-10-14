package com.sm.pfprod.web.portal.biz.tests;

import com.alibaba.fastjson.JSON;
import com.sm.open.care.core.enums.YesOrNoNum;
import com.sm.open.core.facade.model.result.pf.biz.kb.part.FaqMedCaseBodyListResult;
import com.sm.pfprod.model.dto.biz.tests.PfTestExamDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestExamTagDto;
import com.sm.pfprod.model.dto.biz.tests.PfTestWatingRoomDto;
import com.sm.pfprod.model.enums.SysDicGroupEnum;
import com.sm.pfprod.model.enums.SysParamEnum;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.test.PfWaitingRoomDieReasonVo;
import com.sm.pfprod.model.vo.biz.test.PfWaitingRoomPatVo;
import com.sm.pfprod.model.vo.biz.test.paper.PfTestPaperVo;
import com.sm.pfprod.service.biz.check.PfCheckService;
import com.sm.pfprod.service.biz.kb.PfKbPartService;
import com.sm.pfprod.service.biz.tests.PfTestWaitingRoomService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.security.CurrentUserUtils;
import com.sm.pfprod.web.security.SecurityContext;
import com.sm.pfprod.web.security.User;
import com.sm.pfprod.web.util.EnumUtil;
import com.sm.pfprod.web.util.ParamUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: PfTestWaitingRoomController
 * @Description: 候诊室
 * @Author yangtongbin
 * @Date 2018/11/4
 */
@Controller
@RequestMapping(value = "/pf/p/waiting/room")
public class PfTestWaitingRoomController extends BaseController {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private ZoneOffset offset = ZoneOffset.ofHours(8);

    @Resource
    private PfTestWaitingRoomService pfTestWaitingRoomService;

    @Resource
    private EnumUtil enumUtil;

    @Resource
    private ParamUtil paramUtil;

    @Resource
    private PfCheckService pfCheckService;

    @Resource
    private PfKbPartService pfKbPartService;

    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping("/page")
    public String paperPage(Model model) {
        model.addAttribute("sexEnum", enumUtil.getEnumList(SysDicGroupEnum.SEX.getCode()));
        model.addAttribute("showCancel", SecurityContext.hasRole("ROLE_EXM0030_CANCEL"));
        return "pages/biz/tests/room/waitingRoomPage";
    }

    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping("/student")
    public String paperStudentPage(Model model) {
        PfTestWatingRoomDto dto = new PfTestWatingRoomDto();
        dto.setPage(1);
        dto.setLimit(20);
        PageResult pageResult = this.listWaitingRoom(dto);

        model.addAttribute("result", pageResult.getData());
        model.addAttribute("sexEnum", enumUtil.getEnumList(SysDicGroupEnum.SEX.getCode()));
        model.addAttribute("showCancel", SecurityContext.hasRole("ROLE_EXM0030_CANCEL"));

        return "pages/biz/tests/room/studentRoomPage";
    }

    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping("/exam/page")
    public String papPage(Model model, PfTestExamDto dto) {
        model.addAttribute("idTestplanDetail", dto.getIdTestplanDetail());
        model.addAttribute("idTestplan", dto.getIdTestplan());
        model.addAttribute("idDemo", dto.getIdDemo());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTestpaper", "idTestpaper");
        model.addAttribute("patName", dto.getPatName());
        //model.addAttribute("patSex", dto.getPatSex().trim());
        model.addAttribute("patAge", dto.getPatAge());
        model.addAttribute("complain", dto.getComplain());
        model.addAttribute("idMedCase", dto.getIdMedCase());

        PfTestPaperVo pfTestPaperVo = pfTestWaitingRoomService.selectTestPaperInfo(dto);

        pfTestPaperVo.setTags(pfTestPaperVo.getTags().stream()
                .filter(tag -> tag.getIdTag() != 3).collect(Collectors.toList()));

        if (pfTestPaperVo.getStudentInfo() != null) {
            pfTestPaperVo.getStudentInfo().setSex(enumUtil.getEnumTxt(SysDicGroupEnum.SEX.getCode(), pfTestPaperVo.getStudentInfo().getSex()));
        }

        long hours = 0;
        long minutes = 0;
        long seconds = 0;

        if ( pfTestPaperVo.getPaperInfo().getBeginTimeStr() != null) {
            LocalDateTime beginTime = LocalDateTime.parse(pfTestPaperVo.getPaperInfo().getBeginTimeStr(), formatter);
            LocalDateTime now = LocalDateTime.now();
            Duration between = Duration.between(beginTime, now);

            long beginTimestamp = beginTime.toInstant(offset).toEpochMilli();
            long nowTimestamp = LocalDateTime.now().toInstant(offset).toEpochMilli();
            long l = nowTimestamp - beginTimestamp;
            LocalDateTime localTime = Instant.ofEpochMilli(l).atZone(ZoneId.systemDefault()).toLocalDateTime();

            hours = between.toDays() * 24 + localTime.getHour();
            minutes = localTime.getMinute();
            seconds = localTime.getSecond();
        }

        model.addAttribute("h", hours);
        model.addAttribute("m", minutes);
        model.addAttribute("s", seconds);
        model.addAttribute("link", JSON.toJSONString(pfTestPaperVo.getLink()));
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
        model.addAttribute("sdTestexec", dto.getSdTestexec());
        model.addAttribute("idTestexecResult", dto.getIdTestexecResult());
        model.addAttribute("patient", pfKbPartService.selectKbPat(dto.getIdMedCase()));
        model.addAttribute("executingShowExpert", paramUtil.getParamValue(SysParamEnum.EXECUTING_SHOW_EXPERT.getCode()));
        model.addAttribute("completedShowExpert", paramUtil.getParamValue(SysParamEnum.COMPLETED_SHOW_EXPERT.getCode()));
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
        model.addAttribute("patient", pfKbPartService.selectKbPat(dto.getIdMedCase()));
        model.addAttribute("sdTestexec", dto.getSdTestexec());
        PfTestExamTagDto dto1 = new PfTestExamTagDto();
        dto1.setIdMedicalrec(dto.getIdMedicalrec());
        dto1.setCdMedAsse(dto.getCdMedAsse());
        dto1.setIdTestexecResult(dto.getIdTestexecResult());
        dto1.setOffset(0L);
        dto1.setPage(1);
        dto1.setLimit(1000000000);
        PageResult pageResult = pfTestWaitingRoomService.listTestCheck(dto1);
        List<FaqMedCaseBodyListResult> data = pageResult.getData();
        Map<String, Map<String, List<FaqMedCaseBodyListResult>>> collect = data.stream()
                .collect(Collectors.groupingBy(FaqMedCaseBodyListResult::getCdCheckText,
                        Collectors.groupingBy(FaqMedCaseBodyListResult::getSdBody)));

        model.addAttribute("executingShowExpert", paramUtil.getParamValue(SysParamEnum.EXECUTING_SHOW_EXPERT.getCode()));
        model.addAttribute("completedShowExpert", paramUtil.getParamValue(SysParamEnum.COMPLETED_SHOW_EXPERT.getCode()));
        // 位置
        model.addAttribute("bodyPosition", enumUtil.getEnumList(SysDicGroupEnum.BODY_POSITION.getCode()));
        model.addAttribute("map", JSON.toJSON(collect));
        model.addAttribute("listQuestionClassifyTree", pfCheckService.listQuestionClassifyTree());
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
        model.addAttribute("sdTestexec", dto.getSdTestexec());
        model.addAttribute("patient", pfKbPartService.selectKbPat(dto.getIdMedCase()));
        model.addAttribute("executingShowExpert", paramUtil.getParamValue(SysParamEnum.EXECUTING_SHOW_EXPERT.getCode()));
        model.addAttribute("completedShowExpert", paramUtil.getParamValue(SysParamEnum.COMPLETED_SHOW_EXPERT.getCode()));
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
        model.addAttribute("sdEva", enumUtil.getEnumList(SysDicGroupEnum.SD_EVA.getCode()));
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
        model.addAttribute("sdEva", enumUtil.getEnumList(SysDicGroupEnum.SD_EVA.getCode()));
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
        model.addAttribute("idTestexecResult", dto.getIdTestexecResult());
        model.addAttribute("sdEva", enumUtil.getEnumList(SysDicGroupEnum.SD_EVA.getCode()));
        return "pages/biz/tests/room/exec/summaryPage";
    }

    /**
     * 查询确诊理由
     *
     * @param idTestexecResultDiagnosis
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping(value = "/test/die/reason/list")
    @ResponseBody
    public PageResult listDieReason(Long idTestexecResultDiagnosis) {
        return pfTestWaitingRoomService.listDieReason(idTestexecResultDiagnosis);
    }

    /**
     * 查询已做问诊、检查、检验
     *
     * @param
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping(value = "/test/die/ready/reason/list")
    @ResponseBody
    public PageResult listReadyDieReason(Long idTestexecResult, String keyword) {
        List<PfWaitingRoomDieReasonVo> dieReasonVos = pfTestWaitingRoomService.listReadyDieReason(idTestexecResult, keyword);
        return PageResult.create(dieReasonVos);
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
        model.addAttribute("sdPosition", enumUtil.getEnumList(SysDicGroupEnum.SD_POSITION.getCode()));
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

    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping("/assess/page")
    public String assessPage(Model model) {
        model.addAttribute("sexEnum", enumUtil.getEnumList(SysDicGroupEnum.SEX.getCode()));
        model.addAttribute("exmEvaResult", enumUtil.getEnumList(SysDicGroupEnum.EXM_EVAR_ESULT.getCode()));
        return "pages/biz/tests/room/assessPage";
    }

    /**
     * 模拟评估
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping("/test/assess/page")
    public String testAssessPage(Model model, PfTestExamDto dto) {
        model.addAttribute("idTestplanDetail", dto.getIdTestplanDetail());
        model.addAttribute("idTestplan", dto.getIdTestplan());
        model.addAttribute("idDemo", dto.getIdDemo());
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("idTestpaper", dto.getIdTestpaper());
        model.addAttribute("idStudent", dto.getIdStudent());
        model.addAttribute("idTestexecResult", dto.getIdTestexecResult());

        PfTestPaperVo pfTestPaperVo = pfTestWaitingRoomService.selectTestPaper(dto);
        if (pfTestPaperVo.getStudentInfo() != null) {
            pfTestPaperVo.getStudentInfo().setSex(enumUtil.getEnumTxt(SysDicGroupEnum.SEX.getCode(), pfTestPaperVo.getStudentInfo().getSex()));
        }
        model.addAttribute("examInfo", pfTestPaperVo);
        return "pages/biz/tests/room/assess/testAssessPage";
    }


    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listWaitingRoom(PfTestWatingRoomDto dto) {
        User user = CurrentUserUtils.getCurrentUser();
        dto.setIdOrg(user.getIdOrg());
        // 学生只能查看自己的记录
        if (user.getRoleCodes().contains("MCST")) {
            dto.setCurrentUserId(user.getUserId());
        } else {
            dto.setCurrentUserId(null);
        }
        return pfTestWaitingRoomService.listWaitingRoom(dto);
    }

/*    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping(value = "/list/student")
    @ResponseBody
    public PageResult listWaitingStudentRoom(PfTestWatingRoomDto dto) {
        User user = CurrentUserUtils.getCurrentUser();
        dto.setIdOrg(user.getIdOrg());
        // 学生只能查看自己的记录
        if (user.getRoleCodes().contains("MCST")) {
            dto.setCurrentUserId(user.getUserId());
        } else {
            dto.setCurrentUserId(null);
        }
        return pfTestWaitingRoomService.listWaitingRoom(dto);
    }*/

    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping(value = "/receive/list")
    @ResponseBody
    public PageResult listReceivePat(PfTestWatingRoomDto dto) {
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        return pfTestWaitingRoomService.listReceivePat(dto);
    }

    /**
     * 查询拟诊加入理由
     *
     * @param idTestexecResultReferral
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping(value = "/referral/reason/in/list")
    @ResponseBody
    public PageResult listReferralInReason(Long idTestexecResultReferral) {
        return pfTestWaitingRoomService.listReferralReason(idTestexecResultReferral, YesOrNoNum.NO.getCode());
    }

    /**
     * 查询拟诊排除理由
     *
     * @param idTestexecResultReferral
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping(value = "/referral/reason/out/list")
    @ResponseBody
    public PageResult listReferralOutReason(Long idTestexecResultReferral) {
        return pfTestWaitingRoomService.listReferralReason(idTestexecResultReferral, YesOrNoNum.YES.getCode());
    }

    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping(value = "/all/referral/die")
    @ResponseBody
    public PageResult listAllReferralDie(Long idTestexecResult, String keywords) {
        return pfTestWaitingRoomService.listAllReferralDie(idTestexecResult, keywords);
    }

    /**
     * 页签-初步诊断
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping("/test/diagnosis/preliminary/page")
    public String preliminaryDiagnosisPage(Model model, PfTestExamTagDto dto) {
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("cdMedAsse", dto.getCdMedAsse());
        model.addAttribute("idTestexecResult", dto.getIdTestexecResult());
        return "pages/biz/tests/room/exec/diagnosisPreliminaryPage";
    }

    /**
     * 页签-鉴别诊断
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0030','ROLE_SUPER')")
    @RequestMapping("/test/diagnosis/differential/page")
    public String differentialDiagnosisPage(Model model, PfTestExamTagDto dto) {
        model.addAttribute("idMedicalrec", dto.getIdMedicalrec());
        model.addAttribute("cdMedAsse", dto.getCdMedAsse());
        model.addAttribute("idTestexecResult", dto.getIdTestexecResult());
        model.addAttribute("sdEva", enumUtil.getEnumList(SysDicGroupEnum.SD_EVA.getCode()));
        return "pages/biz/tests/room/exec/diagnosisDifferentialPage";
    }

    /**
     * 查询诊断分析、鉴别诊断列表
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_EXM0040','ROLE_SUPER')")
    @RequestMapping(value = "/diagnostic/chart/list")
    @ResponseBody
    public PageResult listDiagnosticChart(PfTestExamTagDto dto) {
        return pfTestWaitingRoomService.listDiagnosticChart(dto);
    }

}
