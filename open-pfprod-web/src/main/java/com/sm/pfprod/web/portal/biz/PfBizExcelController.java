package com.sm.pfprod.web.portal.biz;

import com.sm.open.care.core.utils.ExcelUtils;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.result.ResultFactory;
import com.sm.pfprod.web.portal.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: PfBizExcelController
 * @Description: excel处理案例
 * @Author yangtongbin
 * @Date 2018/3/20 15:49
 */
@Controller
@RequestMapping(value = "/pf/p/biz/excel")
public class PfBizExcelController extends BaseController {

    @PreAuthorize("hasAnyRole('ROLE_BIZ_EXCEL_MG','ROLE_SUPER')")
    @RequestMapping("/page")
    public String page() {
        return "pages/biz/excelDemo";
    }

    /**
     * 获取列表
     */
    @PreAuthorize("hasAnyRole('ROLE_SUPER')")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageResult list() {
        return ResultFactory.initPageResultWithSuccess(0L, null);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER')")
    @RequestMapping("/downloadExcel")
    @ResponseBody
    public String downloadExcel(HttpServletResponse response, HttpServletRequest request) {
        List<List<String>> shts = new ArrayList<>();
        String[] titles = {"姓名", "手机号", "注册时间", "补贴(元)"};
        int[] size = {5000, 5000, 5000, 5000};
        String fileName = "医生补贴发放表格模版";
        String header = "医生补贴发放明细";

        ExcelUtils.setFileDownloadHeader(response, request, fileName + ".xls");
        ExcelUtils.createXlsFileHeader(response, titles, shts, size, header);
        return null;
    }
}
