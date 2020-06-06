package com.sm.pfprod.web.rest.system.set;

import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.model.dto.system.set.PfEmailSendDto;
import com.sm.pfprod.model.dto.system.set.PfEmailSetDto;
import com.sm.pfprod.model.entity.PfWebsiteSet;
import com.sm.pfprod.service.system.set.PfSetService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfSetRestController
 * @Description: 站点设置
 * @Author yangtongbin
 * @Date 2018/9/16 15:41
 */
@Controller
@RequestMapping(value = "/pf/r/set")
public class PfSetRestController {

    @Resource
    private PfSetService pfSetService;

    /**
     * 网站设置
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_SUPER')")
    @RequestMapping(value = "/website", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject websiteSet(@RequestBody PfWebsiteSet dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), "name");
        return ResultObject.createSuccess("websiteSet",
                ResultObject.DATA_TYPE_OBJECT, pfSetService.websiteSet(dto));
    }


    /**
     * 邮件发送设置
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_SUPER')")
    @RequestMapping(value = "/email", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject emailSet(@RequestBody PfEmailSetDto dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getHost()), "host");
        Assert.isTrue(StringUtils.isNotBlank(dto.getNickname()), "nickname");
        Assert.isTrue(StringUtils.isNotBlank(dto.getPassword()), "password");
        Assert.isTrue(StringUtils.isNotBlank(dto.getSender()), "sender");
        Assert.isTrue(StringUtils.isNotBlank(dto.getUserName()), "userName");

        return ResultObject.createSuccess("emailSet",
                ResultObject.DATA_TYPE_OBJECT, pfSetService.emailSet(dto));
    }


    /**
     * 邮件发送
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_SUPER')")
    @RequestMapping(value = "/email/send", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject sendEmail(@RequestBody PfEmailSendDto dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getRecipients()), "recipients");
        Assert.isTrue(StringUtils.isNotBlank(dto.getTitle()), "title");
        Assert.isTrue(StringUtils.isNotBlank(dto.getContent()), "content");

        return ResultObject.createSuccess("sendEmail",
                ResultObject.DATA_TYPE_OBJECT, pfSetService.sendEmail(dto));
    }



}
