package com.sm.pfprod.service.system.message.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.param.pf.system.message.PfMessageParam;
import com.sm.open.core.facade.model.param.pf.system.message.PfMessageTemplateParam;
import com.sm.open.core.facade.model.result.pf.system.message.MessageTemplateResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.system.message.MessageClient;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.system.message.PfMessageDto;
import com.sm.pfprod.model.dto.system.message.PfMessageTemplateDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.system.message.PfMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PfMessageServiceImpl implements PfMessageService {

    @Resource
    private MessageClient messageClient;

    @Override
    public PageResult listMessages(PfMessageDto dto) {
        PfPageResult<MessageTemplateResult> result = messageClient.listMessages(BeanUtil.convert(dto, PfMessageParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addMessageTemplate(PfMessageTemplateDto dto) {
        CommonResult<Boolean> result = messageClient.addMessageTemplate(BeanUtil.convert(dto, PfMessageTemplateParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editMessageTemplate(PfMessageTemplateDto dto) {
        CommonResult<Boolean> result = messageClient.editMessageTemplate(BeanUtil.convert(dto, PfMessageTemplateParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean updateStatus(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = messageClient.updateStatus(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

}
