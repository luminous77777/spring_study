package com.devlumi.spring_basic.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

  @Before(value = "execution(* com..*.get(..))") //이름이 get인 모든 위치의 메서드
  public void before(JoinPoint joinPoint){
    System.out.println(joinPoint.getSignature().getName());
  }

  @Around("execution(* com..*.get(Long))")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable{ //반환타입이 있어야하는 around
    System.out.println("around's before");
    Object ret = joinPoint.proceed();
    System.out.println("around's after");
    return ret;
  }

  @After("bean(boardServiceImpl)")
  public void after(JoinPoint joinPoint){
    System.out.println("after finally");
  }

  @AfterReturning("args(String, String) && execution(* *..BoardServiceImpl.*(..))")
  public void afterReturn(JoinPoint joinPoint){
    System.out.println("정상 종료 afterReturning");
  }
}
