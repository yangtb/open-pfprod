package com.sm.pfprod.web.rest.system.message;

import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.system.message.PfMessageTemplateDto;
import com.sm.pfprod.service.system.message.PfMessageService;
import com.sm.pfprod.web.security.CurrentUserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfMessageRestController
 * @Description: 消息模块
 * @Author yangtongbin
 * @Date 2018/9/17 15:34
 */
@Controller
@RequestMapping(value = "/pf/r/message")
public class PfMessageRestController {

    @Resource
    private PfMessageService pfMessageService;

    /**
     * 新增消息模板
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_SUPER')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addMessageTemplate(@RequestBody PfMessageTemplateDto dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getTemplateName()), "templateName");
        Assert.isTrue(StringUtils.isNotBlank(dto.getTemplateType()), "templateType");
        Assert.isTrue(StringUtils.isNotBlank(dto.getContent()), "content");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("addMessageTemplate", ResultObject.DATA_TYPE_OBJECT,
                pfMessageService.addMessageTemplate(dto));
    }

    /**
     * 编辑消息模板
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_SUPER')")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editMessageTemplate(@RequestBody PfMessageTemplateDto dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getTemplateName()), "templateName");
        Assert.isTrue(StringUtils.isNotBlank(dto.getTemplateType()), "templateType");
        Assert.isTrue(StringUtils.isNotBlank(dto.getContent()), "content");
        dto.setOperator(CurrentUserUtils.getCurrentUsername());
        return ResultObject.createSuccess("editMessageTemplate", ResultObject.DATA_TYPE_OBJECT,
                pfMessageService.editMessageTemplate(dto));
    }

    /**
     * 修改消息模板状态
     *
     * @param dto 消息模板集合
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_SUPER')")
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updateStatus(@RequestBody PfBachChangeStatusDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "入参不能为空");
        Assert.isTrue(StringUtils.isNotBlank(dto.getStatus()), "isDeleted不能为空");
        return ResultObject.createSuccess("updateStatus", ResultObject.DATA_TYPE_OBJECT,
                pfMessageService.updateStatus(dto));
    }
}
