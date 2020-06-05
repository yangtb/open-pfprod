package com.sm.pfprod.model.dto.system.set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: PfEmailSetDto
 * @Description: 邮件发送参数
 * @Author yangtongbin
 * @Date 2018/9/16 16:26
 */
@Setter
@Getter
@ToString
public class PfEmailSendDto implements Serializable {

    private static final long serialVersionUID = -2920361132176314444L;

    private String recipients;
    private String title;
    private String content;

}
