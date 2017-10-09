package com.sm.pfprod.web.system.user.menu;

import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.facade.menu.PfMenuFacade;
import com.sm.pfprod.model.dto.user.menu.MenuDto;
import com.sm.pfprod.model.dto.user.role.PfRoleCommonDto;
import com.sm.pfprod.model.entity.SysMenu;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.web.system.BaseController;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: PfMenuRestController
 * @Description: 菜单
 * @Author yangtongbin
 * @Date 2017/9/8 09:57
 */
@Controller
@RequestMapping(value = "/menu")
public class PfMenuRestController extends BaseController {

    @Resource
    private PfMenuFacade pfMenuFacade;

    @RequestMapping("/page")
    public String menu() {
        return "pages/menu/menu";
    }

    @RequestMapping("/form")
    public String form(String formType, Model model) {
        model.addAttribute("formType", formType);
        return "pages/menu/menuForm";
    }

    /**
     * 获取系统菜单
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listMenus(MenuDto dto) {
        return pfMenuFacade.listMenus(dto);
    }

    /**
     * 新增系统菜单
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addMenu(@RequestBody SysMenu dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getLevel() != null, "level");
        //Assert.isTrue(StringUtils.isNotBlank(dto.getUrl()), "url");
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");

        return ResultObject.create("addMenu", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfMenuFacade.addMenu(dto));
    }

    /**
     * 编辑系统菜单
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updateMenu(@RequestBody SysMenu dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getLevel() != null, "level");
        //Assert.isTrue(StringUtils.isNotBlank(dto.getUrl()), "url");
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");

        return ResultObject.create("updateMenu", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfMenuFacade.updateMenu(dto));
    }

    /**
     * 修改系统菜单状态
     */
    @RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject changeStatusMenu(@RequestBody MenuDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getMenuId() != null, "menuId");
        return ResultObject.create("changeStatusMenu", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfMenuFacade.changeStatusMenu(dto));
    }

    /**
     * 批量修改系统菜单状态
     */
    @RequestMapping(value = "/batchChangeStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject batchChangeStatus(@RequestBody List<Map<Long, Integer>> listMap) {
         /* 参数校验 */
        MenuDto dto;
        Assert.isTrue(CollectionUtils.isNotEmpty(listMap), "入参有误");
        for (Map<Long, Integer> map : listMap) {
            dto = new MenuDto();
            try {
                BeanUtils.populate(dto, map);
                pfMenuFacade.changeStatusMenu(dto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResultObject.create("batchChangeStatus", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, true);
    }

    /**
     * 获取系统菜单【tree】
     */
    @RequestMapping(value = "/list/tree", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject listMenuTree() {
        return ResultObject.create("listMenuTree", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_LIST, pfMenuFacade.listMenuTree());
    }

    /**
     * 获取角色拥有菜单
     */
    @RequestMapping(value = "/list/role/tree", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject listMenuRoleTree(@RequestBody PfRoleCommonDto dto) {
        /* 参数校验 */
        Assert.isTrue(dto.getRoleId() != null, "roleId");
        Long roleId = dto.getRoleId();
        return ResultObject.create("listMenuRoleTree", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_LIST, pfMenuFacade.listMenuRoleTree(roleId));
    }

}
