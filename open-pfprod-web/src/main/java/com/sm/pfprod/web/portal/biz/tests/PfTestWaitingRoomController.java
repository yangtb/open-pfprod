package com.sm.pfprod.web.portal.biz.tests;

import com.alibaba.fastjson.JSON;
import com.sm.pfprod.model.dto.biz.tests.PfTestWatingRoomDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.kb.PfCaseHistoryService;
import com.sm.pfprod.service.biz.tests.PfTestWaitingRoomService;
import com.sm.pfprod.service.system.org.PfOrgService;
import com.sm.pfprod.web.portal.BaseController;
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
@RequestMapping(value = "/pf/p/waiting/room/")
public class PfTestWaitingRoomController extends BaseController {

    @Resource
    private PfOrgService pfOrgService;

    @Resource
    private PfTestWaitingRoomService pfTestWaitingRoomService;

    @Resource
    private PfCaseHistoryService pfCaseHistoryService;

    @Resource
    private EnumUtil enumUtil;

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/page")
    public String paperPage() {
        return "pages/biz/tests/room/waitingRoomPage";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/exam/page")
    public String papPage(Model model) {
        //todo
        model.addAttribute("tags", JSON.parseArray(JSON.toJSONString(pfCaseHistoryService.listAllCaseHistoryTag(1L))));
        return "pages/biz/tests/room/testPage";
    }

    /**
     * 页签-患者信息
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/test/pat/page")
    public String patPage(Model model) {
        return "pages/biz/tests/room/exec/patPage";
    }

    /**
     * 页签-问诊
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/test/cons/page")
    public String consPage(Model model) {
        return "pages/biz/tests/room/exec/consPage";
    }

    /**
     * 页签-检查
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/test/check/page")
    public String checkPage(Model model) {
        return "pages/biz/tests/room/exec/checkPage";
    }

    /**
     * 页签-检验
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/test/exam/page")
    public String examPage(Model model) {
        return "pages/biz/tests/room/exec/examPage";
    }

    /**
     * 页签-拟诊
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/test/referral/page")
    public String referralPage(Model model) {
        return "pages/biz/tests/room/exec/referralPage";
    }

    /**
     * 页签-病史小结
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/test/summary/page")
    public String summaryPage(Model model) {
        return "pages/biz/tests/room/exec/summaryPage";
    }

    /**
     * 页签-医嘱
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/test/orders/page")
    public String ordersPage(Model model) {
        return "pages/biz/tests/room/exec/ordersPage";
    }

    /**
     * 模拟评估
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/test/assess/page")
    public String testAssessPage(Model model) {
        return "pages/biz/tests/room/assess/testAssessPage";
    }


    @PreAuthorize("hasAnyRole('ROLE_STD0030','ROLE_SUPER')")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listWaitingRoom(PfTestWatingRoomDto dto) {
        return pfTestWaitingRoomService.listWaitingRoom(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_STD0030','ROLE_SUPER')")
    @RequestMapping(value = "/receive/list")
    @ResponseBody
    public PageResult listReceivePat(PfTestWatingRoomDto dto) {
        return pfTestWaitingRoomService.listReceivePat(dto);
    }

}
