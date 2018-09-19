package com.sm.pfprod.service.system.set.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.care.core.utils.PropertiesUtil;
import com.sm.open.core.facade.model.param.pf.system.set.PfEmailSendParam;
import com.sm.open.core.facade.model.param.pf.system.set.PfEmailSetParam;
import com.sm.open.core.facade.model.result.pf.system.set.PfEmailSetResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.pfprod.integration.system.set.SetClient;
import com.sm.pfprod.model.dto.system.set.PfEmailSendDto;
import com.sm.pfprod.model.dto.system.set.PfEmailSetDto;
import com.sm.pfprod.model.entity.PfEmailSet;
import com.sm.pfprod.model.entity.PfWebsiteSet;
import com.sm.pfprod.model.enums.WebsiteSetEnum;
import com.sm.pfprod.service.system.set.PfSetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class PfSetServiceImpl implements PfSetService {

    /**
     * 网站配置相对路径
     */
    public static final String WEBSITE_RELATIVE_PATH = "config/website.properties";

    @Resource
    private SetClient setClient;

    @Override
    public boolean websiteSet(PfWebsiteSet dto) {
        // 集合设置初始大小，新增时需要修改
        Map<String, String> websiteMap = new HashMap<>(7);
        websiteMap.put(WebsiteSetEnum.NAME.getCode(), dto.getName());
        websiteMap.put(WebsiteSetEnum.LOG_SWITCH.getCode(), dto.getLogSwitch());
        websiteMap.put(WebsiteSetEnum.UPLOAD_TYPE.getCode(), dto.getUploadType());
        websiteMap.put(WebsiteSetEnum.COPYRIGHT.getCode(), dto.getCopyright());
        websiteMap.put(WebsiteSetEnum.APPROVE.getCode(), dto.getApprove());
        websiteMap.put(WebsiteSetEnum.MAX_UPLOAD_VALUE.getCode(), dto.getMaxUploadValue());
        websiteMap.put(WebsiteSetEnum.IP_BLACKLIST.getCode(), dto.getIpBlacklist());

        PropertiesUtil.setProperty(websiteMap,
                this.getClass().getClassLoader().getResource(WEBSITE_RELATIVE_PATH).getPath(), "网站设置");
        return true;
    }

    @Override
    public PfWebsiteSet selectWebsiteSet() {
        Properties properties = PropertiesUtil.readProperty(WEBSITE_RELATIVE_PATH);
        if (properties == null) {
            return null;
        }
        PfWebsiteSet pfWebsiteSet = new PfWebsiteSet();
        pfWebsiteSet.setName(properties.getProperty(WebsiteSetEnum.NAME.getCode()));
        pfWebsiteSet.setApprove(properties.getProperty(WebsiteSetEnum.APPROVE.getCode()));
        pfWebsiteSet.setCopyright(properties.getProperty(WebsiteSetEnum.COPYRIGHT.getCode()));
        pfWebsiteSet.setIpBlacklist(properties.getProperty(WebsiteSetEnum.IP_BLACKLIST.getCode()));
        pfWebsiteSet.setLogSwitch(properties.getProperty(WebsiteSetEnum.LOG_SWITCH.getCode()));
        pfWebsiteSet.setMaxUploadValue(properties.getProperty(WebsiteSetEnum.MAX_UPLOAD_VALUE.getCode()));
        pfWebsiteSet.setUploadType(properties.getProperty(WebsiteSetEnum.UPLOAD_TYPE.getCode()));
        return pfWebsiteSet;
    }

    @Override
    public boolean emailSet(PfEmailSetDto dto) {
        CommonResult<Boolean> result = setClient.emailSet(BeanUtil.convert(dto, PfEmailSetParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public PfEmailSet selectEmailSet() {
        CommonResult<PfEmailSetResult> result = setClient.selectEmailSet();
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), PfEmailSet.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean sendEmail(PfEmailSendDto dto) {
        CommonResult<Boolean> result = setClient.sendEmail(BeanUtil.convert(dto, PfEmailSendParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }
}
