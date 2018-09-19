package com.sm.pfprod.model.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: PageParam
 * @Description: 分页入参
 * @Author yangtongbin
 * @Date 2017/9/8 12:36
 */
@Setter
@Getter
@ToString
public class PageParam implements Serializable {
    private static final long serialVersionUID = 3938095740389970783L;

    /**
     * 页码
     */
    private Integer page;
    /**
     * 数据偏移量，从当前这个数目开始查询
     */
    private Long offset = 0L;
    /**
     * 每页数据量
     */
    private Integer limit;

    public static void initPageDto(PageParam pageDto) {
        if (pageDto != null) {
            if (pageDto.page <= 0 || pageDto.limit < 0) {
                pageDto.setLimit(15);
                pageDto.setPage(0);
            }
        }
        pageDto.setOffset(Long.valueOf((pageDto.page - 1) * pageDto.limit));
    }
}
