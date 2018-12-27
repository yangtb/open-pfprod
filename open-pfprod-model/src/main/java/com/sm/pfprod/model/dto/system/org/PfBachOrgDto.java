package com.sm.pfprod.model.dto.system.org;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: PfBachOrgDto
 * @Description: 批量操作
 * @Author yangtongbin
 * @Date 2018/9/27
 */
@Setter
@Getter
@ToString
public class PfBachOrgDto implements Serializable {

    private static final long serialVersionUID = 6005656188483189531L;

    private List<Long> idRegList;

    private List<Long> idOrgList;

    private String confirmor;

    private String operator;
}
