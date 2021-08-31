package com.ys.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;


@Entity(name = "user_info")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UserInfo extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_info_seq")
    private Long seq;

    private Integer age;

    @Column(length = 10)
    private String sex;

    @Column(length = 100)
    private String address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_acct_seq")
    private UserAccount userAccount;


    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        userAccount.setUserInfo(this);
    }

}
