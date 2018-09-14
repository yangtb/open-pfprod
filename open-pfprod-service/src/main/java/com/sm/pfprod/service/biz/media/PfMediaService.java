package com.sm.pfprod.service.biz.media;

import com.sm.pfprod.model.dto.biz.media.PfMediaDto;
import com.sm.pfprod.model.entity.Notice;

import java.util.List;

/**
 * @ClassName: PfMediaService
 * @Description: 咨媒服务
 * @Author yangtongbin
 * @Date 2018/8/28 10:31
 */
public interface PfMediaService {

    /**
     * 获取banner总数
     *
     * @param dto
     * @return
     */
    Long countBanner(PfMediaDto dto);

    /**
     * banner列表
     * @param dto
     * @return
     */
    List<Notice> listBanners(PfMediaDto dto);
}
