package com.devlumi.spring_basic.main;

import com.devlumi.spring_basic.domain.Member;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MemberMain2 {
  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("xml/bean-config-java.xml");

    Member m = context.getBean("member", Member.class);
    Member m2 = context.getBean("member", Member.class);
    System.out.println(m == m2);
  }
}
