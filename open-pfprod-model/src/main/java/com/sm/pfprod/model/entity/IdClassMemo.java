package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 班级-学生
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class IdClassMemo implements Serializable {

    private static final long serialVersionUID = 1541221610346L;


    /**
     * 主键
     * 班级成员id
     */
    private Long idMemo;

    /**
     * 班级id
     */
    private Long idClass;

    /**
     * 用户id
     */
    private Long idUser;

    /**
     * 1 老师 2 学生
     */
    private String sdUser;

}
