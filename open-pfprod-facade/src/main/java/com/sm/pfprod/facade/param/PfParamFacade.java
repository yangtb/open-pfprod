package com.sm.pfprod.facade.param;

import com.sm.pfprod.model.dto.system.param.ParamDto;
import com.sm.pfprod.model.dto.system.param.PfParamListDto;
import com.sm.pfprod.model.entity.SysParam;
import com.sm.pfprod.model.result.PageResult;

import java.util.List;

/**
 * @ClassName: PfParamFacade
 * @Description: 参数facade接口
 * @Author yangtongbin
 * @Date 2017/10/10 10:16
 */
public interface PfParamFacade {

    /**
     * 获取参数列表
     *
     * @param dto
     * @return
     */
    PageResult<SysParam> listParams(ParamDto dto);

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
     * 停用、启用参数
     *
     * @param dto 参数id集合
     * @return
     */
    boolean changeStatus(PfParamListDto dto);

}
