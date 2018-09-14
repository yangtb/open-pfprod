package com.sm.pfprod.service.biz.media.impl;

import com.sm.pfprod.dal.biz.media.PfMediaDao;
import com.sm.pfprod.model.dto.biz.media.PfMediaDto;
import com.sm.pfprod.model.entity.Notice;
import com.sm.pfprod.service.biz.media.PfMediaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pfMediaService")
public class PfMediaServiceImpl implements PfMediaService {

    @Resource
    private PfMediaDao pfMediaDao;

    @Override
    public Long countBanner(PfMediaDto dto) {
        return pfMediaDao.countBanner(dto);
    }

    @Override
    public List<Notice> listBanners(PfMediaDto dto) {
        return pfMediaDao.listBanners(dto);
    }
}
