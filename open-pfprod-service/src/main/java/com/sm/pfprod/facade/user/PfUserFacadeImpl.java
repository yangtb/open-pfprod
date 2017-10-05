package com.sm.pfprod.facade.user;

import com.sm.open.care.core.annotation.Loggable;
import com.sm.open.care.core.enums.Level;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.pfprod.facade.role.PfRoleConstant;
import com.sm.pfprod.model.dto.user.PfUserDto;
import com.sm.pfprod.model.dto.user.login.RegisterDto;
import com.sm.pfprod.model.param.PageParam;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.result.ResultFactory;
import com.sm.pfprod.service.user.login.PfUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("pfUserFacade")
public class PfUserFacadeImpl implements PfUserFacade {

    @Resource
    private PfUserService pfUserService;

    @Loggable(bizDec = "获取用户列表", level = Level.info)
    @Override
    public PageResult listUsers(PfUserDto dto) {
        try {
            PageParam.initPageDto(dto);
            return ResultFactory.initPageResultWithSuccess(pfUserService.countUsers(dto), pfUserService.listUsers(dto));
        } catch (Exception e) {
            return ResultFactory.initPageResultWithError(PfUserConstant.SELECT_PAGE_USER_LIST_ERROR,
                    PfUserConstant.SELECT_PAGE_USER_LIST_ERROR_MSG);
        }
    }

    @Loggable(bizDec = "新增用户", level = Level.info)
    @Override
    public boolean saveUser(RegisterDto dto) {
        if (pfUserService.isExistUser(dto.getUsername())) {
            throw new BizRuntimeException(PfUserConstant.ADD_USER_ISEXIST, PfUserConstant.ADD_USER_ISEXIST_MSG);
        }
        return pfUserService.saveUser(dto);
    }

    @Loggable(bizDec = "编辑用户", level = Level.info)
    @Override
    public boolean updateUser(RegisterDto dto) {
        return pfUserService.updateUser(dto);
    }

    @Loggable(bizDec = "批量删除用户", level = Level.info)
    @Override
    public boolean delUser(List<Long> users) {
        return pfUserService.delUser(users);
    }

    @Loggable(bizDec = "批量冻结用户", level = Level.info)
    @Override
    public boolean freezeUser(List<Long> users) {
        return pfUserService.freezeUser(users);
    }
}
