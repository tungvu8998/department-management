package com.vti.enitty;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_name",length = 50,nullable = false,unique = true,updatable = false)
    private String username;
    @Column(name = "password",length = 72,nullable = false)
    private String password;
    @Column(name = "first_name",length = 50,nullable = false)
    private String firstName;
    @Column(name = "last_name",length = 50,nullable = false)
    private String lastName;
    @Formula(value = "concat(first_name, ' ', last_name)")
    private String fullName;
    @Column(name = "created_at",nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "role",nullable = false,length = 8)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;
    public enum Role {
        ADMIN,EMPLOYEE,MANAGER
    }
}
