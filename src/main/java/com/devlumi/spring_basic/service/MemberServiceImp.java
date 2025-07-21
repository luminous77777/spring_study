package com.devlumi.spring_basic.service;

import com.devlumi.spring_basic.domain.Member;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("mem1")
@ToString
@RequiredArgsConstructor
public class MemberServiceImp implements MemberService{

  @Override
  public void register(Member member) {

  }
}
