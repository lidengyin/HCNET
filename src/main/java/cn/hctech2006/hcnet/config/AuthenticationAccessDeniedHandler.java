package cn.hctech2006.hcnet.config;

import cn.hctech2006.hcnet.bean.RespBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@Component
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp, AccessDeniedException e) throws IOException, ServletException {
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        ObjectMapper om = new ObjectMapper();
        RespBean respBean = RespBean.error("权限不足，请联系管理员");
        out.write(om.writeValueAsString(respBean));
        out.flush();
        out.close();

    }
}
