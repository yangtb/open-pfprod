package com.sm.pfprod.service.system.org;

import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.system.org.PfOrgDto;
import com.sm.pfprod.model.entity.SysOrg;
import com.sm.pfprod.model.result.PageResult;

public interface PfOrgService {

    /**
     * 机构列表
     *
     * @param dto
     * @return
     */
    PageResult listOrgs(PfOrgDto dto);

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
    boolean authOrg(PfBachChangeStatusDto dto);
}
