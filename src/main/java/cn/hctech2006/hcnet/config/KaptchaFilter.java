package cn.hctech2006.hcnet.config;

import cn.hctech2006.hcnet.bean.RespBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class KaptchaFilter extends GenericFilterBean {

//    @Override
//    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
//        //非登录请求不验证验证码
//        System.out.println("进入验证");
//        if(!"/login".equals(req.getRequestURI())){
//            chain.doFilter(req,resp);
//        }else{
//            //登陆请求进行验证
//            String requestCode = req.getParameter("kaptcha");
//            HttpSession session = req.getSession();
//            String savedCode = (String) session.getAttribute("kaptcha");
//            System.out.println("requestCode:"+requestCode);
//            System.out.println("savedCode:"+savedCode);
//            System.out.println("now sessionId:"+session.getId());
//            if(!StringUtils.isEmpty(savedCode)){
//                //随手清除验证码，无论登陆失败还是成功
//                session.removeAttribute("kaptcha");
//            }
//            //校验不通过，抛出异常
//            if(StringUtils.isEmpty(requestCode) || StringUtils.isEmpty(savedCode) || ! requestCode.equals(savedCode)){
//                System.out.println("校验失败");
//                return ;
//            }
//            chain.doFilter(req,resp);
//
//        }
//    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //非登录请求不验证验证码
        System.out.println("进入验证");
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        System.out.println("当前登录URL:"+req.getRequestURI()+"当前MEthod:"+req.getMethod());
        System.out.println("before sessionId:"+req.getSession().getId());
        if(!"/login".equals(req.getRequestURI()) || !req.getMethod().equalsIgnoreCase("post") || req.getRequestURI().equalsIgnoreCase("/favicon.ico") ||
                req.getRequestURI().equalsIgnoreCase("/error")
        ){

            chain.doFilter(req,resp);
        }else{
            //登陆请求进行验证
            if(req.getSession().getAttribute("index") != null){
               // req.getSession().removeAttribute("index");
                System.out.println("跳过");
                chain.doFilter(request,response);
            }
            String requestCode = req.getParameter("kaptcha");
            HttpSession session = req.getSession();
            String savedCode = (String) session.getAttribute("kaptcha");
            System.out.println("requestCode:"+requestCode);
            System.out.println("savedCode:"+savedCode);
            System.out.println("now sessionId:"+session.getId());
            if(!StringUtils.isEmpty(savedCode)){
                //随手清除验证码，无论登陆失败还是成功
                session.removeAttribute("kaptcha");
            }
            //校验不通过，抛出异常
            if(StringUtils.isEmpty(requestCode) || StringUtils.isEmpty(savedCode) || ! requestCode.equals(savedCode)){
                System.out.println("校验失败");
                resp.setStatus(401);
                resp.setContentType("application/json;charset=UTf-8");
                RespBean respBean = RespBean.error("验证码校验失败,请重新输入!");
                PrintWriter out = resp.getWriter();
                ObjectMapper om = new ObjectMapper();
                out.write(om.writeValueAsString(respBean));
                return ;
            }
            req.getSession().setAttribute("index","two");
            //return ;
            chain.doFilter(request,response);


        }
    }
}
























