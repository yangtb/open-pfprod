package com.sm.pfprod.web.rest.user.role;

import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.facade.role.PfRoleFacade;
import com.sm.pfprod.model.dto.user.role.PfRoleListDto;
import com.sm.pfprod.model.dto.user.role.PfRoleMenuDto;
import com.sm.pfprod.model.entity.SysRole;
import com.sm.pfprod.web.security.CurrentUserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: PfRoleRestController
 * @Description: 角色模块
 * @Author yangtongbin
 * @Date 2017/9/17 23:13
 */
@Controller
@RequestMapping(value = "/pf/r/role")
public class PfRoleRestController {

    @Resource
    private PfRoleFacade pfRoleFacade;

    /**
     * 新增角色
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addRole(@RequestBody SysRole dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        Assert.isTrue(StringUtils.isNotBlank(dto.getResume()), "resume");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.create("saveRole", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfRoleFacade.addRole(dto));
    }

    /**
     * 编辑角色
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editRole(@RequestBody SysRole dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getRoleId() != null, "roleId");
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        Assert.isTrue(StringUtils.isNotBlank(dto.getResume()), "resume");
        return ResultObject.create("editRole", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfRoleFacade.editRole(dto));
    }

    /**
     * 删除角色
     *
     * @param roles 角色id集合
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delRole(@RequestBody List<Long> roles) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(roles), "入参不能为空");
        return ResultObject.create("delRole", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfRoleFacade.delRole(roles));
    }

    /**
     * 作废/恢复角色
     *
     * @param roles 角色id集合
     * @return
     */
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject cancelRole(@RequestBody PfRoleListDto roles) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(roles.getRoles()), "入参不能为空");
        return ResultObject.create("cancelRole", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfRoleFacade.cancelRole(roles));
    }

    /**
     * 保存角色菜单
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/save/roleMenu", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject saveRoleMenu(@RequestBody PfRoleMenuDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getRoleMenus()), "roleMenus");
        return ResultObject.create("saveRoleMenu", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfRoleFacade.saveRoleMenu(dto));
    }

}
