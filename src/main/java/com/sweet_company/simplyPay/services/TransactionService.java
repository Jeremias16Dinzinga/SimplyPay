package com.sweet_company.simplyPay.services;

import com.sweet_company.simplyPay.domain.transaction.TransactionEntity;
import com.sweet_company.simplyPay.domain.user.UserEntity;
import com.sweet_company.simplyPay.dto.TransactionDto;
import com.sweet_company.simplyPay.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private NotificationService notificationService;

    public TransactionEntity createTransaction(TransactionDto transactionDto) throws Exception {
        UserEntity sender = this.userService.findUserById(transactionDto.senderId());
        UserEntity receiver = this.userService.findUserById(transactionDto.receiverId());

        this.userService.transactionAccess(sender);
        this.transactionValidation(sender,transactionDto.amount(),receiver);

        boolean authorized = this.authorized(sender, transactionDto.amount());

        if (!authorized){
            throw new Exception("Transação não autorizado");
        }

        this.notificationService.sendNotification(sender,"Enviaste "+transactionDto.amount()+"€ Para "+ receiver.getFirstName()+" "+receiver.getLastName());
        this.notificationService.sendNotification(receiver,sender.getFirstName()+" "+sender.getLastName()+" enviou "+ transactionDto.amount()+"€ para ti.");

        return this.saveTransaction(transactionDto,sender,receiver);
    }
    private void transactionValidation(UserEntity sender, BigDecimal amount,UserEntity receiver)throws Exception{
        if (sender.getBalance().compareTo(amount)<0){
            throw new Exception("Saldo insuficiente");
        }
        if(amount.compareTo(BigDecimal.valueOf(0))<=0){
            throw  new Exception("Insira uma montante valido!");
        }
        if(Objects.equals(receiver.getId(), sender.getId())){
            throw new Exception("Não pode fazer o movimento de auto-envio!");
        }
    }
    private boolean authorized(UserEntity sender, BigDecimal amount) {
        ResponseEntity<Map> authorizeResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

        if (authorizeResponse.getStatusCode() == HttpStatus.OK && authorizeResponse.getBody() != null) {
            Object status = authorizeResponse.getBody().get("status");
            return "success".equalsIgnoreCase(String.valueOf(status));
        }else return false;
    }

    private TransactionEntity saveTransaction(TransactionDto transactionDto, UserEntity sender, UserEntity receiver){
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(transactionDto.amount());
        transaction.setReceiver(receiver);
        transaction.setSender(sender);
        transaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDto.amount()));
        receiver.setBalance(receiver.getBalance().add(transactionDto.amount()));

        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);
        return this.transactionRepository.save(transaction);
    }

}
