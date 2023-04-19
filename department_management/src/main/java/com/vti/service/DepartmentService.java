package com.vti.service;

import com.vti.enitty.Account;
import com.vti.enitty.Department;
import com.vti.form.DepartmentCreateForm;
import com.vti.form.DepartmentFilterForm;
import com.vti.form.DepartmentUpdateForm;
import com.vti.repository.IDepartmentRepository;
import com.vti.specification.DepartmentSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService implements IDepartmentService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private IDepartmentRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Page<Department> findAll(Pageable pageable, DepartmentFilterForm form) {
        Specification<Department> specification = DepartmentSpecification.buildSpec(form);
        return repository.findAll(specification,pageable);
    }
    @Override
    public Department findById(int id) {
        return repository.findById(id).orElse(null);
    }
    @Override // Mark từ 1 entity sang department
    @Transactional
    public void create(DepartmentCreateForm form) {
        Department department = mapper.map(form, Department.class);
        List<Account> accounts = department.getAccounts();
        if (accounts != null) {
            for (Account account : accounts) {
                account.setDepartment(department);
                String encodedPassword = encoder.encode(account.getPassword());
                account.setPassword(encodedPassword);
            }
        }
        repository.save(department);
    }
    @Override
    public void update(DepartmentUpdateForm departmentUpdateForm) {
        Department department = mapper.map(departmentUpdateForm,Department.class);
        repository.save(department);
    }
    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllById(List<Integer> ids) {
        repository.deleteAllById(ids);
    }
}