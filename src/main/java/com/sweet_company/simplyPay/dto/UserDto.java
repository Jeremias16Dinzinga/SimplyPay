package com.sweet_company.simplyPay.dto;

import com.sweet_company.simplyPay.domain.user.TypeUser;
import java.math.BigDecimal;

public record UserDto(String firstName, String lastName, String document, BigDecimal balance, String email, String password, TypeUser typeUser) {
}
