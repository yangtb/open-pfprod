package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
public class Notice implements Serializable {

    private static final long serialVersionUID = 7435486910753250713L;

    private Long id;// 公告id
    private String noticeCode; // 公告编号
    private String title; //标题
    private String shortName; //公告简称
    private String content; //内容
    private String pictureUrl; //图片
    private String status; //状态：冻结(freeze)、有效(enabled)、撤销(revoke)、删除(delete)、通过(pass)、失败(fail)、(publish)发布
    private Integer sortNum; // 推荐排序，默认999，数值从小到大排序
    private Date startTime; //开始时间
    private Date endTime; //结束时间
    private String reach; //影响范围：所有(all)、医院(hos)、用户(user)、医生(doc)
    private String noticeType; //公告类型：横幅(banner)、活动(active)、公告(notice)、通知消息(message)、新闻(news), 列表页总横幅(banner_pro)
    private String remark; //备注
    private String linkUrl; //
    private String isLimitArea; //是否限制(Y-是，N-否，默认N)
    private String isLimitGroup; //是否区分人群标签(Y-是，N-否，默认N)
    private String appCode; //应用编码，数据回流时使用
    private String isvAppid; // ISV的APPID
    private String isvScope; //ISV的权限域，多个权限域使用英文逗号分隔
    private String isAuth; //Y-需要授权 N-不需要,默认 Y
    private String operator; //操作人员
    private Date gmtCreate; //数据创建时间
    private Date gmtModify; //数据修改时间
}
