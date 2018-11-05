package com.sm.pfprod.web.rest.biz.tests;

import com.sm.open.care.core.ResultObject;
import com.sm.pfprod.model.entity.ExmTestplan;
import com.sm.pfprod.service.biz.tests.PfTestWaitingRoomService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.security.CurrentUserUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName: PfTestWaitingRoomController
 * @Description: 候诊室
 * @Author yangtongbin
 * @Date 2018/11/4
 */
@RestController
@RequestMapping(value = "/pf/r/waiting/room/")
public class PfTestWaitingRoomRestController extends BaseController {

    @Resource
    private PfTestWaitingRoomService pfTestWaitingRoomService;

    /**
     * 新增计划
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_STD0010','ROLE_SUPER')")
    @PostMapping(value = "/add")
    @ResponseBody
    public ResultObject addPlan(@RequestBody ExmTestplan dto) {
        /* 参数校验 */
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        dto.setIdOrg(CurrentUserUtils.getCurrentUserIdOrg());
        return ResultObject.createSuccess("addPlan", ResultObject.DATA_TYPE_OBJECT, null);
    }

}
