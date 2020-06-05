package com.sm.pfprod.service.system.notice.impl;

import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.system.notice.PfNoticeParam;
import com.sm.open.core.facade.model.param.pf.system.notice.SysNoticeParam;
import com.sm.open.core.facade.model.result.pf.system.notice.SysNoticeResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.system.notice.NoticeClient;
import com.sm.pfprod.model.dto.system.notice.PfNoticeDto;
import com.sm.pfprod.model.entity.SysNotice;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.system.notice.PfNoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PfNoticeServiceImpl implements PfNoticeService {

    @Resource
    private NoticeClient noticeClient;

    @Override
    public PageResult listNotices(PfNoticeDto dto) {
        PfPageResult<SysNoticeResult> result = noticeClient.listNotices(BeanUtil.convert(dto, PfNoticeParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addNotice(SysNotice dto) {
        CommonResult<Boolean> result = noticeClient.addNotice(BeanUtil.convert(dto, SysNoticeParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public boolean editNotice(SysNotice dto) {
        CommonResult<Boolean> result = noticeClient.editNotice(BeanUtil.convert(dto, SysNoticeParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public boolean delNotice(List<Long> notices) {
        CommonResult<Boolean> result = noticeClient.delNotice(notices);
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }
}
