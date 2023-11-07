package com.example.ecommerce.ecommerce.Repository;

import com.example.ecommerce.ecommerce.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User ,String> {
    User findFirstByEmail(String email);


}