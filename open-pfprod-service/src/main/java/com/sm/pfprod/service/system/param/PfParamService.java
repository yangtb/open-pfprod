package com.sm.pfprod.service.system.param;

import com.sm.pfprod.model.dto.system.param.ParamDto;
import com.sm.pfprod.model.entity.SysParam;

import java.util.List;

/**
 * @ClassName: PfParamService
 * @Description: 参数服务
 * @Author yangtongbin
 * @Date 2017/10/10 10:21
 */
public interface PfParamService {

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
    boolean addParam(SysParam dto);

    /**
     * 编辑参数
     *
     * @param dto
     * @return
     */
    boolean editParam(SysParam dto);

    /**
     * 判断是否存在参数
     *
     * @param paramCode 参数编码
     * @param sysType   作用系统
     * @return
     */
    boolean isExistParam(String paramCode, String sysType);

    /**
     * 停用、启用参数
     *
     * @param list   参数id集合
     * @param status 状态
     * @return
     */
    boolean changeStatus(List<Long> list, String status);
}
