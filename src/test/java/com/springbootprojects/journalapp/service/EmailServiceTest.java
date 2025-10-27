package com.springbootprojects.journalapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @Test
    public void testSendEmail(){
        emailService.sendMail(
                "saurabhkumarpatilnitp@gmail.com",
                "Java Testing",
                "Hell, this is test mail.");
    }
}
