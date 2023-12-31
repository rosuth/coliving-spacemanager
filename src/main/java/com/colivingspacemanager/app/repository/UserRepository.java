package com.colivingspacemanager.app.repository;// UserRepository.java

import com.colivingspacemanager.app.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);

    User findByEmail(String email);

    User findByCode(String code);

}
