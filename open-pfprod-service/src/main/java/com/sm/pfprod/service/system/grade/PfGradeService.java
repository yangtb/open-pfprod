package com.sm.pfprod.service.system.grade;

import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCommonListDto;
import com.sm.pfprod.model.dto.system.grade.PfGradeDto;
import com.sm.pfprod.model.dto.user.PfUserDto;
import com.sm.pfprod.model.entity.SysClass;
import com.sm.pfprod.model.entity.SysOrg;
import com.sm.pfprod.model.result.PageResult;

/**
 * @ClassName: PfGradeService
 * @Description: 班级service
 * @Author yangtongbin
 * @Date 2018/11/3
 */
public interface PfGradeService {

    /**
     * 班级列表
     *
     * @param dto
     * @return
     */
    PageResult listGrades(PfGradeDto dto);

    /**
     * 新增班级
     *
     * @param dto
     * @return
     */
    boolean saveGrade(SysClass dto);

    /**
     * 删除班级
     *
     * @param dto
     * @return
     */
    boolean delGrade(PfBachChangeStatusDto dto);

    /**
     * 学生列表
     *
     * @param dto
     * @return
     */
    PageResult listStudents(PfGradeDto dto);

    /**
     * 添加学生
     *
     * @param dto
     * @return
     */
    boolean saveStudent(PfCommonListDto dto);

    /**
     * 删除学生
     *
     * @param dto
     * @return
     */
    boolean delStudent(PfBachChangeStatusDto dto);

    /**
     * 获取学生列表
     *
     * @return
     */
    PageResult listUsStudents(PfUserDto dto);

}
