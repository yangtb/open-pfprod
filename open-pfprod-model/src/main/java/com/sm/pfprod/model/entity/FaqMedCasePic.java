package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 知识库_图片
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class FaqMedCasePic implements Serializable {

    private static final long serialVersionUID = 1540020604667L;


    /**
     * 主键
     * 病历组件案例id
     */
    private Long idMedCase;

    /**
     * 多媒体id
     */
    private Long idMedia;

    /**
     * 文件路径
     */
    private String path;

}
