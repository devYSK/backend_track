package com.ys.demo.dto;

import com.ys.demo.model.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoResponseDTO {

    private Long seq;

    private Integer age;

    private String sex;

    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UserAccountResponseDTO userAccountResponseDTO;


    public UserInfoResponseDTO(UserInfo userInfo, UserAccountResponseDTO userAccountResponseDTO) {
        this.seq = userInfo.getSeq();
        this.age = userInfo.getAge();
        this.sex = userInfo.getSex();
        this.address = userInfo.getAddress();
        this.createdAt = userInfo.getCreatedAt();
        this.updatedAt = userInfo.getUpdatedAt();
        this.userAccountResponseDTO = userAccountResponseDTO;
    }

}
