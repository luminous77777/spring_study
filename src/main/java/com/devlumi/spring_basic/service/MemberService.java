package com.devlumi.spring_basic.service;

import com.devlumi.spring_basic.domain.Member;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@ToString
//@Setter
public interface MemberService {

  void register(Member member);

//  //setter Injection
//  @Autowired
//  public void setMember(Member member){
//    this.member = member;
//  }
}
