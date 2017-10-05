package com.sm.pfprod.service.system.notice.impl;

import com.sm.pfprod.dal.system.notice.PfNoticeDao;
import com.sm.pfprod.model.dto.system.notice.PfNoticeDto;
import com.sm.pfprod.model.entity.SysNotice;
import com.sm.pfprod.model.vo.system.SysNoticeVo;
import com.sm.pfprod.service.system.notice.PfNoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pfNoticeService")
public class PfNoticeServiceImpl implements PfNoticeService {

    @Resource
    private PfNoticeDao pfNoticeDao;

    @Override
    public Long countNotices(PfNoticeDto dto) {
        return pfNoticeDao.countNotices(dto);
    }

    @Override
    public List<SysNoticeVo> listNotices(PfNoticeDto dto) {
        return pfNoticeDao.listNotices(dto);
    }

    @Override
    public boolean addNotice(SysNotice dto) {
        return pfNoticeDao.addNotice(dto) == 1 ? true : false;
    }

    @Override
    public boolean editNotice(SysNotice dto) {
        return pfNoticeDao.editNotice(dto) == 1 ? true : false;
    }

    @Override
    public boolean delNotice(List<Long> notices) {
        return pfNoticeDao.delNotice(notices) >= 1 ? true : false;
    }
}
