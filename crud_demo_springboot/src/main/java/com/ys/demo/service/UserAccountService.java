package com.ys.demo.service;

import com.ys.demo.dto.UserAccountDTO;
import com.ys.demo.model.UserAccount;
import com.ys.demo.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;


    @Transactional
    public Long createUserAccount(UserAccountDTO userAccountDTO) {
        return userAccountRepository.save(userAccountDTO.toEntity()).getSeq();
    }

    @Transactional
    public List<UserAccount> findAll() {
        return userAccountRepository.findAll();
    }

    @Transactional
    public UserAccount findUser(Long id) {
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(id);
        return optionalUserAccount.orElse(null);
    }

    @Transactional
    public UserAccount updateUser(Long id, UserAccountDTO userAccountDTO) {
        UserAccount userAccount = userAccountRepository.findById(id).orElse(null);

        if (userAccount == null)
            return null;

//                .orElseThrow(() -> new IllegalStateException("해당 id에 해당하는 user가 없습니다. id : " + id));

        userAccount.setUserName(userAccountDTO.getUserName());
        userAccount.setUserId(userAccountDTO.getUserId());
        return userAccount;
    }

    @Transactional
    public Long deleteUserAccount(Long id) {

        if (userAccountRepository.findById(id).isPresent()) {
            userAccountRepository.deleteById(id);
            return id;
        }

        return -1L;
    }

}
