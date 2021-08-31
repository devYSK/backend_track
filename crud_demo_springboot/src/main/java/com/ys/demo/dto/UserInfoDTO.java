package com.ys.demo.dto;

import com.ys.demo.model.UserAccount;
import com.ys.demo.model.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class UserInfoDTO {

    @NotNull
    private Integer age;

    @NotNull
    private String sex;

    @NotNull
    private String address;

    @NotNull
    private Long userSeq;

    public UserInfo toEntity() {
        return UserInfo.builder()
                .age(age)
                .sex(sex)
                .address(address)
                .build();
    }

}
