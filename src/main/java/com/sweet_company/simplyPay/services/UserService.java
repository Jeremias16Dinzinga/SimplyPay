package com.sweet_company.simplyPay.services;

import com.sweet_company.simplyPay.domain.user.TypeUser;
import com.sweet_company.simplyPay.domain.user.UserEntity;
import com.sweet_company.simplyPay.dto.UserDto;
import com.sweet_company.simplyPay.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void transactionValidation(UserEntity sender, BigDecimal amount) throws Exception {
        if(sender.getTypeUser()== TypeUser.MERCHANT){
            throw new Exception("O tipode usuário não autorisado a realizar transação");
        }
        if (sender.getBalance().compareTo(amount)<0){
            throw new Exception("Saldo insuficiente");
        }
    }
    public UserEntity findUserById(Long id) throws Exception {
        return this.userRepository.findById(id).orElseThrow(()-> new Exception("Usuário não existe"));
    }
    public void saveUser(UserEntity user){
        this.userRepository.save(user);
    }

    public UserEntity createUser(UserDto userDto){
        UserEntity newUser = new UserEntity(userDto);
        saveUser(newUser);
        return newUser;
    }
    public List<UserEntity> findAllUsers(){
        return this.userRepository.findAll();
    }
}
