package com.vti.specification;

import com.vti.enitty.Account;
import com.vti.form.AccountFilterForm;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class AccountSpecification {
    public static Specification<Account> buildSpec(AccountFilterForm form) {
        if (form == null) {
            return null;
        }
        //            Trả về 1 điều kiện
        return (root, query, builder) -> {
//          khoa , huy , hung -> search = u -> LIKE '%u%'
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(form.getSearch())) {
                predicates.add(builder.or(
//                                DepartmentName LIKE '%search'
//        (builder.like(root.get("department").get("name"), "%" + form.getSearch() + "%"),
//                SELECT * FROM account WHERE username LIKE '%search%'
                builder.like(root.get("username"), "%" + form.getSearch() + "%"),
                builder.like(root.get("firstName"), "%" + form.getSearch() + "%"),
                builder.like(root.get("lastName"), "%" + form.getSearch() + "%")
        ));
            }
            if (form.getMinId() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("id"), form.getMinId()));
            }
            if (form.getMaxId() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("id"), form.getMaxId()));
            }
            if (form.getRole() != null) {
                predicates.add(builder.equal(root.get("role"),form.getRole()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
//              convert list to array

        };
    }
}
