package com.sm.pfprod.web.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: XSSFilter
 * @Description: 防御XSS攻击: 采用Filter技术，对所有参数都进行过滤，处理方案为：
 * * 1. 含有html标签做转义。
 * * 2. 含有敏感的html脚本，直接处理掉
 * @Author yangtongbin
 * @Date 2018-12-10
 */
public class XSSFilter implements Filter {

    /**
     * XSS处理Map
     */
    private static Map<String, String> xssMap = new LinkedHashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 含有脚本： script
        xssMap.put("[s|S][c|C][r|R][i|C][p|P][t|T]", "");
        // 含有脚本 javascript
        xssMap.put("[\\\"\\\'][\\s]*[j|J][a|A][v|V][a|A][s|S][c|C][r|R][i|I][p|P][t|T]:(.*)[\\\"\\\']", "\"\"");
        // 含有函数： eval
        xssMap.put("[e|E][v|V][a|A][l|L]\\((.*)\\)", "");
        // 含有符号 <
        xssMap.put("<", "&lt;");
        // 含有符号 (
        xssMap.put("\\(", "(");
        // 含有符号 >
        xssMap.put(">", "&gt;");
        // 含有符号 )
        xssMap.put("\\)", ")");
        // 含有符号 '
        xssMap.put("\"", "\"");
        // 含有符号 "
        xssMap.put("'", "'");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 1：强制类型转换 HttpServletRequest
        HttpServletRequest httpReq = (HttpServletRequest) request;
        // 2：构造HttpRequestWrapper对象处理XSS
        HttpRequestWrapper httpReqWarp = new HttpRequestWrapper(httpReq, xssMap);
        // 3：执行后续Filter
        chain.doFilter(httpReqWarp, response);
    }

    @Override
    public void destroy() {

    }
}
