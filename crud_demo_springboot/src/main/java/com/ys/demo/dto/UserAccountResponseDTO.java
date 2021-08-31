package com.ys.demo.dto;

import com.ys.demo.model.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountResponseDTO {

    private Long seq;

    private String userId;

    private String userName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public UserAccountResponseDTO(UserAccount userAccount) {
        this.seq = userAccount.getSeq();
        this.userId = userAccount.getUserId();
        this.userName = userAccount.getUserName();
        this.createdAt = userAccount.getCreatedAt();
        this.updatedAt = userAccount.getUpdatedAt();
    }

}
