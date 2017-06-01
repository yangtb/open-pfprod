package com.sm.pfprod.model.vo.menu;

import java.io.Serializable;
import java.util.List;

public class PfMenuVo extends PfBaseMenuVo implements Serializable {

    private static final long serialVersionUID = -8884040581281729809L;

    private List<PfBaseMenuVo>   child;	// 子菜单

    public List<PfBaseMenuVo> getChild() {
        return child;
    }

    public void setChild(List<PfBaseMenuVo> child) {
        this.child = child;
    }
}
