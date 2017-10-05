package com.sm.pfprod.model.vo.menu;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: PfMenuVo
 * @Description: 菜单分组
 * @Author yangtongbin
 * @Date 2017/10/2 19:34
 */
public class PfMenuVo extends PfBaseMenuVo implements Serializable {

    private static final long serialVersionUID = -8884040581281729809L;

    private Long                        parentMenuId;
    private String                      parentUrl;
    private String                      parentMenuName;
    private String                      parentImg;
    private List<PfBaseMenuVo>          groupList;      //  菜单list

    public Long getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Long parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getParentUrl() {
        return parentUrl;
    }

    public void setParentUrl(String parentUrl) {
        this.parentUrl = parentUrl;
    }

    public String getParentMenuName() {
        return parentMenuName;
    }

    public void setParentMenuName(String parentMenuName) {
        this.parentMenuName = parentMenuName;
    }

    public String getParentImg() {
        return parentImg;
    }

    public void setParentImg(String parentImg) {
        this.parentImg = parentImg;
    }

    public List<PfBaseMenuVo> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<PfBaseMenuVo> groupList) {
        this.groupList = groupList;
    }
}
