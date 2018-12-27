package com.sm.pfprod.service.system.grade.Impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.param.pf.common.PfCommonListParam;
import com.sm.open.core.facade.model.param.pf.system.grade.PfGradeParam;
import com.sm.open.core.facade.model.param.pf.system.grade.SysClassParam;
import com.sm.open.core.facade.model.param.pf.user.PfUserParam;
import com.sm.open.core.facade.model.result.pf.system.grade.IdClassMemoResult;
import com.sm.open.core.facade.model.result.pf.system.grade.SysClassResult;
import com.sm.open.core.facade.model.result.pf.user.login.PfUsersResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.system.grade.GradeClient;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCommonListDto;
import com.sm.pfprod.model.dto.system.grade.PfGradeDto;
import com.sm.pfprod.model.dto.user.PfUserDto;
import com.sm.pfprod.model.entity.SysClass;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.system.grade.PfGradeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PfGradeServiceImpl implements PfGradeService {

    @Resource
    private GradeClient gradeClient;

    @Override
    public PageResult listGrades(PfGradeDto dto) {
        PfPageResult<SysClassResult> result = gradeClient.listGrades(BeanUtil.convert(dto, PfGradeParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public List<SysClass> listAllGrades(PfGradeDto dto) {
        CommonResult<List<SysClassResult>> result = gradeClient.listAllGrades(BeanUtil.convert(dto, PfGradeParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), SysClass.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean saveGrade(SysClass dto) {
        CommonResult<Boolean> result = gradeClient.saveGrade(BeanUtil.convert(dto, SysClassParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delGrade(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = gradeClient.delGrade(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listStudents(PfGradeDto dto) {
        PfPageResult<IdClassMemoResult> result = gradeClient.listStudents(BeanUtil.convert(dto, PfGradeParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean saveStudent(PfCommonListDto dto) {
        CommonResult<Boolean> result = gradeClient.saveStudent(BeanUtil.convert(dto, PfCommonListParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delStudent(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = gradeClient.delStudent(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listUsStudents(PfUserDto dto) {
        PfPageResult<PfUsersResult> result = gradeClient.listUsStudents(BeanUtil.convert(dto, PfUserParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }
}
