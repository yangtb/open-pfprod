package com.sm.pfprod.service.biz.media;

import com.sm.pfprod.model.dto.biz.media.PfMediaDto;
import com.sm.pfprod.model.entity.Notice;
import com.sm.pfprod.model.result.PageResult;

/**
 * @ClassName: PfMediaService
 * @Description: 资媒
 * @Author yangtongbin
 * @Date 2018/8/27 19:56
 */
public interface PfMediaService {

    /**
     * 获取字典分组
     *
     * @param dto
     * @return
     */
    PageResult<Notice> listBanners(PfMediaDto dto);


}
