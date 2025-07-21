package com.devlumi.spring_basic.repository;

import com.devlumi.spring_basic.domain.Member;
import com.devlumi.spring_basic.domain.Memo;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class MemoRepositoryTest {

  @Autowired
  MemoRepository memoRepository;

  @Autowired
  private EntityManager entityManager;


  @Test
  public void testExitst(){
    log.info("testExist : {}", memoRepository);
  }

  @Test
  @Transactional
  @Rollback(value = false)
  public void testInsert(){
    Memo memo = Memo.builder().memoText("기본값 아닌 텍스트").build();
    memoRepository.save(memo);
  }

  @Test
  public void testFindById(){
    Memo memo = memoRepository.findById(2L).orElseThrow(() -> new RuntimeException("지정된 메모 번호가 없습니다"));
  }

  @Test
  public void testFindAll(){
    memoRepository.findAll().forEach(m -> log.info("{}",m));
  }

  @Test
  @Transactional
  @Rollback
  public void testUpdate(){
    Memo memo =  memoRepository.findById(2L).orElseThrow(() -> new RuntimeException("지정된 회원 번호가 없습니다"));
    memo.setMemoText("수정하기 테스트");
  }

  @Test
  public void testDelete(){
    Memo memo = entityManager.find(Memo.class, 2L);
    memoRepository.delete(memo);
  }

  @Test
  public void testEtityManger(){
    log.info("{}",entityManager);
    entityManager.persist(new Memo());
  }

  @Test
  @Transactional
  @Rollback(value = false)
  public void testEtityManger2(){
    Memo memo = memoRepository.findById(2L).orElseThrow(RuntimeException::new);
    memo.setMemoText("Hello World");
    entityManager.persist(new Memo());
    //save 메서드없음
    //jpa는 dirty checkging을 통한 값 변경 감지를 한다
  }

  @Test
  public void testEntityManger3(){
    Memo memo = entityManager.find(Memo.class,2L);
    memo.setMemoText("퍼시스턴스 테스트");
    entityManager.flush();
  }

  @Test
  @Transactional
  @Rollback(false)
  public void testEntityManager4(){
    Memo memo = new Memo();
    memo.setMno(2L);
    memo.setMemoText("비영속");

    entityManager.merge(memo);
  }
}
