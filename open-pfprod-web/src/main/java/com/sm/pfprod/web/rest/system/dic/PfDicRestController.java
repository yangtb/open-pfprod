package com.sm.pfprod.web.rest.system.dic;

import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.common.PfCommonListDto;
import com.sm.pfprod.model.entity.SysDictionary;
import com.sm.pfprod.service.system.dic.PfDicService;
import com.sm.pfprod.web.security.CurrentUserUtils;
import com.sm.pfprod.web.util.EnumUtil;
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
 * @ClassName: PfDicRestController
 * @Description: 字典管理
 * @Author yangtongbin
 * @Date 2017/10/9 11:05
 */
@Controller
@RequestMapping(value = "/pf/r/dic")
public class PfDicRestController {

    @Resource
    private PfDicService pfDicService;

    @Resource
    private EnumUtil enumUtil;

    /**
     * 新增字典
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_DIC_ADD','ROLE_SUPER')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addDic(@RequestBody SysDictionary dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getDictCode()), "dictCode");
        Assert.isTrue(StringUtils.isNotBlank(dto.getDictName()), "dictName");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.create("addDic", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfDicService.addDic(dto));
    }

    /**
     * 编辑字典
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_DIC_EDIT','ROLE_SUPER')")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editDic(@RequestBody SysDictionary dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getDictCode()), "dictCode");
        Assert.isTrue(StringUtils.isNotBlank(dto.getDictName()), "dictName");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.create("editDic", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfDicService.editDic(dto));
    }

    /**
     * 删除字典枚举
     *
     * @param dto 字典id集合
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_DIC_DEL','ROLE_SUPER')")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delDic(@RequestBody PfCommonListDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "入参不能为空");
        return ResultObject.create("delDic", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfDicService.delDic(dto.getList()));
    }

    /**
     * 新增枚举
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_DIC_ENUM_ADD','ROLE_SUPER')")
    @RequestMapping(value = "/enum/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addEnum(@RequestBody SysDictionary dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getGroupCode()), "groupCode");
        Assert.isTrue(StringUtils.isNotBlank(dto.getGroupName()), "groupCode");
        Assert.isTrue(StringUtils.isNotBlank(dto.getDictCode()), "dictCode");
        Assert.isTrue(StringUtils.isNotBlank(dto.getDictName()), "dictName");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.create("addEnum", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfDicService.addEnum(dto));
    }

    /**
     * 编辑枚举
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_DIC_ENUM_EDIT','ROLE_SUPER')")
    @RequestMapping(value = "/enum/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editEnum(@RequestBody SysDictionary dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getGroupCode()), "groupCode");
        Assert.isTrue(StringUtils.isNotBlank(dto.getGroupName()), "groupCode");
        Assert.isTrue(StringUtils.isNotBlank(dto.getDictCode()), "dictCode");
        Assert.isTrue(StringUtils.isNotBlank(dto.getDictName()), "dictName");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.create("editEnum", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfDicService.editEnum(dto));
    }

    /**
     * 刷新缓存
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_DIC_REFRESHCACHE','ROLE_SUPER')")
    @RequestMapping(value = "/refreshCache", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject refreshCache() {
        enumUtil.init();
        return ResultObject.create("refreshCache", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, true);
    }

}
