package com.sm.pfprod.model.dto.biz.media;

import com.sm.pfprod.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: PfMediaDto
 * @Description: 咨媒
 * @Author yangtongbin
 * @Date 2018/8/28 10:13
 */
@Setter
@Getter
@ToString
public class PfMediaDto extends PageParam implements Serializable {

    /**
     * 标题
     */
    private String title;
    /**
     * 状态
     */
    private String status;
    /**
     * banner位
     */
    private String noticeType;

}
