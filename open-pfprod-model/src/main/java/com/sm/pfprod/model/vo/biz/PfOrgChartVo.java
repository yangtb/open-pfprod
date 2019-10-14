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
public class PfOrgChartVo implements Serializable {

    private static final long serialVersionUID = -1606426697549719856L;

    private String id;

    private String name;

    private Integer type;

    private List<PfOrgChartVo> children;

}
