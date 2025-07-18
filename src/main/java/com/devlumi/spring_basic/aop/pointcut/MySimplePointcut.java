package com.devlumi.spring_basic.aop.pointcut;

import com.devlumi.spring_basic.service.FisrtService;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class MySimplePointcut extends StaticMethodMatcherPointcut {

  @Override
  public boolean matches(Method method, Class<?> targetClass) {
    //매개변수 갯수가 1개 그리고 리턴타입이 void
//    System.out.println(method.getReturnType());
//    System.out.println(method.getParameterCount());
//    System.out.println(method.getName());
//    System.out.println(method.getDeclaringClass());
//    System.out.println(method.getDefaultValue());

    return method.getParameterCount() == 1 && method.getReturnType() == void.class;
//      return method.getName().equals("two") && targetClass == FisrtService.class;
  }

}
