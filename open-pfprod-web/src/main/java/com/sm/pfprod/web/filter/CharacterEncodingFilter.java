package com.sm.pfprod.web.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: session上下文过滤器，
 * >>>>>>>>>>>>>>将request，response写入到SessionContent中
 * @ClassName: CharacterEncodingFilter.java
 * @Package: com.yuntai.med.spring.filter
 * @Author: dingmj@hundsun.com
 * @Date: 2014年11月18日 下午3:15:00
 * @Copyright: 版权归 Hundsun 所有
 * <ModifyLog>
 * @ModifyContent:
 * @Author:
 * @Date:	
 * </ModifyLog>	
 */
public class CharacterEncodingFilter extends OncePerRequestFilter{
    
    private String encoding;

    private boolean forceEncoding = false;
    
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    
    public void setForceEncoding(boolean forceEncoding) {
        this.forceEncoding = forceEncoding;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (this.encoding != null && (this.forceEncoding || request.getCharacterEncoding() == null)) {
            request.setCharacterEncoding(this.encoding);
            if (this.forceEncoding) {
                response.setCharacterEncoding(this.encoding);
            }
        }
        filterChain.doFilter(request, response);
    }

	
}
