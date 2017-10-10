package com.sm.pfprod.service.system.dic;

import com.sm.pfprod.model.dto.system.dic.PfDicDto;
import com.sm.pfprod.model.entity.SysDictionary;

import java.util.List;

/**
 * @ClassName: PfDicService
 * @Description: 字典服务
 * @Author yangtongbin
 * @Date 2017/10/9 17:28
 */
public interface PfDicService {

    /**
     * 获取字典分组
     *
     * @param dto
     * @return
     */
    List<SysDictionary> listDicGroups(PfDicDto dto);

    /**
     * 获取字典枚举
     *
     * @param dto
     * @return
     */
    List<SysDictionary> listEnums(PfDicDto dto);

    /**
     * 获取字典枚举总数
     *
     * @param dto
     * @return
     */
    Long countEnum(PfDicDto dto);

    /**
     * 获取所有枚举
     *
     * @return
     */
    List<SysDictionary> listAllEnums();

    /**
     * 判断是否存在该字典
     *
     * @param dictCode 字典编码
     * @return
     */
    boolean isExistDic(String dictCode);

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
     * 判断是否存在该字典
     *
     * @param dictCode  字典编码
     * @param groupCode 字典分组
     * @return
     */
    boolean isExistEnum(String dictCode, String groupCode);

}
