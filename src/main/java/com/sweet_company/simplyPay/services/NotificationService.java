package com.sweet_company.simplyPay.services;

import com.sweet_company.simplyPay.domain.user.UserEntity;
import com.sweet_company.simplyPay.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(UserEntity user,String message) throws Exception {
        //String email = user.getEmail();
        //NotificationDto notificationRequest = new NotificationDto(email,message);

        /**
        ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify",notificationRequest,String.class);

        if(!(notificationResponse.getStatusCode()== HttpStatus.OK)){
            System.out.println("Erro com serviço de notificação!");
            throw  new Exception("Serviço de notificação Indisponível");
        }
        ***/
        System.out.println(message);
    }
}
