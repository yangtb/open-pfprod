package com.sm.pfprod.service.user.role.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.user.role.PfRoleListParam;
import com.sm.open.core.facade.model.param.pf.user.role.PfRoleMenuParam;
import com.sm.open.core.facade.model.param.pf.user.role.PfRoleParam;
import com.sm.open.core.facade.model.param.pf.user.role.SysRoleParam;
import com.sm.open.core.facade.model.result.pf.user.role.PfRoleResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.user.role.RoleClient;
import com.sm.pfprod.model.dto.user.role.PfRoleDto;
import com.sm.pfprod.model.dto.user.role.PfRoleListDto;
import com.sm.pfprod.model.dto.user.role.PfRoleMenuDto;
import com.sm.pfprod.model.entity.SysRole;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.role.PfRoleVo;
import com.sm.pfprod.service.user.role.PfRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: PfRoleServiceImpl
 * @Description: 用户角色接口实现
 * @Author yangtongbin
 * @Date 2017/9/9 15:52
 */
@Service("pfRoleService")
public class PfRoleServiceImpl implements PfRoleService {

    @Resource
    private RoleClient roleClient;

    @Override
    public PageResult listRoles(PfRoleDto dto) {
        PfPageResult<PfRoleResult> result = roleClient.listRoles(BeanUtil.convert(dto, PfRoleParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public List<PfRoleVo> list() {
        CommonResult<List<PfRoleResult>> result = roleClient.list();
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfRoleVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<PfRoleVo> listUserRole(Long userId) {
        CommonResult<List<PfRoleResult>> result = roleClient.listUserRole(userId);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfRoleVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean addRole(SysRole dto) {
        CommonResult<Boolean> result = roleClient.addRole(BeanUtil.convert(dto, SysRoleParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public boolean editRole(SysRole dto) {
        CommonResult<Boolean> result = roleClient.editRole(BeanUtil.convert(dto, SysRoleParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public boolean delRole(List<Long> roles) {
        CommonResult<Boolean> result = roleClient.delRole(roles);
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public boolean cancelRole(PfRoleListDto roles) {
        CommonResult<Boolean> result = roleClient.cancelRole(BeanUtil.convert(roles, PfRoleListParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public boolean saveRoleMenu(PfRoleMenuDto dto) {
        CommonResult<Boolean> result = roleClient.saveRoleMenu(BeanUtil.convert(dto, PfRoleMenuParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }
}
