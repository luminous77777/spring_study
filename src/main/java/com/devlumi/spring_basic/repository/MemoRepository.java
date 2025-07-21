package com.devlumi.spring_basic.repository;

import com.devlumi.spring_basic.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}
