package com.sm.pfprod.service.system.set;

import com.sm.pfprod.model.dto.system.set.PfEmailSendDto;
import com.sm.pfprod.model.dto.system.set.PfEmailSetDto;
import com.sm.pfprod.model.entity.PfEmailSet;
import com.sm.pfprod.model.entity.PfWebsiteSet;

/**
 * @ClassName: PfSetService
 * @Description: 站点设置接口
 * @Author yangtongbin
 * @Date 2018/9/16 16:19
 */
public interface PfSetService {

    /**
     * 网站设置
     *
     * @param dto
     * @return
     */
    boolean websiteSet(PfWebsiteSet dto);

    /**
     * 获取网站设置信息
     *
     * @return
     */
    PfWebsiteSet selectWebsiteSet();

    /**
     * 邮件发送设置
     *
     * @param dto
     * @return
     */
    boolean emailSet(PfEmailSetDto dto);

    /**
     * 获取邮件发送设置
     *
     * @return
     */
    PfEmailSet selectEmailSet();

    /**
     * 邮件发送
     *
     * @param dto
     * @return
     */
    boolean sendEmail(PfEmailSendDto dto);
}
