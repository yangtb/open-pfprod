package com.sm.pfprod.model.param;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @ClassName: PageParam
 * @Description: 分页入参
 * @Author yangtongbin
 * @Date 2017/9/8 12:36
 */
public class PageParam implements Serializable {
    private static final long serialVersionUID = 3938095740389970783L;

    private Integer page; //页码
    /**
     * 数据偏移量，从当前这个数目开始查询
     */
    private Long offset = 0L;
    private Integer limit; //每页数据量

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

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
