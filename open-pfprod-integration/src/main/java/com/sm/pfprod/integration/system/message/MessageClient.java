package com.sm.pfprod.integration.system.message;

import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.param.pf.system.message.PfMessageParam;
import com.sm.open.core.facade.model.param.pf.system.message.PfMessageTemplateParam;
import com.sm.open.core.facade.model.result.pf.system.message.MessageTemplateResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.system.message.PfMessageFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MessageClient {

    @Resource
    private PfMessageFacade pfMessageFacade;

    public PfPageResult<MessageTemplateResult> listMessages(PfMessageParam param) {
        return pfMessageFacade.listMessages(param);
    }

    public CommonResult<Boolean> addMessageTemplate(PfMessageTemplateParam param) {
        return pfMessageFacade.addMessageTemplate(param);
    }


    public CommonResult<Boolean> editMessageTemplate(PfMessageTemplateParam param) {
        return pfMessageFacade.editMessageTemplate(param);
    }


    public CommonResult<Boolean> updateStatus(PfBachChangeStatusParam param) {
        return pfMessageFacade.updateStatus(param);
    }

}