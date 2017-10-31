package com.sm.pfprod.web.portal.notice;

import com.sm.pfprod.facade.notice.PfNoticeFacade;
import com.sm.pfprod.model.dto.system.notice.PfNoticeDto;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.web.portal.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfNoticeRestController
 * @Description: 公告模块
 * @Author yangtongbin
 * @Date 2017/10/3 21:43
 */
@Controller
@RequestMapping(value = "/pf/p/notice")
public class PfNoticeController extends BaseController {

    @Resource
    private PfNoticeFacade pfNoticeFacade;

    @RequestMapping("/page")
    public String page() {
        return "pages/notice/notice";
    }

    @RequestMapping("/form")
    public String form(String formType, Model model) {
        model.addAttribute("formType", formType);
        return "pages/notice/noticeForm";
    }

    @RequestMapping("/detail")
    public String detail() {
        return "pages/notice/noticeDetail";
    }

    /**
     * 获取所有公告
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listNotices(PfNoticeDto dto) {
        return pfNoticeFacade.listNotices(dto);
    }

}
