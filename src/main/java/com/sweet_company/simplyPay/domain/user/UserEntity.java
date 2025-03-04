package com.sweet_company.simplyPay.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private  String lastName;
    @Column(unique = true)
    private String document;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String password;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;
}
