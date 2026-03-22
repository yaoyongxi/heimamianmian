package com.itheima.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/store/*", "/system/*"})
public class AuthorFilter implements Filter {

    private FilterConfig filterConfig;

    /**
     * 初始化方法，获取过滤器的配置对象
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        //1.定义和协议相关的请求和响应对象
        HttpServletRequest request ;
        HttpServletResponse response;
        HttpSession session;
        try{
            //2.把参数转换成协议相关的对象
            request = (HttpServletRequest)req;
            response = (HttpServletResponse)resp;
            session=request.getSession();
            //3.获取本次操作
            String url = request.getRequestURI();
            //.css .js .png .jpg .index
            if(url.endsWith(".css")
                    || url.endsWith(".js")
                    || url.endsWith(".png")
                    || url.endsWith(".jpg")
                    || url.endsWith("index.jsp")
                    || url.endsWith("unauthorized.jsp")
                    || url.endsWith("login.jsp")){
                //6.放行
                chain.doFilter(request,response);
                return;
            }
            String queryString = request.getQueryString();
            if(queryString.endsWith("operation=login")
                    ||queryString.endsWith("operation=home")
                    ||queryString.endsWith("operation=logout")){
                //6.放行
                chain.doFilter(request,response);
                return;
            }
//            3.1当前获取到的url:    /system/dept
            url=url.substring(1);
            //3.2当前获取到查询参数：     operation=list          operation=toEdit&id=100
            int index = queryString.indexOf("&");//如果没&符号，则返回"-1"
            if(index !=-1){
                queryString=queryString.substring(0,index);
            }
            url=url+"?"+queryString;
            //4.获取到当前登录人允许的操作
            String authorStr = session.getAttribute("authorStr").toString();
            //5.比对本次操作是否在当前登录人允许的操作范围内
            if(authorStr.contains(url)){
                //如果允许，放行；
                chain.doFilter(request,response);
                return;
            }else {
                // 不允许，跳转到非法访问页
                response.sendRedirect(request.getContextPath()+"/unauthorized.jsp");
            }
            //6.放行
//            chain.doFilter(request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void destroy() {
        //可以做一些清理操作
    }
}

