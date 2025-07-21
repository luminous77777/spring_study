package com.devlumi.spring_basic.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_member")
@Setter
@Getter
@ToString(exclude = "boards")
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;
  private String name;
  private String id;
  private String pw;
  private int age;

  @OneToMany(mappedBy = "member")
  private List<Board> boards;

}
