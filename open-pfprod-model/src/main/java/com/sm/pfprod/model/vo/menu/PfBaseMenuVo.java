package com.sm.pfprod.model.vo.menu;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PfBaseMenuVo {

    private Long            menuId;     // 菜单ID
    private String          url;        // 菜单url
    private String          name;       // 菜单名称
    private String          img;        // 图片
    private String          position;   // 菜单位置left=左边，top=顶部

}
