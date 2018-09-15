package com.sm.pfprod.integration.biz.media;

import com.sm.open.core.facade.model.param.pf.biz.media.PfMediaParam;
import com.sm.open.core.facade.model.result.pf.system.notice.NoticeResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.media.PfMediaFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MediaClient {

    @Resource
    private PfMediaFacade pfMediaFacade;

    public PfPageResult<NoticeResult> listBanners(PfMediaParam param) {
        return pfMediaFacade.listBanners(param);
    }
}
