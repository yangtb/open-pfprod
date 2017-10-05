package com.sm.pfprod.web.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: BaseController
 * @Description: 基础controller，其他controller都继承此controller
 * @author 王勇琳
 * @date 2017年6月29日 下午5:29:44
 */
public abstract class BaseController {

	protected final static Logger logger = LoggerFactory.getLogger(BaseController.class);

	@ModelAttribute
	public void commonModel(Model model, HttpServletRequest request) {
		// 上下文路径：'/projectName'
		String contextPath = request.getContextPath();
		model.addAttribute("contextPath", contextPath);
		logger.debug("上下文路径：{}", contextPath);
		// 全文路径：'http://localhost:8080/projectName'
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath;
		model.addAttribute("basePath", basePath);
		logger.debug("全文路径：{}", basePath);
	}


}
