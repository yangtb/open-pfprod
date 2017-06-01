package com.sm.pfprod.web.rest.user.menu;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.common.User;
import com.sm.pfprod.model.dto.user.menu.MenuDto;
import com.sm.pfprod.model.entity.SysMenu;
import com.sm.pfprod.service.common.util.CurrentUserUtil;
import com.sm.pfprod.service.user.menu.PfMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

/**
 * 菜单
 */
@Controller
@RequestMapping(value = "/pf/r/menu")
public class PfMenuRestController {

    @Resource
    private PfMenuService pfMenuService;

    /**
     * 获取用户菜单【tree】
     * @param request
     * @return
     */
    @RequestMapping(value = "/my_list", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject listMyMenus(HttpServletRequest request) {
        User user = CurrentUserUtil.currentUser(request);
        if (user == null || user.getUserId() == null) {
            throw new BizRuntimeException(ErrorCode.ERROR_NET_150001, "session_user为空，访问被拒绝");
        }
        return ResultObject.create("listMyMenus", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_LIST, pfMenuService.listMyMenus(user));
    }

    /**
     * 获取系统菜单
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject listMenus(@RequestBody MenuDto dto) {
        return ResultObject.create("listMenus", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_LIST, pfMenuService.listMenus(dto));
    }

    /**
     * 获取系统菜单【tree】
     * @return
     */
    @RequestMapping(value = "/list_tree", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject listMenuTree() {
        return ResultObject.create("listMenuTree", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_LIST, pfMenuService.listMenuTree());
    }

    /**
     * 新增系统菜单
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addMenu(@RequestBody SysMenu dto) {
        /* 参数校验 */
        Assert.isNull(dto.getLevel(), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "level"));
        Assert.isTrue(StringUtils.isNotBlank(dto.getUrl()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "url"));
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "name"));
        Assert.isNull(dto.getSort(), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "sort"));

        return ResultObject.create("addMenus", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfMenuService.addMenu(dto));
    }

    /**
     * 编辑系统菜单
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updateMenu(@RequestBody SysMenu dto) {
        /* 参数校验 */
        Assert.isNull(dto.getLevel(), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "level"));
        Assert.isTrue(StringUtils.isNotBlank(dto.getUrl()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "url"));
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "name"));
        Assert.isNull(dto.getSort(), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "sort"));

        return ResultObject.create("updateMenu", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfMenuService.updateMenu(dto));
    }

    /**
     * 删除系统菜单
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delMenu(@RequestBody MenuDto dto) {
         /* 参数校验 */
        Assert.isNull(dto.getMenuId(), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "menu_id"));

        return ResultObject.create("delMenu", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfMenuService.delMenu(dto));
    }
}
