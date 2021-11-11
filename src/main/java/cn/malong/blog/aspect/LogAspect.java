package cn.malong.blog.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 日志类
 * @author malong
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* cn.malong.blog.controller.*.*(..))")
    public void log(){}


    //请求进来时，获取对应的信息
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        //获取请求的URL地址
        String url=request.getRequestURL().toString();
        //获取请求的ip地址
        String ip=request.getRemoteAddr();
        //获取请求的类以及方法
        String classMethod=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        //获取请求的参数
        Object[] args=joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("Request : {}",requestLog);
    }

    @After("log()")
    public void doAfter(){
    }

    //获取响应方法的返回值
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterRutrun(Object result){
        logger.info("Result : {}",result);
    }

    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
