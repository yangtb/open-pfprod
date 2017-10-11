package com.sm.pfprod.service.user.login.impl;

import com.sm.pfprod.dal.user.login.PfUserDao;
import com.sm.pfprod.model.dto.user.PfUserDto;
import com.sm.pfprod.model.dto.user.login.RegisterDto;
import com.sm.pfprod.model.dto.user.login.UpdatePswDto;
import com.sm.pfprod.model.entity.UserInfo;
import com.sm.pfprod.model.vo.user.PfUsersVo;
import com.sm.pfprod.service.user.login.PfUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;


@Service("pfUserService")
public class PfUserServiceImpl implements PfUserService {

    @Resource
    private PfUserDao pfUserDao;

    @Override
    public List<PfUsersVo> listUsers(PfUserDto dto) {
        return pfUserDao.listUsers(dto);
    }

    @Override
    public Long countUsers(PfUserDto dto) {
        return pfUserDao.countUsers();
    }

    @Transactional
    @Override
    public boolean saveUser(RegisterDto dto) {
        UserInfo user = new UserInfo();
        BeanUtils.copyProperties(dto, user);
        // 密码加密
        String salt = UUID.randomUUID().toString().replace("-", "");
        user.setSalt(salt);
        user.setPassword(genEncriptPwd(dto.getPassword(), salt));
        // 新增用户
        pfUserDao.saveUser(user);
        // 插入用户角色
        pfUserDao.saveUserRole(dto.getRoles(), user.getUserId());
        return true;
    }

    @Override
    public boolean isExistUser(String userName) {
        return pfUserDao.isExistUser(userName);
    }

    @Transactional
    @Override
    public boolean updateUser(RegisterDto dto) {
        UserInfo user = new UserInfo();
        BeanUtils.copyProperties(dto, user);
        // 修改用户信息
        pfUserDao.updateUser(user);
        // 删除用户角色
        pfUserDao.delUserRole(dto.getUserId());
        // 插入用户角色
        pfUserDao.saveUserRole(dto.getRoles(), dto.getUserId());
        return true;
    }

    @Override
    public boolean delUser(List<Long> users) {
        return pfUserDao.delUser(users) >= 1 ? true : false;
    }

    @Override
    public boolean freezeUser(List<Long> users) {
        return pfUserDao.freezeUser(users) >= 1 ? true : false;
    }

    @Override
    public UserInfo selectUser(String userName) {
        return pfUserDao.selectUser(userName);
    }

    @Override
    public boolean updatePsw(UpdatePswDto dto) {
        return false;
    }

    @Override
    public String genEncriptPwd(String rawPwd, String salt) {
        if(StringUtils.isBlank(salt)){
            salt = "";
        }
        PasswordEncoder passwordEncoder = new StandardPasswordEncoder(salt);
        String encodedPassword = passwordEncoder.encode(rawPwd);
        return encodedPassword;
    }


    public static void main(String[] args) {
        String salt = UUID.randomUUID().toString().replace("-", "");
        System.out.println(salt + ", " + genEncriptPwd1("ytb316", salt));
    }

    public static String genEncriptPwd1(String rawPwd, String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = "";
        }
        PasswordEncoder passwordEncoder = new StandardPasswordEncoder(salt);
        String encodedPassword = passwordEncoder.encode(rawPwd);
        return encodedPassword;
    }


}
