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
        this.userService.transactionValidation(sender,transactionDto.amount());
        boolean authorized = authorized(sender, transactionDto.amount());
        if (!authorized){
            throw new Exception("Transação não autorizado");
        }
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(transactionDto.amount());
        transaction.setReceiver(receiver);
        transaction.setSender(sender);
        transaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDto.amount()));
        receiver.setBalance(receiver.getBalance().add(transactionDto.amount()));

        transactionRepository.save(transaction);
        userService.saveUser(sender);
        userService.saveUser(receiver);

        notificationService.sendNotification(sender,"Enviaste "+transactionDto.amount()+"€ Para "+ receiver.getFirstName()+" "+receiver.getLastName());
        notificationService.sendNotification(receiver,sender.getFirstName()+" "+sender.getLastName()+" enviou "+ transactionDto.amount()+"€ para ti.");

        return transaction;
    }
    private boolean authorized(UserEntity sender, BigDecimal amount) {
        ResponseEntity<Map> authorizeResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

        if (authorizeResponse.getStatusCode() == HttpStatus.OK && authorizeResponse.getBody() != null) {
            Object status = authorizeResponse.getBody().get("status");
            return "success".equalsIgnoreCase(String.valueOf(status));
        }else return false;
    }

}
