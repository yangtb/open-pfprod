package com.sm.pfprod.service.user.role;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.sm.pfprod.model.dto.user.common.PfCommonDto;
import com.sm.pfprod.model.entity.SysRole;
import com.sm.pfprod.model.vo.role.PfRoleVo;
import com.sm.pfprod.service.TestBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPfRoleService extends TestBase {

    private PfRoleService pfRoleService;

    @Before
    public void before() {
        pfRoleService = context.getBean(PfRoleService.class);
    }

    @Test
    public void testListMenuTree() {
        PfCommonDto dto = new PfCommonDto();
        PageInfo<PfRoleVo> list = pfRoleService.listRoles(dto);
        System.out.println(JSON.toJSONString(list));
    }

    @After
    public void after() {
        if (null != pfRoleService) {
            pfRoleService = null;
        }
    }
}
