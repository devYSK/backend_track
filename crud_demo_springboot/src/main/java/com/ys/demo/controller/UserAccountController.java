package com.ys.demo.controller;


import com.ys.demo.dto.UserAccountDTO;
import com.ys.demo.model.UserAccount;
import com.ys.demo.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class UserAccountController {


    private final UserAccountService userAccountService;

    @GetMapping
    public ResponseEntity getAccounts() {

        List<UserAccount> all = userAccountService.findAll();

        return all.size() > 0 ? ResponseEntity.ok(all) : ResponseEntity.noContent().build();
    }


    @PostMapping
    public ResponseEntity createAccount(@RequestBody UserAccountDTO userAccountDTO,
                                        Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Long savedId = userAccountService.createUserAccount(userAccountDTO);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(UserAccountController.class).slash(savedId);

        URI createdUri = selfLinkBuilder.toUri();


        return ResponseEntity.created(createdUri).body(savedId);
    }

    @GetMapping("/{id}")
    public ResponseEntity getAccount(@PathVariable Long id) {

        UserAccount findUser = userAccountService.findUser(id);


        return findUser != null ? ResponseEntity.ok(findUser) : ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateAccount(@PathVariable Long id,
                                        @RequestBody UserAccountDTO userAccountDTO,
                                        Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        UserAccount updatedUser = userAccountService.updateUser(id, userAccountDTO);

        return updatedUser != null ?
                ResponseEntity.ok(updatedUser) : ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteAccount(@PathVariable Long id) {

        Long userId = userAccountService.deleteUserAccount(id);

        return userId > 0 ? ResponseEntity.ok(userId) : ResponseEntity.noContent().build();
    }

}