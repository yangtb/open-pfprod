package com.sm.pfprod.web.portal;

import com.sm.open.care.core.utils.rsa.RsaKeyPair;
import com.sm.pfprod.web.security.rsa.RsaKeyPairQueue;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: IndexController
 * @Description: 首页控制器跳转
 */
@Controller
public class LoginController extends BaseController {

	/** 用户业务错误信息显示的常量变量名 */
	private static final String ERROR_MSG = "errorMsg";
	/** rsa公钥常量变量名 */
	private static final String PUBLIC_KEY = "publicKey";

	@Resource(name="rsaKeyPairQueue")
	private RsaKeyPairQueue rsaKeyPairQueue;

	@RequestMapping(value = "/login")
	public String home(Model model, HttpServletRequest request) {
		this.setModelAttr(model, request);
		return "login";
	}

	@RequestMapping(value = "/login/{errorMsg}")
	public String homeError(@PathVariable String errorMsg, Model model, HttpServletRequest request) {
		if(StringUtils.isNotBlank(errorMsg)){
			model.addAttribute(ERROR_MSG, new String(Base64.decodeBase64(errorMsg)));
		}else{
			model.addAttribute(ERROR_MSG, "");
		}
		this.setModelAttr(model, request);
		return "login";
	}

	private void setModelAttr(Model model, HttpServletRequest request){
		RsaKeyPair keyPair;
		try {
			keyPair = rsaKeyPairQueue.takeQueue(request);
			model.addAttribute(PUBLIC_KEY, keyPair.getPublicKey());
		} catch (InterruptedException e) {
			logger.error("进入首页时，rsa公私钥队列相关操作异常", e);
		}
	}

}
