package com.suez.acoustic_logger.controller;

import com.suez.acoustic_logger.auth.AuthProvider;
import com.suez.acoustic_logger.entity.User;
import com.suez.acoustic_logger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class UserRestController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public HashMap<String, String> login(@RequestBody User user){
        System.out.println(user.toString());
        Optional<User> optional = userRepository.findById(user.getUsername());
        HashMap<String, String> response = new HashMap<>();
         optional.ifPresent(user1 -> {
             if(user1.getPassword().equals(user.getPassword())){
                 user1.setToken(AuthProvider.createJWT(user1));
                 userRepository.save(user1);
                 response.put("token", user1.getToken());
             } else {
                 response.put("error","Invalid username/password");
             }
         });
         return response;
    }
}
