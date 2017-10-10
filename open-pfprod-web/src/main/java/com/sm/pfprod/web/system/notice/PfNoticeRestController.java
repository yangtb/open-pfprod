package com.sm.pfprod.web.system.notice;

import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.pfprod.facade.notice.PfNoticeFacade;
import com.sm.pfprod.model.dto.system.notice.PfNoticeDto;
import com.sm.pfprod.model.dto.common.PfCommonListDto;
import com.sm.pfprod.model.entity.SysNotice;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.web.security.CurrentUserUtils;
import com.sm.pfprod.web.system.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfNoticeRestController
 * @Description: 公告模块
 * @Author yangtongbin
 * @Date 2017/10/3 21:43
 */
@Controller
@RequestMapping(value = "/notice")
public class PfNoticeRestController extends BaseController {

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

    /**
     * 新增公告
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject addNotice(@RequestBody SysNotice dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getNoticeTitle()), "noticeTitle");
        Assert.isTrue(StringUtils.isNotBlank(dto.getNoticeType()), "noticeType");
        Assert.isTrue(StringUtils.isNotBlank(dto.getNoticeContent()), "noticeContent");
        dto.setCreator(CurrentUserUtils.getCurrentUserId());
        return ResultObject.create("addNotice", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfNoticeFacade.addNotice(dto));
    }

    /**
     * 编辑公告
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject editNotice(@RequestBody SysNotice dto) {
        /* 参数校验 */
        Assert.isTrue(StringUtils.isNotBlank(dto.getNoticeTitle()), "noticeTitle");
        Assert.isTrue(StringUtils.isNotBlank(dto.getNoticeType()), "noticeType");
        Assert.isTrue(StringUtils.isNotBlank(dto.getNoticeContent()), "noticeContent");
        return ResultObject.create("editNotice", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfNoticeFacade.editNotice(dto));
    }

    /**
     * 删除公告
     *
     * @param dto 公告id集合
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject delNotice(@RequestBody PfCommonListDto dto) {
        /* 参数校验 */
        Assert.isTrue(CollectionUtils.isNotEmpty(dto.getList()), "入参不能为空");
        return ResultObject.create("delNotice", ResultObject.SUCCESS_CODE, ResultObject.MSG_SUCCESS,
                ResultObject.DATA_TYPE_OBJECT, pfNoticeFacade.delNotice(dto.getList()));
    }


}
