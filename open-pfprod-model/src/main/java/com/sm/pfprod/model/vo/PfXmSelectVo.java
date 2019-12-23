package com.sm.pfprod.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: PfTreeSelectVo
 * @Description: TreeSelect
 * @Author yangtongbin
 * @Date 2019-05-09
 */
@Setter
@Getter
@ToString
public class PfXmSelectVo implements Serializable {

    private static final long serialVersionUID = 1354508241331087188L;

    private String value;

    private String name;

    private Object children;

}
