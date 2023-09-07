package com.example.demo.bank.application;

import com.example.demo.bank.application.request.SaveMoneyServiceRequest;
import com.example.demo.bank.application.response.SaveMoneyServiceResponse;
import com.example.demo.global.exception.BadRequestException;
import com.example.demo.global.exception.BadRequestExceptionCode;
import org.springframework.stereotype.Service;

import java.util.prefs.BackingStoreException;

@Service
public class SaveMoneyService {
    public SaveMoneyServiceResponse save(SaveMoneyServiceRequest serviceRequest) {
        Long moneyValue = serviceRequest.getMoneyValue();
        if(moneyValue<0){
            throw new BadRequestException(BadRequestExceptionCode.NEGATIVE_MONEY);
        }
        return null;
    }
}
