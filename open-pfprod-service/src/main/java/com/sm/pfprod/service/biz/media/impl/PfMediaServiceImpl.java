package com.sm.pfprod.service.biz.media.impl;

import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.media.PfMediaParam;
import com.sm.open.core.facade.model.result.pf.system.notice.NoticeResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.media.MediaClient;
import com.sm.pfprod.model.dto.biz.media.PfMediaDto;
import com.sm.pfprod.model.entity.Notice;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.media.PfMediaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PfMediaServiceImpl implements PfMediaService {

    @Resource
    private MediaClient mediaClient;

    @Override
    public PageResult<Notice> listBanners(PfMediaDto dto) {
        PfPageResult<NoticeResult> result = mediaClient.listBanners(BeanUtil.convert(dto, PfMediaParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }
}
