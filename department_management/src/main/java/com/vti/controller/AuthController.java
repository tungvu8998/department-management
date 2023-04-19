package com.vti.controller;

import com.vti.dto.ProfileDTO;
import com.vti.enitty.Account;
import com.vti.form.AuthCreateForm;
import com.vti.form.AuthUpdateForm;
import com.vti.repository.IAccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private IAccountRepository  repository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder encoder;
    @GetMapping("/login")
//    Khi có Principal : người dùng đăng nhập thành công
    public ProfileDTO login(Principal principal) {
        String username = principal.getName();
        Account account = repository.findByUsername(username);
        return mapper.map(account,ProfileDTO.class);
    }

    @PostMapping("/register")
    private void register(@RequestBody AuthCreateForm form) {
        Account account = mapper.map(form,Account.class);
        String encodedPassword = encoder.encode(form.getPassword());
        account.setPassword(encodedPassword);
        repository.save(account);
    }
    @PutMapping("/change")
    private void changePassword(@RequestBody AuthUpdateForm form) {
        Account account = repository.findByUsername(form.getUsername());;
        String encodedPassword = encoder.encode(form.getPassword());
        account.setPassword(encodedPassword);
        repository.save(account);
    }

}
