package com.example.api.service;

import com.example.api.model.AutismRegister;
import com.example.api.repository.AutismRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class AutismRegisterService {
    @Autowired
    private AutismRegisterRepository autismRegisterRepository;

    public AutismRegister registerAutismRegister(AutismRegister autismRegister) {
        autismRegister.setRegisterDate(Date.from(Instant.now()));
        return autismRegisterRepository.save(autismRegister);
    }
}
