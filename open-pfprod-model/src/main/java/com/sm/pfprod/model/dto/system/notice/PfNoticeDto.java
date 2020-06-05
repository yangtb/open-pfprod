package com.sm.pfprod.model.dto.system.notice;

import com.sm.pfprod.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfNoticeDto extends PageParam implements Serializable {

    private static final long serialVersionUID = -8065165575922718762L;

    private String publishTimeBegin;
    private String publishTimeEnd;

}
