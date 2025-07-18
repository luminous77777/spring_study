package com.devlumi.spring_basic.aop;

public interface BoardService {
  void wirte(String title, String content);

  Object get(Long bno);
  void modify(String title,String content);
  void remove(Long bno);
}
