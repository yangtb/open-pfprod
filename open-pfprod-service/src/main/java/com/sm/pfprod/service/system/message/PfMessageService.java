package com.sm.pfprod.service.system.message;

import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.system.message.PfMessageDto;
import com.sm.pfprod.model.dto.system.message.PfMessageTemplateDto;
import com.sm.pfprod.model.result.PageResult;

import java.util.List;

/**
 * @ClassName: PfMessageService
 * @Description: 消息接口
 * @Author yangtongbin
 * @Date 2018/9/16 20:19
 */
public interface PfMessageService {

    /**
     * 消息模板列表
     *
     * @param dto
     * @return
     */
    PageResult listMessages(PfMessageDto dto);

    /**
     * 新增消息模板
     *
     * @param dto
     * @return
     */
    boolean addMessageTemplate(PfMessageTemplateDto dto);

    /**
     * 编辑消息模板
     *
     * @param dto
     * @return
     */
    boolean editMessageTemplate(PfMessageTemplateDto dto);

    /**
     * 修改消息模板状态
     *
     * @param dto
     * @return
     */
    boolean updateStatus(PfBachChangeStatusDto dto);
}
