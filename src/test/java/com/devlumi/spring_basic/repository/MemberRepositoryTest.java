package com.devlumi.spring_basic.repository;

import com.devlumi.spring_basic.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  private EntityManager entityManager;

  @Test
  public void testExitst(){
    log.info("testExist : {}", memberRepository);
  }

  @Test
  @Transactional
  @Rollback(false)
  public void testInsert(){
    Member member = Member.builder()
            .no(3L)
            .id("test3")
            .pw("1234")
            .age(15)
            .name("테스터3")
            .build();

    memberRepository.save(member);
  }

  @Test
  public void testFindById(){
    Member member = memberRepository.findById(1L).orElseThrow(() -> new RuntimeException("지정된 회원 번호가 없습니다"));
    log.info("testFindById : {}", member);
  }

  @Test
  public void testFindAll(){
    memberRepository.findAll().forEach(m -> log.info("{}",m));
  }

  @Test
  @Transactional
  @Rollback
  public void testUpdate(){
    Member member = memberRepository.findById(1L).orElseThrow(() -> new RuntimeException("지정된 회원 번호가 없습니다"));
    member.setAge(17);
  }

  @Test
  public void testDelete(){
    memberRepository.deleteById(2L);
  }

  @Test
  public void testEtityManger(){
    log.info("{}",entityManager);
  }
}
