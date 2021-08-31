package com.ys.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Entity(name = "user_acct")
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class UserAccount extends BaseEntity {

    @Id @Column(name = "user_acct_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;


    @Column(unique = true, length = 100)
    private String userId;

    @Column(name = "user_nm", length = 100)
    private String userName;


    @OneToOne(mappedBy = "userAccount", fetch = FetchType.LAZY)
    private UserInfo userInfo;

}
