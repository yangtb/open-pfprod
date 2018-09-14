package com.sm.pfprod.web.security.rsa;

import com.sm.open.care.core.utils.rsa.RsaKeyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName: RsaKeyPairCache
 * @Description: Rsa
 * @Author yangtongbin
 * @Date 2018/9/7 22:05
 */
public class RsaKeyPairCache {
    private final static Logger logger = LoggerFactory.getLogger(RsaKeyPairCache.class);

    private static final String RSA_SESSION_KEY = "RSA_SESSION_KEY";

    /**
     * 将取出的rsa公私钥对存入session中
     *
     * @param request
     * @param keyPair
     * @return 存入成功的条数，-1表示存入失败，0表示已存在将不存入，1表示存入一条数据成功
     */
    public static int putRsaKeyPair(HttpServletRequest request, RsaKeyPair keyPair) {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute(RSA_SESSION_KEY) != null) {
                return 0;
            }
            session.setAttribute(RSA_SESSION_KEY, keyPair);
            return 1;
        } catch (Exception e) {
            logger.error("rsa公私钥对插入session缓存异常", e);
            return -1;
        }
    }

    /**
     * 从session中获取公私钥对
     *
     * @param request
     * @return
     */
    public static RsaKeyPair getRsaKeyPair(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (RsaKeyPair) session.getAttribute(RSA_SESSION_KEY);
    }

}
