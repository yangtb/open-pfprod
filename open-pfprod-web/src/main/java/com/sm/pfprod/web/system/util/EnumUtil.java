package com.sm.pfprod.web.system.util;

import com.sm.pfprod.model.entity.SysDictionary;
import com.sm.pfprod.model.enums.PfEnum;
import com.sm.pfprod.service.system.dic.PfDicService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: EnumUtil
 * @Description: 枚举工具类
 * @Author yangtongbin
 * @Date 2017/10/10 13:05
 */
@Component
public class EnumUtil {

    @Resource
    private PfDicService pfDicService;

    public static Map<String, PfEnum> allEnums = new HashMap<String, PfEnum>();

    /**
     * 获取指定枚举类型 指定枚举值的显示值
     *
     * @param groupCode 枚举类型
     * @param dictCode  枚举值
     * @return
     */
    public static String getEnumTxt(String groupCode, String dictCode) {
        String txt = allEnums.get(groupCode).getDicMap().get(dictCode);
        if (StringUtils.isBlank(txt)) {
            txt = dictCode;
        }
        return txt;
    }

    /**
     * 获得指定类型的枚举
     *
     * @param groupCode 枚举类型
     * @return
     */
    public static Map<String, String> getEnumMap(String groupCode) {
        PfEnum item = allEnums.get(groupCode);
        if (item == null) {
            return null;
        }
        return item.getDicMap();
    }


    /**
     * 构造方法 从数据库读取枚举值
     */
    @PostConstruct
    public void init() {
        List<SysDictionary> dictionaries = pfDicService.listAllEnums();
        for (SysDictionary dictionary : dictionaries) {
            String key = dictionary.getGroupCode();
            String name = dictionary.getGroupName();
            if (allEnums.containsKey(key)) {
                allEnums.get(key).getDicMap().put(dictionary.getDictCode(), dictionary.getDictName());
            } else {
                PfEnum medEnum = new PfEnum();
                medEnum.setGroupCode(key);
                medEnum.setGroupName(name);
                Map<String, String> txtMap = new LinkedHashMap<>();
                txtMap.put(dictionary.getDictCode(), dictionary.getDictName());
                medEnum.setDicMap(txtMap);
                allEnums.put(key, medEnum);
            }
        }
    }

}
