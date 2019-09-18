package com.sm.pfprod.model.vo.biz;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: PfTreeSelectVo
 * @Description: TreeSelect
 * @Author yangtongbin
 * @Date 2019-05-09
 */
@Setter
@Getter
@ToString
public class PfTreeSelectVo implements Serializable {

    private static final long serialVersionUID = -4044844488756130510L;

    private String id;

    private String name;

    private boolean open;

    private List<PfTreeSelectVo> children;

}
