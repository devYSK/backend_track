package com.ys.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ys.demo.dto.UserInfoDTO;
import com.ys.demo.model.UserAccount;
import com.ys.demo.model.UserInfo;
import com.ys.demo.repository.UserAccountRepository;
import com.ys.demo.repository.UserInfoRepository;
import com.ys.demo.service.UserInfoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserInfoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    UserInfoRepository userInfoRepository;


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserInfoService userInfoService;

    @Test
    @DisplayName("유저 정보 생성 성공 테스트")
    public void createUserInfo() throws Exception {

        UserAccount userAccount = createUserAccount();

        String address = "서울시 노원구 상계동";
        String sex = "남자";
        int age = 99;

        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .userSeq(userAccount.getSeq())
                .address(address)
                .age(age)
                .sex(sex)
                .build();

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/userinfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInfoDTO)))
                .andDo(print())
                .andExpect(status().isOk());


        Optional<UserInfo> optionalUserInfo = userInfoRepository.findById(1L);
        UserInfo savedUserInfo = optionalUserInfo.orElse(null);

        assertNotNull(savedUserInfo != null ? savedUserInfo.getUserAccount() : null);

        UserAccount savedUserAccount = savedUserInfo.getUserAccount();

        assertEquals(savedUserAccount.getSeq(), userAccount.getSeq());

        assertNotNull(savedUserInfo);

        assertEquals(savedUserInfo.getAddress(), address);
        assertEquals(savedUserInfo.getSex(), sex);
        assertEquals(savedUserInfo.getAge(), age);

    }


    @Test
    @DisplayName("유저 정보 전체 출력 테스트 ")
    public void getUserInfoList() throws Exception {
        UserAccount userAccount = createUserAccount();

        int size = 5;

        for (int i = 0; i < size; i++) {
            userInfoRepository.save(UserInfo.builder()
                            .userAccount(userAccount)
                            .address("home " + i)
                            .age(20 + i)
                            .sex("남자").build());
        }

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/userinfo"))
                .andExpect(status().isOk())
                .andDo(print())
        ;

        List<UserInfo> allUserInfo = userInfoRepository.findAll();

        assertEquals(size, allUserInfo.size());
        assertEquals(userAccount.getSeq(), allUserInfo.get(0).getUserAccount().getSeq());
    }

    @Test
    @DisplayName("유저 정보 삭제 테스트 ")
    public void deleteUserInfo() throws Exception {
        UserAccount userAccount = createUserAccount();

        String address = "서울시 노원구 상계동";
        String sex = "남자";
        int age = 99;

        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .userSeq(userAccount.getSeq())
                .address(address)
                .age(age)
                .sex(sex)
                .build();

        userInfoService.createUserInfo(userInfoDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/userinfo/{id}", userInfoDTO.getUserSeq()))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @DisplayName("유저 정보 수정 테스트")
    public void updateUserInfo() throws Exception {
        UserAccount userAccount = createUserAccount();

        String address = "서울시 노원구 상계동";
        String sex = "남자";
        int age = 99;

        UserInfo userInfo = UserInfo.builder()
                .sex(sex)
                .userAccount(userAccount)
                .age(age)
                .address(address)
                .build();

        userInfoRepository.save(userInfo);

        Long id = 1L;

        String changedAddress = "서울시 노원구 상계동";
        String changedSex = "남자";
        int changedAge = 99;


        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .userSeq(userAccount.getSeq())
                .address(changedAddress)
                .age(changedAge)
                .sex(changedSex)
                .build();

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/userinfo/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userInfoDTO)))
                .andExpect(status().isOk())
                .andDo(print());

        Optional<UserInfo> optionalUserInfo = userInfoRepository.findById(id);


        UserInfo updatedUserInfo = optionalUserInfo.orElseThrow();

        assertEquals(updatedUserInfo.getAge(), changedAge);
        assertEquals(updatedUserInfo.getAddress(), changedAddress);
        assertEquals(updatedUserInfo.getSex(), changedSex);


    }


    private UserAccount createUserAccount() {

        UserAccount userAccount = UserAccount.builder()
                .userId("youngsoo@naver.com")
                .userName("youngsoo")
                .build();

        return userAccountRepository.save(userAccount);
    }


}