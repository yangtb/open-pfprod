package com.sm.pfprod.service.user;

import com.alibaba.fastjson.JSON;
import com.sm.pfprod.model.dto.common.User;
import com.sm.pfprod.model.vo.menu.PfMenuVo;
import com.sm.pfprod.service.TestBase;
import com.sm.pfprod.service.user.menu.PfMenuService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TestPfMenuService extends TestBase {

    private PfMenuService pfMenuService;

    @Before
    public void before() {
        pfMenuService = context.getBean(PfMenuService.class);
    }

    @Test
    public void testListMenus() {
        User user = new User();
        user.setUserId(4L);
        user.setRoleType("1");
        List<PfMenuVo> list = pfMenuService.listMyMenus(user);
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void testListMenuTree() {
        List<PfMenuVo> list = pfMenuService.listMenuTree();
        System.out.println(JSON.toJSONString(list));
    }

    @After
    public void after() {
        if (null != pfMenuService) {
            pfMenuService = null;
        }
    }
}
