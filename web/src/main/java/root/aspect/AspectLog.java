package root.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.time.LocalDateTime;

@Aspect
public class AspectLog {
    static final Logger logger = LogManager.getLogger(AspectLog.class);

    @Pointcut("execution(public * root.service.*.*(..))")
    public void isRepositoryLayer1() {}


   @Around("isRepositoryLayer1()")
    public Object aroundLogging(ProceedingJoinPoint joinPoint) throws Throwable {
       Signature signature = joinPoint.getSignature();
       String s1 = " METOD "+signature.toString()+" TIME "+LocalDateTime.now();
       logger.info(s1);
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            logger.error(e.getMessage());
            throw e;
        }
        return result;
    }
}
