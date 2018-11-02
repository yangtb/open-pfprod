package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 临床模拟_测试计划_关联学生
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmTestplanStudent implements Serializable {

    private static final long serialVersionUID = 1541156593355L;

    /**
     * 主键
     * 关联学生ID
     */
    private Long idTestplanStudent;

    /**
     * 学生ID
     */
    private Long idStudent;

    /**
     * 测试计划ID
     */
    private Long idTestplan;

}
