package com.springbootprojects.journalapp.repository;

import com.springbootprojects.journalapp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserRepositoryImplTest {
    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    public void getUserForSATest() {
        List<User> userForSA = userRepository.getUserForSA();
        for (User user : userForSA) {
            System.out.println(user.getUserName());
        }
    }
}
