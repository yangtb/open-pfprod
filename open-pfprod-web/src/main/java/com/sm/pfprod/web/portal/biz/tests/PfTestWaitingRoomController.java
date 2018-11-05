package com.sm.pfprod.web.portal.biz.tests;

import com.sm.pfprod.model.dto.biz.tests.PfTestWatingRoomDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.tests.PfTestWaitingRoomService;
import com.sm.pfprod.service.system.org.PfOrgService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.util.EnumUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
    private EnumUtil enumUtil;

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/page")
    public String paperPage() {
        return "pages/biz/tests/room/waitingRoomPage";
    }

    @PreAuthorize("hasAnyRole('ROLE_FAQ0010','ROLE_SUPER')")
    @RequestMapping("/exam/page")
    public String papPage() {
        return "pages/biz/tests/room/examPage";
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
