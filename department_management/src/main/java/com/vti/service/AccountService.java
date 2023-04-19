package com.vti.service;

import com.vti.enitty.Account;
import com.vti.form.AccountCreateForm;
import com.vti.form.AccountFilterForm;
import com.vti.form.AccountUpdateForm;
import com.vti.repository.IAccountRepository;
import com.vti.specification.AccountSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private IAccountRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Page<Account> findAll(Pageable pageable, AccountFilterForm form) {// Phân trang
        Specification<Account> specification = AccountSpecification.buildSpec(form);
        return repository.findAll(specification, pageable);
    }

    @Override
    public Account findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void create(AccountCreateForm accountCreateForm) {
        Account account = mapper.map(accountCreateForm, Account.class);
        String encodedPassword = encoder.encode(accountCreateForm.getPassword());
        account.setPassword(encodedPassword);
        repository.save(account);
    }

    @Override
    public void update(AccountUpdateForm accountUpdateForm) {
        Account account = mapper.map(accountUpdateForm, Account.class);
        String encodedPassword = encoder.encode(accountUpdateForm.getPassword());
        account.setPassword(encodedPassword);
        repository.save(account);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllById(List<Integer> ids) {
        repository.deleteAllById(ids);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = repository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("Tài khoản không tồn tại");
        }
        return new User(account.getUsername(),
                account.getPassword(),
                AuthorityUtils.createAuthorityList(account.getRole().toString())); // Phân quyền
    }
}
