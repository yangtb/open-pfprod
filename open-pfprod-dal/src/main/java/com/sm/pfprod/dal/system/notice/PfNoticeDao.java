package com.sm.pfprod.dal.system.notice;

import com.sm.pfprod.model.dto.system.notice.PfNoticeDto;
import com.sm.pfprod.model.entity.SysNotice;
import com.sm.pfprod.model.vo.system.SysNoticeVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfNoticeDao {

    /**
     * 获取公告list
     *
     * @return
     */
    List<SysNoticeVo> listNotices(PfNoticeDto dto);

    /**
     * 获取公告总数
     *
     * @return
     */
    Long countNotices(PfNoticeDto dto);

    /**
     * 新增公告
     *
     * @param dto
     * @return
     */
    int addNotice(SysNotice dto);

    /**
     * 编辑公告
     *
     * @param dto
     * @return
     */
    int editNotice(SysNotice dto);

    /**
     * 删除公告
     *
     * @param notices 公告ID
     * @return
     */
    int delNotice(@Param("list") List<Long> notices);

}
