package com.sm.pfprod.web.rest.user.menu;

import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.facade.menu.PfMenuFacade;
import com.sm.pfprod.model.dto.system.menu.PfMenuListDto;
import com.sm.pfprod.model.dto.user.menu.MenuDto;
import com.sm.pfprod.model.dto.user.role.PfRoleCommonDto;
import com.sm.pfprod.model.entity.SysFunction;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: PfMenuRestController
 * @Description: 菜单
 * @Author yangtongbin
 * @Date 2017/9/8 09:57
 */
@Controller
@RequestMapping(value = "/pf/r/menu")
public class PfMenuRestController {

    @Resource
    private PfMenuFacade pfMenuFacade;

    /**
     * 新增系统菜单
     */
    @PreAuthorize("hasAnyRole('ROLE_MENU_ADD','ROLE_SUPER')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addMenu(@RequestBody SysFunction dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getLevel() != null, "level");
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");

        return ResultObject.create("addMenu", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfMenuFacade.addMenu(dto));
    }

    /**
     * 编辑系统菜单
     */
    @PreAuthorize("hasAnyRole('ROLE_MENU_EDIT','ROLE_SUPER')")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updateMenu(@RequestBody SysFunction dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getLevel() != null, "level");
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");

        return ResultObject.create("updateMenu", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfMenuFacade.updateMenu(dto));
    }

    /**
     * 修改系统菜单状态
     */
    @PreAuthorize("hasAnyRole('ROLE_MENU_STATUS','ROLE_SUPER')")
    @RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject changeStatusMenu(@RequestBody PfMenuListDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "入参不能为空");
        Assert.isTrue(StringUtils.isNotBlank(dto.getStatus()), "status");
        return ResultObject.create("changeStatusMenu", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfMenuFacade.changeStatusMenu(dto));
    }

}
