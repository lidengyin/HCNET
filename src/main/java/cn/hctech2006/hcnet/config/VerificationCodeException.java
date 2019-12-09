package cn.hctech2006.hcnet.config;

import javax.naming.AuthenticationException;
//自定义一个验证码校验失败异常
public class VerificationCodeException extends AuthenticationException {
    public VerificationCodeException(){
        super("图像验证码校验失败");
        return ;
    }

}
