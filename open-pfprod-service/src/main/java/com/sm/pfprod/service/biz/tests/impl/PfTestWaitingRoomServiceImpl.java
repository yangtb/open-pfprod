package com.sm.pfprod.service.biz.tests.impl;

import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.tests.room.PfTestWatingRoomParam;
import com.sm.open.core.facade.model.result.pf.biz.tests.room.PfTestWatingRoomResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.tests.TestWaitingRoomClient;
import com.sm.pfprod.model.dto.biz.tests.PfTestWatingRoomDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.tests.PfTestWaitingRoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("pfTestWaitingRoomService")
public class PfTestWaitingRoomServiceImpl implements PfTestWaitingRoomService {

    @Resource
    private TestWaitingRoomClient testWaitingRoomClient;

    @Override
    public PageResult listWaitingRoom(PfTestWatingRoomDto dto) {
        PfPageResult<PfTestWatingRoomResult> result = testWaitingRoomClient.listWaitingRoom(
                BeanUtil.convert(dto, PfTestWatingRoomParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public PageResult listReceivePat(PfTestWatingRoomDto dto) {
        PfPageResult<PfTestWatingRoomResult> result = testWaitingRoomClient.listReceivePat(
                BeanUtil.convert(dto, PfTestWatingRoomParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }
}
