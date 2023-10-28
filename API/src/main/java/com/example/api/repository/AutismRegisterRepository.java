package com.example.api.repository;

import com.example.api.model.AutismRegister;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AutismRegisterRepository extends CrudRepository<AutismRegister, Integer> {

}

