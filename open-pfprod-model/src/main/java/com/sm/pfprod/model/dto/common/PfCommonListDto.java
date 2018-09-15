package com.sm.pfprod.model.dto.common;

import java.io.Serializable;
import java.util.List;

public class PfCommonListDto implements Serializable {

    private static final long serialVersionUID = 6005656188483189531L;

    private List<Long> list;

    public List<Long> getList() {
        return list;
    }

    public void setList(List<Long> list) {
        this.list = list;
    }
}
