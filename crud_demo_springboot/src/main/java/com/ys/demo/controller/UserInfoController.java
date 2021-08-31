package com.ys.demo.controller;

import com.ys.demo.dto.UserInfoDTO;
import com.ys.demo.dto.UserInfoResponseDTO;
import com.ys.demo.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userinfo")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;


    @GetMapping
    public ResponseEntity getUserInfos() {

        return ResponseEntity.ok(userInfoService.findAllInfos());
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserInfo(@PathVariable Long id) {

        return ResponseEntity.ok(userInfoService.findUser(id));
    }

    //TODO create UserInfo API 작성
    @PostMapping
    public ResponseEntity createUserInfo(@RequestBody UserInfoDTO userInfoDTO, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        UserInfoResponseDTO userInfoResponseDTO = userInfoService.createUserInfo(userInfoDTO);

        return ResponseEntity.ok(userInfoResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUserInfo(@PathVariable Long id,
                                         @RequestBody UserInfoDTO userInfoDTO,
                                         Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }


        UserInfoResponseDTO userInfoResponseDTO = userInfoService.updateUserInfo(id, userInfoDTO);

        return ResponseEntity.ok(userInfoResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserInfo(@PathVariable Long id) {

        Long deletedId = userInfoService.deleteUserInfo(id);

        return ResponseEntity.ok(deletedId);
    }

}
