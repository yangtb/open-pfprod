package com.sm.pfprod.service.system.dic;

import com.sm.pfprod.model.dto.system.dic.PfDicDto;
import com.sm.pfprod.model.entity.SysDictionary;
import com.sm.pfprod.model.result.PageResult;

import java.util.List;

/**
 * @ClassName: PfDicFacade
 * @Description: 字典
 * @Author yangtongbin
 * @Date 2017/10/9 17:13
 */
public interface PfDicService {

    /**
     * 获取字典分组
     *
     * @param dto
     * @return
     */
    PageResult<SysDictionary> listDicGroups(PfDicDto dto);

    /**
     * 获取字典枚举
     *
     * @param dto
     * @return
     */
    PageResult<SysDictionary> listEnums(PfDicDto dto);

    /**
     * 新增字典
     *
     * @param dto
     * @return
     */
    boolean addDic(SysDictionary dto);

    /**
     * 编辑字典
     *
     * @param dto
     * @return
     */
    boolean editDic(SysDictionary dto);

    /**
     * 删除字典
     *
     * @param list
     * @return
     */
    boolean delDic(List<Long> list);

    /**
     * 新增枚举
     *
     * @param dto
     * @return
     */
    boolean addEnum(SysDictionary dto);

    /**
     * 编辑枚举
     *
     * @param dto
     * @return
     */
    boolean editEnum(SysDictionary dto);

    /**
     * 获取所有枚举
     *
     * @return
     */
    List<SysDictionary> listAllEnums();

}
