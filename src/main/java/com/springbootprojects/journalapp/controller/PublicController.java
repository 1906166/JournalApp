package com.springbootprojects.journalapp.controller;

import com.springbootprojects.journalapp.entity.User;
import com.springbootprojects.journalapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck() {
        log.info("INFO");
        log.warn("WARN");
        log.error("ERROR");
        log.trace("TRACE");
        log.debug("DEBUG");
        return "OK";
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user) {
        userService.saveNewUser(user);
    }
}
