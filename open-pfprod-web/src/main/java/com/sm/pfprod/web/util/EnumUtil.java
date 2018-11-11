package com.sm.pfprod.web.util;

import com.sm.pfprod.model.entity.SysDictionary;
import com.sm.pfprod.model.vo.dic.PfDicCache;
import com.sm.pfprod.model.vo.dic.PfEnum;
import com.sm.pfprod.service.system.dic.PfDicService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
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

    public static Map<String, PfEnum> allEnums = new HashMap<>();

    /**
     * 获取指定枚举类型 指定枚举值的显示值
     *
     * @param groupCode 枚举类型
     * @param dictCode  枚举值
     * @return
     */
    public String getEnumTxt(String groupCode, String dictCode) {
        if (allEnums.isEmpty()) {
            init();
        }
        List<PfDicCache> dicCaches = allEnums.get(groupCode).getDicCacheList();
        String txt = null;
        for (PfDicCache pfDicCache : dicCaches) {
            if (pfDicCache.getDictCode().equals(dictCode)) {
                txt = pfDicCache.getDictName();
                break;
            }
        }
        return StringUtils.isBlank(txt) ? dictCode : txt;
    }

    /**
     * 获得指定类型的枚举
     *
     * @param groupCode 枚举类型
     * @return
     */
    public List<PfDicCache> getEnumList(String groupCode) {
        if (allEnums.isEmpty()) {
            init();
        }
        PfEnum item = allEnums.get(groupCode);
        if (item == null) {
            return null;
        }
        return item.getDicCacheList();
    }

    /**
     * 构造方法 从数据库读取枚举值
     */
    public void init() {
        allEnums.clear();
        List<SysDictionary> dictionaries = pfDicService.listAllEnums();
        for (SysDictionary dictionary : dictionaries) {
            String key = dictionary.getGroupCode();
            String name = dictionary.getGroupName();
            if (allEnums.containsKey(key)) {
                PfDicCache pfDicCache = new PfDicCache();
                pfDicCache.setDictName(dictionary.getDictName());
                pfDicCache.setDictCode(dictionary.getDictCode());
                pfDicCache.setExtValue(dictionary.getExtValue());
                pfDicCache.setSortNum(dictionary.getSortNum());
                pfDicCache.setRemark(dictionary.getRemark());
                allEnums.get(key).getDicCacheList().add(pfDicCache);
            } else {
                PfEnum medEnum = new PfEnum();
                medEnum.setGroupCode(key);
                medEnum.setGroupName(name);
                List<PfDicCache> dicCacheList = new ArrayList<>(dictionaries.size());
                PfDicCache pfDicCache = new PfDicCache();
                pfDicCache.setDictName(dictionary.getDictName());
                pfDicCache.setDictCode(dictionary.getDictCode());
                pfDicCache.setExtValue(dictionary.getExtValue());
                pfDicCache.setSortNum(dictionary.getSortNum());
                pfDicCache.setRemark(dictionary.getRemark());
                dicCacheList.add(pfDicCache);
                medEnum.setDicCacheList(dicCacheList);
                allEnums.put(key, medEnum);
            }
        }
    }

}
