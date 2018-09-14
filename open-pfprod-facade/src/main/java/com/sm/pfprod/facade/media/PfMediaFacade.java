package com.sm.pfprod.facade.media;

import com.sm.pfprod.model.dto.biz.media.PfMediaDto;
import com.sm.pfprod.model.entity.Notice;
import com.sm.pfprod.model.result.PageResult;

/**
 * @ClassName: PfMediaFacade
 * @Description: 资媒facade
 * @Author yangtongbin
 * @Date 2018/8/27 19:56
 */
public interface PfMediaFacade {

    /**
     * 获取字典分组
     *
     * @param dto
     * @return
     */
    PageResult<Notice> listBanners(PfMediaDto dto);


}
