package com.sm.pfprod.model.dto.system.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfMessageTemplateDto implements Serializable {

    private static final long serialVersionUID = -1383701634655812521L;

    /**
     * 模板ID
     */
    private String templateId;
    /**
     *模板名称
     */
    private String templateName;
    /**
     *模板类型sms=短信, email=邮件
     */
    private String templateType;
    /**
     *模板内容
     */
    private String content;
    /**
     *删除标示，N未删除 Y-已删除
     */
    private String isDeleted;
    /**
     *最后修改管理员ID
     */
    private String operator;
}
