package com.ys.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ys.demo.dto.UserAccountDTO;
import com.ys.demo.model.UserAccount;
import com.ys.demo.repository.UserAccountRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.AssertTrue;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserAccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    ObjectMapper objectMapper;

    @AfterEach
    public void afterEach() {
        userAccountRepository.deleteAll();
    }

    @Test
    @DisplayName("유저 생성 테스트 ")
    public void createUser() throws Exception {
        String id = "sooyoung@naver.com";
        String name = "sooyoung";

        UserAccountDTO userAccountDTO = UserAccountDTO.builder()
                .userId(id)
                .userName(name)
                .build();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userAccountDTO)))
                .andDo(print())
                .andExpect(status().isCreated());


        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(1L);

        assertTrue(optionalUserAccount.isPresent());


        UserAccount userAccount = optionalUserAccount.get();

        assertEquals(id, userAccount.getUserId());

        assertEquals(name, userAccount.getUserName());


    }

    @Test
    @DisplayName("전체 유저 조회")
    public void getUsers() throws Exception {

        int size = 10;
        initListUser(size);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts"))
                .andDo(print())
                .andExpect(status().isOk());

        List<UserAccount> findUsers = userAccountRepository.findAll();

        assertEquals(size, findUsers.size());

    }

    @Test
    @DisplayName("전체 유저 조회 했지만 데이터가 없을 떄 204 응답")
    public void getUsers_204() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("1명의 유저 조회 성공 테스트")
    public void getUser() throws Exception {
        initListUser(3);
        Long id = 2L;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @DisplayName("1명의 유저 조회. 데이터가 없을 떄 204 응답")
    public void getUser_204() throws Exception {
        initListUser(3);
        int id = 4;


        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print());

    }


    @Test
    @DisplayName("유저 업데이트 테스트")
    public void updateUser() throws Exception {
        String id = "sooyoung@naver.com";
        String name = "sooyoung";
        Long userSeq = 1L;

        UserAccount account = UserAccount.builder()
                .userId(id)
                .userName(name)
                .build();

        userAccountRepository.save(account);


        UserAccountDTO userAccountDTO = UserAccountDTO.builder()
                .userId(id)
                .userName(name)
                .build();


        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/{id}", userSeq)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(userAccountDTO)))
                .andExpect(status().isOk())
                .andDo(print())
        ;

        UserAccount findUser = userAccountRepository.findById(userSeq)
                .orElseThrow(() -> new IllegalStateException("해당 id의 유저가 없습니다"));


        assertEquals(findUser.getUserId(), id);
        assertEquals(findUser.getUserName(), name);

    }

    @Test
    @DisplayName("유저 업데이트 테스트 유저가 없을때 204 응답")
    public void updateUser_204() throws Exception {
        String id = "sooyoung@naver.com";
        String name = "sooyoung";
        Long userSeq = 1L;


        UserAccountDTO userAccountDTO = UserAccountDTO.builder()
                .userId(id)
                .userName(name)
                .build();


        mockMvc.perform(MockMvcRequestBuilders.put("/api/accounts/{id}", userSeq)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(userAccountDTO))
                )
                .andExpect(status().isNoContent())
                .andDo(print())
        ;
    }



    @Test
    @DisplayName("유저 삭제 테스트")
    public void deleteUser() throws Exception {
        UserAccount userAccount = UserAccount.builder()
                .userId("youngsoo@naver.com")
                .userName("youngsoo")
                .build();

        Long userSeq = 1L;


        userAccountRepository.save(userAccount);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/accounts/{id}", userSeq))
                .andExpect(status().isOk());

        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(userSeq);

        assertTrue(optionalUserAccount.isEmpty());

    }

    @Test
    @DisplayName("유저 삭제 테스트. 없는 유저 삭제시 204응답")
    public void deleteUser_204() throws Exception {
        Long userSeq = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/accounts/{id}", userSeq))
                .andExpect(status().isNoContent());


    }

    private void initListUser(int size) {
        for (int i = 0; i < size; i++) {
            UserAccount userAccount = UserAccount.builder()
                    .userId("ysk" + i + "@naver.com")
                    .userName("ysk" + i)
                    .build();
            userAccountRepository.save(userAccount);
        }
    }

}