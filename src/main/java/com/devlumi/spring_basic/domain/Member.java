package com.devlumi.spring_basic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
//@Component
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
//  @Value("test2")
  private String name;
//  @Value("12")
  private int age;

  private String id;

  public Member(String s, int i) {
    this.name= s;
    this.age = i;
  }
}
