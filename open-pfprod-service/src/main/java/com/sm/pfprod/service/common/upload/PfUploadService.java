package com.sm.pfprod.service.common.upload;

import com.sm.pfprod.model.entity.BasMedia;

/**
 * @ClassName: PfUploadService
 * @Description: 基础-多媒体service
 * @Author yangtongbin
 * @Date 2018/10/7
 */
public interface PfUploadService {

    /**
     * 新增多媒体信息
     *
     * @param dto
     * @return
     */
    Long addBasMedia(BasMedia dto);

}
