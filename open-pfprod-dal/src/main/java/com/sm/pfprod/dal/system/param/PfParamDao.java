package com.sm.pfprod.dal.system.param;

import com.sm.pfprod.model.dto.system.param.ParamDto;
import com.sm.pfprod.model.entity.SysParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfParamDao {

    /**
     * 获取参数列表
     *
     * @param dto
     * @return
     */
    List<SysParam> listParams(ParamDto dto);

    /**
     * 获取参数总数
     *
     * @param dto
     * @return
     */
    Long count(ParamDto dto);

    /**
     * 获取所有参数
     *
     * @return
     */
    List<SysParam> listAllParam();

    /**
     * 新增参数
     *
     * @param dto
     * @return
     */
    int addParam(SysParam dto);

    /**
     * 编辑参数
     *
     * @param dto
     * @return
     */
    int editParam(SysParam dto);

    /**
     * 判断是否存在参数
     *
     * @param paramCode 参数编码
     * @param sysType   作用系统
     * @return
     */
    int isExistParam(@Param("paramCode") String paramCode,
                     @Param("sysType") String sysType);

    /**
     * 停用、启用参数
     *
     * @param list   参数ID集合
     * @param status 状态
     * @return
     */
    int changeStatus(@Param("list") List<Long> list,
                     @Param("status") String status);
}
