package com.sm.pfprod.service.home;

import com.sm.pfprod.model.dto.home.PfHomeDto;
import com.sm.pfprod.model.vo.home.PfHomeVo;

/**
 * @author yangtongbin
 * @className: PfHomeService
 * @description: 首页信息
 * @date 2018/9/26
 */
public interface PfHomeService {

    /**
     * 获取首页信息
     *
     * @param dto
     * @return
     */
    PfHomeVo selectHomeInfo(PfHomeDto dto);
}
