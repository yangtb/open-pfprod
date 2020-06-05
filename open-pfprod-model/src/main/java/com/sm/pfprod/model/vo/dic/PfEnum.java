package com.sm.pfprod.model.vo.dic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName: ParamEnum
 * @Description: 枚举封装
 * @Author yangtongbin
 * @Date 2017/10/10 13:09
 */
@Setter
@Getter
@ToString
public class PfEnum {

    /**
     * 枚举类型
     */
    private String groupCode;
    /**
     * 枚举名称
     */
    private String groupName;

    /**
     * 字典缓存 显示值 list
     */
    private List<PfDicCache> dicCacheList;

}
