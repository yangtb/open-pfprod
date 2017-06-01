package com.sm.pfprod.service.user.role.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sm.open.care.core.log.LoggerUtil;
import com.sm.pfprod.dal.user.role.PfRoleDao;
import com.sm.pfprod.model.dto.user.common.PfCommonDto;
import com.sm.pfprod.model.dto.user.role.PfRoleDto;
import com.sm.pfprod.model.entity.SysAuthority;
import com.sm.pfprod.model.entity.SysRole;
import com.sm.pfprod.model.entity.UserRole;
import com.sm.pfprod.model.vo.role.PfRoleVo;
import com.sm.pfprod.service.user.role.PfRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户角色接口实现
 */
@Service("pfRoleService")
public class PfRoleServiceImpl implements PfRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfRoleServiceImpl.class);

    @Resource
    private PfRoleDao           pfRoleDao;

    @Override
    public PageInfo<PfRoleVo> listRoles(PfCommonDto dto) {
         /* 设置分页默认值 */
        if (null == dto.getPageNum() || dto.getPageNum() < 1) {
            dto.setPageNum(1);
        }
        if (null == dto.getPageSize() || dto.getPageSize() < 1) {
            dto.setPageSize(15);
        }
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize()); // 设置分页查询
        List<PfRoleVo> list = pfRoleDao.listRoles();
        return new PageInfo<PfRoleVo>(list);
    }

    @Override
    public List<UserRole> listRole(Long userId) {
        return pfRoleDao.listRole(userId);
    }

    @Override
    public boolean saveRole(PfRoleDto dto) {
        LoggerUtil.info(LOGGER, "【PfRoleServiceImpl-saveRole-params】dto={0}", JSON.toJSONString(dto));
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(dto, sysRole);
        return pfRoleDao.saveRole(sysRole) == 1 ? true : false;
    }

    @Override
    @Transactional
    public boolean updateRole(PfRoleDto dto) {
        LoggerUtil.info(LOGGER, "【PfRoleServiceImpl-updateRole-params】dto={0}", JSON.toJSONString(dto));
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(dto, sysRole);
        return pfRoleDao.updateRole(sysRole) == 1 ? true : false;
    }

    @Override
    public boolean delRole(Long roleId) {
        LoggerUtil.info(LOGGER, "【PfRoleServiceImpl-delRole-params】roleId={0}", roleId);
        return pfRoleDao.delRole(roleId) == 1 ? true : false;
    }

    @Override
    public List<SysAuthority> selectAuthority(Long userId) {
        return pfRoleDao.selectAuthority(userId);
    }


}
