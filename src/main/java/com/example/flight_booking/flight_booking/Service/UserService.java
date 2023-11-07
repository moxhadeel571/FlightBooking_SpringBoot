package com.example.ecommerce.ecommerce.Service;

import com.example.ecommerce.ecommerce.Entity.User;
import com.example.ecommerce.ecommerce.Entity.UserInfo;
import org.springframework.stereotype.Service;

@Service
public interface UserService {



 User saveUser(User User);

 public void removeSuccessMessage();

}