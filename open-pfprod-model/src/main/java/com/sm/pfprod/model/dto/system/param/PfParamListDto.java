package com.sm.pfprod.model.dto.system.param;

import com.sm.pfprod.model.dto.common.PfCommonListDto;

import java.io.Serializable;

public class PfParamListDto extends PfCommonListDto implements Serializable {

    private static final long serialVersionUID = -6476365369471501402L;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
