package com.ys.demo.dto;

import com.ys.demo.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class UserAccountDTO {

    @NotNull
    private String userId;

    @NotNull
    private String userName;


    public UserAccount toEntity() {
        return UserAccount.builder()
                .userId(userId)
                .userName(userName)
                .build();
    }
}
