package com.devlumi.spring_basic.service;

import org.springframework.stereotype.Service;

@Service
public class SecondService {
  public void one(){
    System.out.println("second.one()");
  }
  public void two(){
    System.out.println("second.two()");
  }
}
