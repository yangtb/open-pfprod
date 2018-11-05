package com.sm.pfprod.integration.biz.tests;

import com.sm.open.core.facade.model.param.pf.biz.tests.room.PfTestWatingRoomParam;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.tests.PfTestWaitingRoomFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TestWaitingRoomClient {

    @Resource
    private PfTestWaitingRoomFacade pfTestWaitingRoomFacade;

    public PfPageResult listWaitingRoom(PfTestWatingRoomParam param) {
        return pfTestWaitingRoomFacade.listWaitingRoom(param);
    }

    public PfPageResult listReceivePat(PfTestWatingRoomParam param) {
        return pfTestWaitingRoomFacade.listReceivePat(param);
    }
}
