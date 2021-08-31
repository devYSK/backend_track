package com.ys.demo.service;

import com.ys.demo.dto.UserAccountResponseDTO;
import com.ys.demo.dto.UserInfoDTO;
import com.ys.demo.dto.UserInfoResponseDTO;
import com.ys.demo.model.UserAccount;
import com.ys.demo.model.UserInfo;
import com.ys.demo.repository.UserAccountRepository;
import com.ys.demo.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional
    public List<UserInfoResponseDTO> findAllInfos() {
        return userInfoRepository.findAll()
                .stream()
                .map(userInfo -> new UserInfoResponseDTO(userInfo, new UserAccountResponseDTO(userInfo.getUserAccount())))
                .collect(Collectors.toList());
    }


    @Transactional
    public UserInfoResponseDTO findUser(Long id) {
        UserInfo userInfo = userInfoRepository.findById(id).orElseThrow(() -> new IllegalStateException("해당 id의 유저가 없습니다. id : " + id));

        return new UserInfoResponseDTO(userInfo, new UserAccountResponseDTO(userInfo.getUserAccount()));
    }


    @Transactional
    public UserInfoResponseDTO updateUserInfo(Long id, UserInfoDTO userInfoDTO) {
        UserInfo userInfo = userInfoRepository.findById(id).orElseThrow(() -> new IllegalStateException("해당 id의 유저가 없습니다 id : " + id));

        userInfo.setAddress(userInfoDTO.getAddress());
        userInfo.setAge(userInfo.getAge());
        userInfo.setSex(userInfoDTO.getSex());

        return new UserInfoResponseDTO(userInfo, new UserAccountResponseDTO(userInfo.getUserAccount()));
    }

    @Transactional
    public Long deleteUserInfo(Long id) {

        userInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당 id의 userinfo가 없습니다. id : " + id));

        userInfoRepository.deleteById(id);

        return id;
    }


    @Transactional
    public UserInfoResponseDTO createUserInfo(UserInfoDTO userInfoDTO) {

        Long userId = userInfoDTO.getUserSeq();

        UserAccount userAccount = userAccountRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("해당 id의 user가 없습니다. id : " + userId));


        UserInfo userInfo = userInfoDTO.toEntity();

        userInfo.setUserAccount(userAccount);

        UserInfo savedUserInfo = userInfoRepository.save(userInfo);

        UserInfoResponseDTO userInfoResponseDTO = new UserInfoResponseDTO(savedUserInfo, new UserAccountResponseDTO(userAccount));

        return userInfoResponseDTO;
    }
}
