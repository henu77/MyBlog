package cn.malong.blog.aspect;

import cn.malong.blog.pojo.UserInfo;
import cn.malong.blog.utils.ResponseUtil;
import cn.malong.blog.utils.servlet.ServletUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * @author marlone
 * @Date 2021/11/24 13:34
 */
@Aspect
@Component
public class EmailVerifyAspect {
    @Around("execution(* cn.malong.blog.controller.LoginController.register(..))")
    public Object around(ProceedingJoinPoint pj) throws Throwable {
        Object[] args = pj.getArgs();
        UserInfo arg0 = (UserInfo) args[0];
        HttpSession session = ServletUtil.getSession();
        Object registerEmailVerifyCode = session.getAttribute("registerEmailVerifyCode");
        if (null != registerEmailVerifyCode && registerEmailVerifyCode.equals(arg0.getVerifyCode())) {
            return pj.proceed(args);
        } else {
            ResponseUtil<String> json = new ResponseUtil<>();
            json.setCode(0);
            if (null == registerEmailVerifyCode) {
                json.setMsg("您还没有发送验证码！");
            } else {
                json.setMsg("验证码错误！");
            }
            return json.toString();
        }
    }
}
