package com.sm.pfprod.dal.system.dic;

import com.sm.pfprod.model.dto.system.dic.PfDicDto;
import com.sm.pfprod.model.entity.SysDictionary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfDicDao {

    /**
     * 查询才单分组
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
    int isExistDic(@Param("dictCode") String dictCode);

    /**
     * 新增字典
     *
     * @param dto
     * @return
     */
    int addDic(SysDictionary dto);

    /**
     * 编辑字典
     *
     * @param dto
     * @return
     */
    int editDic(SysDictionary dto);

    /**
     * 删除字典
     *
     * @param list
     * @return
     */
    int delDic(@Param("list") List<Long> list);

    /**
     * 判断是否存在该字典
     *
     * @param dictCode  字典编码
     * @param groupCode 字典分组
     * @return
     */
    int isExistEnum(@Param("dictCode") String dictCode,
                    @Param("groupCode") String groupCode);
}
