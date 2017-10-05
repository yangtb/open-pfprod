package com.sm.pfprod.service.user.login.impl;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.log.LoggerUtil;
import com.sm.open.care.core.utils.Assert;
import com.sm.open.care.core.utils.rsa.RSAEncrypt;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class PfLoginHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfLoginHelper.class);

    /**
     * 获取明文密码
     *
     * @return
     */
    public static String getPlaintext(String privateKey, String password) {
        try {
            /* 参数校验 */
            Assert.isTrue(StringUtils.isNotEmpty(privateKey), ErrorCode.ERROR_PARAM_170002, MessageFormat.format(ErrorMessage.MESSAGE_PARAM_170002, "privateKey"));
            Assert.isTrue(StringUtils.isNotEmpty(password), ErrorCode.ERROR_PARAM_170002, MessageFormat.format(ErrorMessage.MESSAGE_PARAM_170002, "password"));
            /* 获取明文密码 */
            //byte[] res = RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(privateKey), Base64.decodeBase64(password));
            //return new String(res);
            return null;
        } catch (BizRuntimeException e) {
            throw new BizRuntimeException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            LoggerUtil.error(LOGGER, "获取明文密码失败：", e.getMessage());
            return null;
        }
    }

    /**
     * 生产盐值 ： 用户名 + 随机数
     *
     * @param userName
     * @return
     */
    public static String getSalt(String userName) {
        return userName + new SecureRandomNumberGenerator().nextBytes().toHex();
    }

    /**
     * @param algorithmName  算法
     * @param plaintext      明码文本
     * @param hashIterations 加密次数
     * @param salt           盐值
     * @return
     */
    public static String getHashPsw(String algorithmName, String plaintext, int hashIterations, String salt) {
        return new SimpleHash(algorithmName, plaintext, salt, hashIterations).toHex();
    }
}
