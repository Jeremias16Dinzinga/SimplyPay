package com.sweet_company.simplyPay.domain.user;

import com.sweet_company.simplyPay.dto.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private  String lastName;
    @Column(unique = true)
    @NotNull
    private String document;
    @Column(unique = true)
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;

    public UserEntity(UserDto userDto){
        this.firstName = userDto.firstName();
        this.lastName = userDto.lastName();
        this.document = userDto.document();
        this.balance = userDto.balance();
        this.email = userDto.email();
        this.password = userDto.password();
        this.typeUser = userDto.typeUser();
    }
}
