package com.sm.pfprod.facade.notice;

import com.sm.open.care.core.annotation.Loggable;
import com.sm.open.care.core.enums.Level;
import com.sm.pfprod.model.dto.system.notice.PfNoticeDto;
import com.sm.pfprod.model.entity.SysNotice;
import com.sm.pfprod.model.param.PageParam;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.result.ResultFactory;
import com.sm.pfprod.service.system.notice.PfNoticeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("pfNoticeFacade")
public class PfNoticeFacadeImpl implements PfNoticeFacade {

    @Resource
    private PfNoticeService pfNoticeService;

    @Loggable(bizDec = "获取系统公告列表", level = Level.info)
    @Override
    public PageResult listNotices(PfNoticeDto dto) {
        try {
            PageParam.initPageDto(dto);
            return ResultFactory.initPageResultWithSuccess(pfNoticeService.countNotices(dto), pfNoticeService.listNotices(dto));
        } catch (Exception e) {
            return ResultFactory.initPageResultWithError(PfNoticeConstant.SELECT_PAGE_NOTICE_LIST_ERROR,
                    PfNoticeConstant.SELECT_PAGE_NOTICE_LIST_ERROR_MSG);
        }
    }

    @Loggable(bizDec = "新增公告", level = Level.info)
    @Override
    public boolean addNotice(SysNotice dto) {
        return pfNoticeService.addNotice(dto);
    }

    @Loggable(bizDec = "编辑公告", level = Level.info)
    @Override
    public boolean editNotice(SysNotice dto) {
        return pfNoticeService.editNotice(dto);
    }

    @Loggable(bizDec = "删除公告", level = Level.info)
    @Override
    public boolean delNotice(List<Long> notices) {
        return pfNoticeService.delNotice(notices);
    }
}
