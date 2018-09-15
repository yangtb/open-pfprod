package com.sm.pfprod.integration.system.notice;

import com.sm.open.core.facade.model.param.pf.system.notice.PfNoticeParam;
import com.sm.open.core.facade.model.param.pf.system.notice.SysNoticeParam;
import com.sm.open.core.facade.model.result.pf.system.notice.SysNoticeResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.system.notice.PfNoticeFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class NoticeClient {

    @Resource
    private PfNoticeFacade pfNoticeFacade;

    public PfPageResult<SysNoticeResult> listNotices(PfNoticeParam param) {
        return pfNoticeFacade.listNotices(param);
    }

    public CommonResult<Boolean> addNotice(SysNoticeParam param) {
        return pfNoticeFacade.addNotice(param);
    }

    public CommonResult<Boolean> editNotice(SysNoticeParam param) {
        return pfNoticeFacade.editNotice(param);
    }

    public CommonResult<Boolean> delNotice(List<Long> notices) {
        return pfNoticeFacade.delNotice(notices);
    }
}
