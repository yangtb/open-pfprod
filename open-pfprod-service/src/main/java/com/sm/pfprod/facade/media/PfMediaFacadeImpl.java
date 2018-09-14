package com.sm.pfprod.facade.media;

import com.sm.pfprod.model.dto.biz.media.PfMediaDto;
import com.sm.pfprod.model.entity.Notice;
import com.sm.pfprod.model.param.PageParam;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.result.ResultFactory;
import com.sm.pfprod.service.biz.media.PfMediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("pfMediaFacade")
public class PfMediaFacadeImpl implements PfMediaFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfMediaFacadeImpl.class);

    @Resource
    private PfMediaService pfMediaService;

    @Override
    public PageResult<Notice> listBanners(PfMediaDto dto) {
        try {
            PageParam.initPageDto(dto);
            return ResultFactory.initPageResultWithSuccess(pfMediaService.countBanner(dto), pfMediaService.listBanners(dto));
        } catch (Exception e) {
            return ResultFactory.initPageResultWithError(PfMediaConstant.SELECT_BANNERS_ERROR,
                    PfMediaConstant.SELECT_BANNERS_ERROR_MSG);
        }
    }
}
