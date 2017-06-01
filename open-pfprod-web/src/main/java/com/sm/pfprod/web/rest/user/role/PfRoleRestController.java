package com.sm.pfprod.web.rest.user.role;

import com.sm.open.care.core.ResultObject;
import com.sm.pfprod.model.dto.user.common.PfCommonDto;
import com.sm.pfprod.model.dto.user.role.PfRoleDto;
import com.sm.pfprod.service.user.role.PfRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 角色模块
 */
@Controller
@RequestMapping(value = "/pf/r/role")
public class PfRoleRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfRoleRestController.class);

    @Resource
    private PfRoleService       pfRoleService;

    /**
     * 获取所有角色
     * @param dto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject listRoles(@RequestBody PfCommonDto dto) {
        return ResultObject.create("listRoles", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_LIST, pfRoleService.listRoles(dto));
    }

    /**
     * 新增角色
     * @param dto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject saveRole(@RequestBody PfRoleDto dto) {
        return ResultObject.create("saveRole", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfRoleService.saveRole(dto));
    }

    /**
     * 编辑角色
     * @param dto
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updateRole(@RequestBody PfRoleDto dto) {
        return ResultObject.create("updateRole", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfRoleService.updateRole(dto));
    }

    /**
     * 删除角色
     * @param dto
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delRole(@RequestBody PfRoleDto dto) {
        return ResultObject.create("delRole", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfRoleService.delRole(dto.getRoleId()));
    }

}
