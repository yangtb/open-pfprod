package com.sm.pfprod.web.portal.biz.media;

import com.sm.pfprod.model.dto.biz.media.PfMediaDto;
import com.sm.pfprod.model.enums.SysDicGroupEnum;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.media.PfMediaService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.util.EnumUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PfBannerController
 * @Description: bnner管理
 * @Author yangtongbin
 * @Date 2018/8/22 16:59
 */
@Controller
@RequestMapping(value = "/pf/p/banner")
public class PfBannerController extends BaseController {

    @Resource
    private PfMediaService pfMediaService;
    @Resource
    private EnumUtil enumUtil;

    @PreAuthorize("hasAnyRole('ROLE_BANNER_MG','ROLE_SUPER')")
    @RequestMapping("/page")
    public String page(Model model) {
        model.addAttribute("bannerPosition", enumUtil.getEnumList(SysDicGroupEnum.BANNER_POSITION.getCode()));
        return "pages/biz/media/banner/banner";
    }

    @PreAuthorize("hasAnyRole('ROLE_BANNER_MG','ROLE_SUPER')")
    @RequestMapping("/form")
    public String form(String formType, Model model) {
        model.addAttribute("areaCode", enumUtil.getEnumList(SysDicGroupEnum.AREA_CODE.getCode()));
        model.addAttribute("formType", formType);
        return "pages/biz/media/banner/bannerForm";
    }

    /**
     * 获取banner列表
     */
    @PreAuthorize("hasAnyRole('ROLE_BANNER_MG','ROLE_SUPER')")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult listDicGroups(PfMediaDto dto) {
        return pfMediaService.listBanners(dto);
    }

}
