package bootstrap.Aspectj;

import bootstrap.annotations.ApiNeedLogin;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

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
        ApiNeedLogin methodAnnotation = getMethodAnnotation(point, ApiNeedLogin.class);
        System.out.println(methodAnnotation.value());

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
    private <T extends Annotation> T getMethodAnnotation(ProceedingJoinPoint joinPoint, Class<T> clazz) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        return method.getAnnotation(clazz);
    }
}
