package com.sm.pfprod.model.vo.biz.test.paper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: PfTestPaperStudentVo
 * @Description: 学生信息
 * @Author yangtongbin
 * @Date 2018/11/9
 */
@Setter
@Getter
@ToString
public class PfTestPaperStudentVo implements Serializable {

    private static final long serialVersionUID = -8593324346365792342L;

    /**
     * 用户id
     */
    private Long studentId;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 所在机构
     */
    private String orgName;

    /**
     * 班级
     */
    private String className;

    /**
     * 电邮
     */
    private String email;

    /**
     * 联系电话
     */
    private String phoneNo;


}
