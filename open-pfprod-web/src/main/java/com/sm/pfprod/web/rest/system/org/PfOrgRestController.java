package com.sm.pfprod.web.rest.system.org;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.SysOrg;
import com.sm.pfprod.model.entity.SysOrgReg;
import com.sm.pfprod.service.system.org.PfOrgService;
import com.sm.pfprod.web.security.CurrentUserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfOrgRestController
 * @Description: 机构rest服务
 * @Author yangtongbin
 * @Date 2018/9/20 13:59
 */
@Controller
@RequestMapping(value = "/pf/r/org")
public class PfOrgRestController {

    @Resource
    private PfOrgService pfOrgService;

    /**
     * 新增机构
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ORG_ADD','ROLE_SUPER')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addOrg(@RequestBody SysOrg dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return pfOrgService.addOrg(dto) ? ResultObject.createSuccess("addOrg", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("addOrg", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 编辑机构
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ORG_EDIT','ROLE_ORG_DETAIL','ROLE_SUPER')")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editOrg(@RequestBody SysOrg dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdOrg() != null, "idOrg");
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        return pfOrgService.editOrg(dto) ? ResultObject.createSuccess("editOrg", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("editOrg", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);

    }

    /**
     * 删除机构
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ORG_DEL','ROLE_SUPER')")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delOrg(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "入参不能为空");
        return pfOrgService.delOrg(dto) ? ResultObject.createSuccess("delOrg", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("delOrg", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 机构认证
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ORG_AUTH','ROLE_SUPER')")
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject authOrg(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "入参不能为空");
        return pfOrgService.authOrg(dto) ? ResultObject.createSuccess("authOrg", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("authOrg", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }

    /**
     * 申请激活
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ORG_DETAIL','ROLE_SUPER')")
    @RequestMapping(value = "/active", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject activeOrg(@RequestBody SysOrgReg dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getIdOrg() != null, "idOrg");
        String currentUsername = CurrentUserUtils.getCurrentUsername();
        dto.setApplyor(currentUsername);
        dto.setCreator(currentUsername);
        dto.setOperator(currentUsername);
        return pfOrgService.activeOrg(dto) ? ResultObject.createSuccess("activeOrg", ResultObject.DATA_TYPE_OBJECT, true)
                : ResultObject.create("activeOrg", ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
    }


}
