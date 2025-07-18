package com.devlumi.spring_basic.aop;

import com.devlumi.spring_basic.aop.advice.MyBeforeAdvice;
import com.devlumi.spring_basic.aop.advice.MyAroundAdvice;
import com.devlumi.spring_basic.service.FisrtService;
import com.devlumi.spring_basic.service.SecondService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.aop.*;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

@Slf4j
@SpringBootTest
public class ProxyTest {
  @Autowired
  private BoardService boardService;



  @Autowired
  private MyAroundAdvice advice;

  @Autowired
  private MyBeforeAdvice before;

  @Autowired
  private AfterReturningAdvice afterReturn;

  @Autowired
  private ThrowsAdvice throwsAdvice;

  @Autowired
  private Pointcut pointcut;

  private BoardService proxy;

  @PostConstruct
  public void init(){
    Advice[] advices = new Advice[]{afterReturn, throwsAdvice};
    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setTarget(boardService);

    for(Advice a : advices){
      proxyFactory.addAdvice(a);
    }
    proxy = (BoardService) proxyFactory.getProxy();
  }

  @Test
  public void testExist(){
    log.info("{}",boardService);
  }

  @Test
  public void testWrite() {
    boardService.wirte("원본 객체의 제목", "내용");

    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setTarget(boardService);
    proxyFactory.addAdvice(advice);
    BoardService proxy = (BoardService) proxyFactory.getProxy();
    
    proxy.wirte("프록시 객체의 제목", "내용");
  }

  @Test
  public void testBefore(){
    System.out.println("===========글쓰기==============");
    proxy.wirte("프록시 객체의 제목", "내용");
    System.out.println("===========글조회==============");
    proxy.get(3L);
  }

  @Test
  public void testAfterReturn(){
    try{
      proxy.remove(1L);
    } catch (Exception e){
      System.out.println(e.getMessage());
    }

  }

  @Test
  public void testAdvisor() {
    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setTarget(boardService);

    PointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor(pointcut, before);
    proxyFactory.addAdvisor(pointcutAdvisor);

    proxy = (BoardService) proxyFactory.getProxy();

    proxy.wirte("제목","내용");
    proxy.get(3L);
    proxy.remove(4L);
  }


  @Autowired
  private FisrtService fisrtService;
  @Test
  public void testExAdvisor(){
    MethodBeforeAdvice beforeAdvice = (method, args, target) -> System.out.println("익명 출력");
    DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new StaticMethodMatcherPointcut() {
      @Override
      public boolean matches(Method method, Class<?> targetClass) {
        return method.getName().equals("two") && targetClass == FisrtService.class;
      }
    }, beforeAdvice);
    
    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setTarget(fisrtService);
    proxyFactory.addAdvisor(advisor);

    ProxyFactory proxyFactory2 = new ProxyFactory();
    proxyFactory2.setTarget(new SecondService());

    FisrtService proxy = (FisrtService) proxyFactory.getProxy();
    proxy.one();
    proxy.two();
  }
  @Test
  public void testAspectj(){
    AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
    pc.setExpression("execution(void *.write*(..))");  // 반환타입 클래스명 메서드명(반환타입) 순

    DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pc, before);

    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setTarget(boardService);

    proxyFactory.addAdvisor(advisor);

    BoardService proxy = (BoardService) proxyFactory.getProxy();
    proxy.wirte("title", "content");
    proxy.get(3L);
  }
}
