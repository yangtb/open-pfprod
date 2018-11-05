package com.sm.pfprod.service.biz.tests;

import com.sm.pfprod.model.dto.biz.tests.PfTestWatingRoomDto;
import com.sm.pfprod.model.result.PageResult;

public interface PfTestWaitingRoomService {

    /**
     * 候诊室列表
     *
     * @param dto
     * @return
     */
    PageResult listWaitingRoom(PfTestWatingRoomDto dto);

    /**
     * 接诊列表
     *
     * @param dto
     * @return
     */
    PageResult listReceivePat(PfTestWatingRoomDto dto);
}
