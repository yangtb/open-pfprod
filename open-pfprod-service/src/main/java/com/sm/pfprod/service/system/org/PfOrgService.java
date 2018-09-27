package com.sm.pfprod.service.system.org;

import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.system.org.PfBachOrgDto;
import com.sm.pfprod.model.dto.system.org.PfOrgAuthDto;
import com.sm.pfprod.model.dto.system.org.PfOrgDto;
import com.sm.pfprod.model.entity.SysOrg;
import com.sm.pfprod.model.entity.SysOrgReg;
import com.sm.pfprod.model.result.PageResult;

import java.util.List;
import java.util.Map;

public interface PfOrgService {

    /**
     * 机构列表
     *
     * @param dto
     * @return
     */
    PageResult listOrgs(PfOrgDto dto);

    /**
     * 机构认证列表
     *
     * @param dto
     * @return
     */
    PageResult listAuthOrg(PfOrgAuthDto dto);

    /**
     * 查询所有机构
     *
     * @return
     */
    List<SysOrg> listAllOrg();

    /**
     * 查询所有机构
     *
     * @return Map
     */
    Map<Long, String> listAllOrgMap();

    /**
     * 新增机构
     *
     * @param dto
     * @return
     */
    boolean addOrg(SysOrg dto);

    /**
     * 编辑机构
     *
     * @param dto
     * @return
     */
    boolean editOrg(SysOrg dto);

    /**
     * 删除机构
     *
     * @param dto
     * @return
     */
    boolean delOrg(PfBachChangeStatusDto dto);

    /**
     * 机构认证
     *
     * @param dto
     * @return
     */
    boolean authOrg(PfBachOrgDto dto);

    /**
     * 机构认证驳回
     *
     * @param dto
     * @return
     */
    boolean rejectOrg(PfBachOrgDto dto);

    /**
     * 根据id查询机构信息
     *
     * @param idOrg 机构id
     * @return
     */
    SysOrg selectOrgInfoById(Long idOrg);

    /**
     * 申请激活
     *
     * @param dto
     * @return
     */
    boolean activeOrg(SysOrgReg dto);

}
