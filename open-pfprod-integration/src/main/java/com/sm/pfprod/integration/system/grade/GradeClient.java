package com.sm.pfprod.integration.system.grade;

import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.param.pf.common.PfCommonListParam;
import com.sm.open.core.facade.model.param.pf.system.grade.PfGradeParam;
import com.sm.open.core.facade.model.param.pf.system.grade.SysClassParam;
import com.sm.open.core.facade.model.param.pf.user.PfUserParam;
import com.sm.open.core.facade.model.result.pf.system.grade.SysClassResult;
import com.sm.open.core.facade.model.result.pf.user.login.PfUsersResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.system.grade.PfGradeFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class GradeClient {

    @Resource
    private PfGradeFacade pfGradeFacade;

    public PfPageResult listGrades(PfGradeParam param) {
        return pfGradeFacade.listGrades(param);
    }

    public CommonResult<List<SysClassResult>> listAllGrades(PfGradeParam param) {
        return pfGradeFacade.listAllGrades(param);
    }

    public CommonResult<Boolean> saveGrade(SysClassParam param) {
        return pfGradeFacade.saveGrade(param);
    }

    public CommonResult<Boolean> delGrade(PfBachChangeStatusParam param) {
        return pfGradeFacade.delGrade(param);
    }

    public PfPageResult listStudents(PfGradeParam param) {
        return pfGradeFacade.listStudents(param);
    }

    public CommonResult<Boolean> saveStudent(PfCommonListParam param) {
        return pfGradeFacade.saveStudent(param);
    }

    public CommonResult<Boolean> delStudent(PfBachChangeStatusParam param) {
        return pfGradeFacade.delStudent(param);
    }

    public PfPageResult<PfUsersResult> listUsStudents(PfUserParam param) {
        return pfGradeFacade.listUsStudents(param);
    }

}
