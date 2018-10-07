package com.sm.pfprod.web.portal.common;

import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.utils.Assert;
import com.sm.open.care.core.utils.FileTypeUtil;
import com.sm.pfprod.model.entity.BasMedia;
import com.sm.pfprod.service.common.upload.PfUploadService;
import com.sm.pfprod.web.portal.BaseController;
import com.sm.pfprod.web.security.CurrentUserUtils;
import com.sm.pfprod.web.util.oss.OSSUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName: PfUploadController
 * @Description: 上传控制器
 * @Author yangtongbin
 * @Date 2018/10/6
 */
@Controller
public class PfUploadController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfUploadController.class);

    @Resource
    private PfUploadService pfUploadService;

    @ResponseBody
    @RequestMapping(value = "/upload")
    public ResultObject uploadFile(HttpServletRequest request) {
        // 转型为MultipartHttpRequest：
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得文件：
        MultipartFile file = multipartRequest.getFile("file");
        Assert.isTrue(!file.isEmpty(), "请选择要上传文件");
        // 获取上传的文件的名称
        String originalFilename = file.getOriginalFilename();
        String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        // 上传oss
        String url = "http://hsknowledgebase.oss-cn-hangzhou.aliyuncs.com/mytest/music.mp3";
        String fileName = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        //OSSUploadUtil.uploadFile(file, fileType, null, request, 1);
        // todo 模拟进度条 begin
        HttpSession session = request.getSession();

        int a = 10;
        while (true) {
            int percent = (int) (a * 100.0 / 100);
            session.setAttribute("upload_percent", percent);
            session.setAttribute("upload_sum", 1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(session.getAttribute("upload_percent"));
            a = a + 10;
            if (a == 100) {
                break;
            }
        }
        // todo end
        LOGGER.warn("上传返回url : " + url);
        // 上传后url保存至数据库
        BasMedia dto = new BasMedia();
        dto.setSdType(FileTypeUtil.getFileTypeEnum(fileType));
        dto.setDes(originalFilename);
        dto.setName(fileName);
        dto.setFormat(fileType);
        dto.setSize(file.getSize());
        dto.setPath(url);
        dto.setCreator(CurrentUserUtils.getCurrentUsername());
        dto.setOperator(CurrentUserUtils.getCurrentUsername());

        Long idMedia = pfUploadService.addBasMedia(dto);
        dto.setIdMedia(idMedia);
        return ResultObject.createSuccess("/uploadFile", ResultObject.DATA_TYPE_OBJECT, dto);
    }

    /**
     * 文件上传进度
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/selectUploadPercent", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> selectUploadPercent(HttpServletRequest request) {
        HttpSession session = request.getSession();
        //图片上传进度
        int percent = session.getAttribute("upload_percent") == null ?
                0 : Integer.parseInt(session.getAttribute("upload_percent").toString());
        //第几张图片
        int sum = session.getAttribute("upload_sum") == null ?
                0 : Integer.parseInt(session.getAttribute("upload_sum").toString());
        //图片全部上传结束
        int end = session.getAttribute("upload_end") == null ?
                0 : Integer.parseInt(session.getAttribute("upload_end").toString());
        Map<String, Integer> map = new HashMap<>(3);
        map.put("percent", percent);
        map.put("sum", sum);
        map.put("end", end);
        return map;
    }

    /**
     * 数据复原
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/clearUploadPercent", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> clearUploadPercent(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("upload_percent", 0);
        session.setAttribute("upload_sum", 1);
        session.setAttribute("upload_end", 0);

        Map<String, Integer> map = new HashMap<>(3);
        map.put("percent", 0);
        map.put("sum", 0);
        map.put("end", 0);
        return map;
    }

}
