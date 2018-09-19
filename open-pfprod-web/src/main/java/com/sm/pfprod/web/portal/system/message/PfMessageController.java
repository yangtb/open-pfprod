package com.sm.pfprod.web.portal.system.message;

import com.sm.pfprod.model.dto.system.message.PfMessageDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.system.message.PfMessageService;
import com.sm.pfprod.web.portal.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfMessageController
 * @Description: 消息模板
 * @Author yangtongbin
 * @Date 2018/9/16 20:02
 */
@Controller
@RequestMapping(value = "/pf/p/message")
public class PfMessageController extends BaseController {

    @Resource
    private PfMessageService pfMessageService;

    @PreAuthorize("hasAnyRole('ROLE_SUPER')")
    @RequestMapping("/page")
    public String message() {
        return "pages/system/message/message";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER')")
    @RequestMapping("/form")
    public String messageForm(String formType, String templateType, Model model) {
        model.addAttribute("formType", formType);
        model.addAttribute("templateType", templateType);
        return "pages/system/message/messageForm";
    }

    /**
     * 获取所有消息模板列表
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_SUPER')")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listMessages(PfMessageDto dto)
    {
        return pfMessageService.listMessages(dto);
    }
}
