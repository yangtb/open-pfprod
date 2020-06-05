package com.sm.pfprod.model.dto.system.grade;

import com.sm.pfprod.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfGradeDto extends PageParam implements Serializable {

    private static final long serialVersionUID = -8065165575922718762L;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 班级id
     */
    private Long idClass;

    /**
     * 机构id
     */
    private Long idOrg;

}
