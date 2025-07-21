package com.devlumi.spring_basic.service;

import com.devlumi.spring_basic.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("mem2")
public class MemberServiceImp2 implements MemberService{

  @Override
  public void register(Member member) {

  }
}
