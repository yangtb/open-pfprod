package com.sm.pfprod.web.util;

import com.sm.pfprod.model.entity.SysParam;
import com.sm.pfprod.service.system.param.PfParamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ParamUtil
 * @Description: 字典工具类，做缓存，可放入redis
 * @Author yangtongbin
 * @Date 2018/10/9
 */
@Component
public class ParamUtil {

    @Resource
    private PfParamService pfParamService;

    public static Map<String, SysParam> allParams = new HashMap<>();

    /**
     * 获得指定参数信息
     *
     * @param paramCode 参数编码
     * @return
     */
    public SysParam getParamInfo(String paramCode) {
        if (allParams.isEmpty()) {
            init();
        }
        SysParam item = allParams.get(paramCode);
        if (item == null) {
            return null;
        }
        return item;
    }

    public String getParamValue(String paramCode) {
        SysParam sysParam = getParamInfo(paramCode);
        if (sysParam == null) {
            return null;
        }
        if (StringUtils.isNotBlank(sysParam.getParamValue())) {
            return sysParam.getParamValue();
        }
        return sysParam.getDefaultValue();
    }

    /**
     * 构造方法 从数据库读取枚举值
     */
    public void init() {
        allParams.clear();
        List<SysParam> allParam = pfParamService.listAllParam();
        if (CollectionUtils.isEmpty(allParam)) {
            return;
        }
        for (SysParam sysParam : allParam) {
            allParams.put(sysParam.getParamCode(), sysParam);
        }
    }
}
