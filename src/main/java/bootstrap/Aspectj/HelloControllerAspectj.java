package bootstrap.Aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * 顺序：环绕前，before，环绕后，after。
 */
@Aspect
@Component
public class HelloControllerAspectj {

    @Pointcut("execution(public * bootstrap.controller.HelloController.index(..))")
    public void m1(){
        System.out.println("这个啥时候执行?");
    }

    @Around("m1()")
    public Object around(ProceedingJoinPoint point){

        Object result = null;
        try {
            result = point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("end");
        return result;

    }
    @Before("m1()")
    public void before(JoinPoint pjp){
        Object[] args = pjp.getArgs();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        System.out.println("before");
    }

    @After("m1()")
    public void after(JoinPoint pjp){
        System.out.println("after");
    }
}
