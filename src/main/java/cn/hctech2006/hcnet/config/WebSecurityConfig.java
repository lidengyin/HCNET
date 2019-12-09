package cn.hctech2006.hcnet.config;

import cn.hctech2006.hcnet.bean.RespBean;
import cn.hctech2006.hcnet.bean.SysRole;
import cn.hctech2006.hcnet.bean.SysUser;

import cn.hctech2006.hcnet.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomMetadadaSource customMetadadaSource;
    @Autowired
    private UrlAccessDecisionManager urlAccessDecisionManager;
    @Autowired
    private AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;



    @Bean
    public KaptchaFilter kaptchaFilter(){
        return new KaptchaFilter();
    }

    @Bean
    public FilterRegistrationBean registrationBean(KaptchaFilter filter){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(filter);
        filterRegistrationBean.setEnabled(false);
        return filterRegistrationBean;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**","/login.html","/defaultKaptcha","/favicon.ico");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(urlAccessDecisionManager);
                        o.setSecurityMetadataSource(customMetadadaSource);
                        return o;
                    }
                })
                .and()

                .formLogin()
                .loginPage("/login.html")
                //.permitAll()
                //登录请求处理接口
                .loginProcessingUrl("/login")
                //.permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        resp.setStatus(401);
                        resp.setContentType("application/json;charset=UTF-8");
                        RespBean respBean;
                        if(e instanceof BadCredentialsException || e instanceof UsernameNotFoundException){
                            System.out.println("111");
                            respBean = RespBean.error("账户或密码错误",null);
                        }else if(e instanceof LockedException){
                            System.out.println("222");
                            respBean = RespBean.error("账户被锁定, 请联系管理员");
                        }else if(e instanceof CredentialsExpiredException){
                            System.out.println("333");
                            respBean= RespBean.error("密码过期请联系管理员");
                        }else if(e instanceof AccountExpiredException){
                            System.out.println("444");
                            respBean = RespBean.error("账户过期,请联系管理员");
                        }else if(e instanceof DisabledException){
                            System.out.println("555");
                           respBean = RespBean.error("账户被禁用,请联系管理员");
                        }else{
                            System.out.println("666");
                            respBean = RespBean.error("登陆失败");

                        }

                        ObjectMapper om = new ObjectMapper();
                        PrintWriter out = resp.getWriter();
                        out.write(om.writeValueAsString(respBean));
                        System.out.println("失败操作");
                        out.flush();
                        out.close();
                    }

                })
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req,
                                                        HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=UTF-8");
                        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        System.out.println(sysUser.getUserName() + sysUser.getPassword());
                        List<SysRole> roleList = sysUser.getRoleList();
                        for(SysRole role: roleList){
                            System.out.println("role" + role.getRoleName());
                        }
                        RespBean respBean = RespBean.ok("登录成功", sysUser);
                        ObjectMapper om = new ObjectMapper();
                        PrintWriter out = resp.getWriter();
                        out.write(om.writeValueAsString(respBean));
                        out.flush();
                        out.close();

                    }
                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req,
                                                HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                       resp.setContentType("application/json;charset=UTF-8");

                       RespBean respBean = RespBean.ok("注销成功");
                       ObjectMapper om = new ObjectMapper();
                       PrintWriter out = resp.getWriter();
                       out.write(om.writeValueAsString(respBean));
                       out.flush();
                       out.close();
                    }
                })
                .permitAll()
                .and()

                .csrf()
                .disable()

                .exceptionHandling().accessDeniedHandler(authenticationAccessDeniedHandler)
                ;
                //添加自动登陆的功能，默认为简单散列加密;
                //http.rememberMe().userDetailsService(userService).key("blurooo");
                //session管理
                //session失效后跳转
                //http.sessionManagement().invalidSessionUrl("/login.html");
                //只允许一个用户登录,如果有同一个账户两次登录,那么第一个账户将被提下线
                //http.sessionManagement().maximumSessions(1)
                        //.sessionRegistry(sessionRegistry())
                        //.expiredUrl("/login.html");
                //将过滤器添加到UsernamePasswordAuthenticationFilter之前
                http.addFilterBefore(kaptchaFilter(),UsernamePasswordAuthenticationFilter.class);

    }
//    //session失效后跳转
//    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
//        return new SimpleRedirectSessionInformationExpiredStrategy("/login.html");
//    }
//    @Bean
//    public SessionRegistry sessionRegistry(){
//        return new SessionRegistryImpl();
//    }
//    //Spring Security内置的session监听器
//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher(){
//        return new HttpSessionEventPublisher();
//    }


    /**
     *  We do this to ensure our Filter is only loaded once into Application Context
     *
     */

}














