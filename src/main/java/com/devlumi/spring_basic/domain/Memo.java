package com.devlumi.spring_basic.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_memo")
@ToString
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Memo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long mno;

  @Column(length = 200, nullable = false)
  private String memoText = "기본값";
}
