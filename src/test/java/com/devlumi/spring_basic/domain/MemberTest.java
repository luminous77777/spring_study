package com.devlumi.spring_basic.domain;

import com.devlumi.spring_basic.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@SpringBootTest
//@ContextConfiguration(locations = "classpath:xml/bean-config.xml")
@ContextConfiguration(classes = AppConfig.class)
public class MemberTest {
  @Autowired //타입으로 탐색
  Member member;

  @Test
  public void testExist(){
    log.info("{}", member);
  }
}
