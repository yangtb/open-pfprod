package com.sm.pfprod.web.portal;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.rsa.RsaKeyPair;
import com.sm.pfprod.model.result.Result;
import com.sm.pfprod.web.security.rsa.RsaKeyPairQueue;
import com.sm.pfprod.web.util.ImageCodeUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName: IndexController
 * @Description: 首页控制器跳转
 */
@Controller
public class LoginController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    /**
     * 用户业务错误信息显示的常量变量名
     */
    private static final String ERROR_MSG = "errorMsg";
    /**
     * rsa公钥常量变量名
     */
    private static final String PUBLIC_KEY = "publicKey";

    @Resource(name = "rsaKeyPairQueue")
    private RsaKeyPairQueue rsaKeyPairQueue;

    @Resource
    private ImageCodeUtil imageCodeUtil;

    @RequestMapping(value = "/login")
    public String home(Model model, HttpServletRequest request) {
        this.setModelAttr(model, request);
        return "login";
    }

    @RequestMapping(value = "/login/{errorMsg}")
    public String homeError(@PathVariable String errorMsg, Model model, HttpServletRequest request) {
        if (StringUtils.isNotBlank(errorMsg)) {
            model.addAttribute(ERROR_MSG, new String(Base64.decodeBase64(errorMsg)));
        } else {
            model.addAttribute(ERROR_MSG, "");
        }
        this.setModelAttr(model, request);
        return "login";
    }

    private void setModelAttr(Model model, HttpServletRequest request) {
        RsaKeyPair keyPair;
        try {
            keyPair = rsaKeyPairQueue.takeQueue(request);
            model.addAttribute(PUBLIC_KEY, keyPair.getPublicKey());
        } catch (InterruptedException e) {
            logger.error("进入首页时，rsa公私钥队列相关操作异常", e);
        }
    }

    @RequestMapping(value = "login/verificationCode", method = RequestMethod.GET)
    @ResponseBody
    public String loginVerificationCode(HttpServletRequest request, HttpServletResponse response) {
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 将图像输出到Servlet输出流中。
        try {
            ServletOutputStream sos = response.getOutputStream();
            ImageIO.write(imageCodeUtil.getImage(request), "jpeg", sos);
            sos.close();
        } catch (IOException e) {
            LOGGER.error("LoginController-loginVerificationCode error: {}", e);
        }
        return null;
    }

    @RequestMapping("/loginScanCode")
    @ResponseBody
    public Result loginScanCode(HttpServletRequest request) {
        Result result = new Result(true, null, null);
        try {
            String authKey = UUID.randomUUID().toString().replaceAll("-", "");
            //生产二维码信息至redis
            //二维码信息(qr地址+uuid)
            request.getSession().setAttribute("authKey", authKey);
        } catch (Exception e) {
            String errMsg = StringUtils.isBlank(e.getMessage()) ? e.getMessage() : "登录失败！";
            LOGGER.error("LoginController-loginScanCode error: {},e: {}", errMsg, e);
            result.change(false, errMsg, errMsg);
        }
        return result;
    }

    @RequestMapping("/loginTimeout")
    public String loginTimeout(HttpServletRequest request) {
        return "/pages/common/loginTimeout";
    }

}
