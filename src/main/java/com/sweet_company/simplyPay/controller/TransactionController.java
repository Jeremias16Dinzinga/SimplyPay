package com.sweet_company.simplyPay.controller;

import com.sweet_company.simplyPay.domain.transaction.TransactionEntity;
import com.sweet_company.simplyPay.dto.TransactionDto;
import com.sweet_company.simplyPay.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @PostMapping
    public ResponseEntity<TransactionEntity> sendMoney(@Valid @RequestBody TransactionDto transactionDto) throws Exception{
        TransactionEntity transaction = this.transactionService.createTransaction(transactionDto);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}
