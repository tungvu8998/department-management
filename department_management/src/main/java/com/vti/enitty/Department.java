package com.vti.enitty;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "department")
public class Department {
    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name",nullable = false,unique = true,length = 50)
    private String name;
    @Column(name = "total_members",nullable = false)
    private int totalMembers;
    @Column(name = "type",length = 20,nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Type type;
    @Column(name = "created_at",nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updated_at",nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "department")
    private List<Account> accounts;
    public enum Type {
        DEVELOPER, TESTER, SCRUM_MASTER, PROJECT_MANAGER
    }
}
