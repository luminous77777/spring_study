package com.devlumi.spring_basic.repository;

import com.devlumi.spring_basic.domain.Member;
import com.devlumi.spring_basic.domain.Memo;
import com.devlumi.spring_basic.domain.dto.MemoDTO;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

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
  public void testClass(){
    log.info("{}",memoRepository.getClass().getName());
  }

  @Test
  @Transactional
  @Rollback(value = false)
  public void testInsert(){
    Memo memo = Memo.builder().memoText("기본값 아닌 텍스트").build();
    memoRepository.save(memo);
  }

  @Test
  public void testInsertDummies(){
    IntStream.range(0, 100).forEach(i -> {
      Memo memo = Memo.builder().memoText("Sample..."+i).build();
      memoRepository.save(memo);
    });
  }

  @Test
  public void testFindById(){
    Memo memo = memoRepository.findById(3L).orElseThrow(() -> new RuntimeException("지정된 메모 번호가 없습니다"));
  }



  @Test
  public void testFindAll(){
    memoRepository.findAll().forEach(m -> log.info("{}",m));
  }

  @Test
  @Transactional
  @Rollback
  public void testUpdate(){
    Memo memo =  memoRepository.findById(2L).orElseThrow(() -> new RuntimeException("지정된 메모 번호가 없습니다"));
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

  @Test
  public void testPageDefault(){
    PageRequest pageRequest = PageRequest.of(9, 10);
    Page<Memo> result = memoRepository.findAll(pageRequest);
    result.forEach(r-> log.info("{}",r));

    long totalElements =  result.getTotalElements();
    int totalPages = result.getTotalPages();

    log.info("totalElements :{}",totalElements);
    log.info("totalPages : {}",totalPages);

    log.info("=================");

    log.info("Total Pages  :{}",result.getTotalPages());

    log.info("Total Count: {}", result.getTotalElements());

    log.info("Page Number : {}", result.getNumber());

    log.info("Page Size : {}", result.getSize());

    log.info("has next page? : {}", result.hasNext());

    log.info("first page? :{}", result.isFirst());

    result.getContent().forEach(c -> log.info("{}", c));
  }

  @Test
  public void testSort() {
    Sort sort = Sort.by(Sort.Direction.DESC, "mno");
    PageRequest pageRequest = PageRequest.of(0, 10, sort);
    Page<Memo> result = memoRepository.findAll(pageRequest);
    result.forEach(r-> log.info("{}",r));
  }

  @Test
  public void testQueryMethod(){
    memoRepository. findByMnoBetweenOrderByMnoDesc(70L, 80L).forEach(m -> log.info("{}",m));
  }

  @Test
  public void testQueryMethod2(){
    Page<Memo> memos = memoRepository.findByMnoBetween(10L, 50L, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno")));
  }

  @Test
  public void testCountByMno(){
     Long count = memoRepository.count();
     log.info("{}",count);

     //Mno가 특정 long 이거나 memoText가 특정 문자열일때의 query method
  }

  @Test
  @Commit
  public void testMethod(){
    memoRepository.findByMnoOrMemoText(55L,"79").forEach(m -> log.info("{}",m));
  }

  @Test
  public void testQuery(){
    memoRepository.getListDesc().forEach(m-> log.info("{}",m));
  }

  @Test
  public void testQuery2(){
    memoRepository.getListDesc2().forEach(m-> log.info("{}",m));
  }

  @Test
  public void testUpdateMEmoText(){
    Memo memo =  memoRepository.findById(8L).orElseThrow();
    memo.setMemoText("변경내용 1");
  }

  @Test
  public void testUpdateMemoText2(){
    memoRepository.updateMemoText(5L, "변경 내용2");
  }

  @Test
  public void testUpdateMemoText3(){
    memoRepository.updateMemoText2(Memo.builder().mno(10L).memoText("변경내용 3").build());
  }

  @Test
  public void testUpdateMEmoText4(){
    memoRepository.updateMemoText3(7L,"순서 찾기로 Param 생략");
  }

  @Test
  public void testListWithQueryOjbect(){
    Page<Object[]> objects = memoRepository.getListWithQueryOjbect(0L, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno")));
    objects.forEach(r-> {
      for(Object o : r) {
        log.info("{}", o);
      }
    });
  }

  @Test
  public void testListWithQueryProjection(){
    Page<MemoDTO> dtos = memoRepository.getListWithQueryProjection(11L,PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno")));
    dtos.forEach(r-> log.info("{}",r.getMemoText()));
  }
}
