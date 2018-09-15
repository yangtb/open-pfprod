package com.sm.pfprod.service.system.notice;

import com.sm.pfprod.model.dto.system.notice.PfNoticeDto;
import com.sm.pfprod.model.entity.SysNotice;
import com.sm.pfprod.model.result.PageResult;

import java.util.List;

public interface PfNoticeService {

    /**
     * 公告列表
     *
     * @param dto
     * @return
     */
    PageResult listNotices(PfNoticeDto dto);

    /**
     * 新增公告
     *
     * @param dto
     * @return
     */
    boolean addNotice(SysNotice dto);

    /**
     * 编辑公告
     *
     * @param dto
     * @return
     */
    boolean editNotice(SysNotice dto);

    /**
     * 删除公告
     *
     * @param notices 公告ID
     * @return
     */
    boolean delNotice(List<Long> notices);

}
