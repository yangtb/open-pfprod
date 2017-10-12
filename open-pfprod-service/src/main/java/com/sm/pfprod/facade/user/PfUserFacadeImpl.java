package com.sm.pfprod.facade.user;

import com.sm.open.care.core.annotation.Loggable;
import com.sm.open.care.core.enums.Level;
import com.sm.open.care.core.enums.YesOrNo;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.pfprod.model.dto.user.PfUserDto;
import com.sm.pfprod.model.dto.user.login.RegisterDto;
import com.sm.pfprod.model.dto.user.login.UpdatePswDto;
import com.sm.pfprod.model.entity.UserInfo;
import com.sm.pfprod.model.param.PageParam;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.result.ResultFactory;
import com.sm.pfprod.service.system.email.PfEmailService;
import com.sm.pfprod.service.user.login.PfUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("pfUserFacade")
public class PfUserFacadeImpl implements PfUserFacade {

    @Resource
    private PfUserService pfUserService;
    @Resource
    private PfEmailService pfEmailService;

    /** 发送邮件开关 */
    @Value(value = "${reset.password.email.switch}")
    private final String resetPswSwitch = "N";

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

    /**
     * 修改密码
     *
     * @param dto
     * @return
     */
    @Override
    public boolean updatePsw(UpdatePswDto dto) {
        // 查询用户密码、盐值等
        UserInfo userInfo = pfUserService.selectUser(dto.getUserName());
        if (!pfUserService.matchPassword(dto.getOldPassword(), userInfo.getSalt(), userInfo.getPassword())) {
            throw new BizRuntimeException(PfUserConstant.OLD_PASSWORD_ERROR, PfUserConstant.OLD_PASSWORD_ERROR_MSG);
        }
        return pfUserService.updatePsw(dto);
    }

    @Override
    public boolean resetPsw(RegisterDto dto) {
        UpdatePswDto updatePswDto = new UpdatePswDto();
        updatePswDto.setUserId(dto.getUserId());
        updatePswDto.setNewPassword(dto.getPassword());
        if (resetPswSwitch.equals(YesOrNo.YES.getCode())) {
            pfEmailService.sendEmail();
        }
        return pfUserService.updatePsw(updatePswDto);
    }
}
