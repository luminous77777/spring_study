package com.devlumi.spring_basic.repository;

import com.devlumi.spring_basic.domain.Memo;
import com.devlumi.spring_basic.domain.dto.MemoDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

  List<Memo> findByMnoBetweenOrderByMnoDesc(Long from ,Long to);

  Page<Memo> findByMnoBetween(Long mno1, Long mno2, Pageable pageable);

  List<Memo> findByMnoOrMemoText(Long mno, String memoText);

  @Query("select m from Memo m order by m.mno desc limit 10")
  List<Memo> getListDesc();

  @Query(value = "select * from tbl_memo order by mno desc limit 10", nativeQuery = true)
  List<Memo> getListDesc2();

  @Transactional
  @Modifying
  @Query("update Memo m set m.memoText = :memoText where m.mno = :mno")
  int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);

  @Transactional
  @Modifying
  @Query("update Memo m set m.memoText = :#{#memo.memoText} where m.mno = :#{#memo.mno}")
  int updateMemoText2(@Param("memo") Memo memo);

  @Transactional
  @Modifying
  @Query("update Memo m set m.memoText = ?2 where m.mno = ?1")
  int updateMemoText3(Long mno, String memoText);

  @Query(value = "select m.mno, m.memoText, CURRENT_DATE AS n from Memo m where m.mno > :mno"
          , countQuery="select count(m) from Memo m where m.mno > :mno")
  Page<Object[]> getListWithQueryOjbect(Long mno, Pageable pageable);

  @Query(value = "select m.mno AS mno, m.memoText AS memoText, CURRENT_DATE AS n from Memo m where m.mno > :mno"
          , countQuery="select count(m) from Memo m where m.mno > :mno")
  Page<MemoDTO> getListWithQueryProjection(Long mno, Pageable pageable);
}
