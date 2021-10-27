package com.example.filesfromyou.api;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PingController {

    @GetMapping(path = "/ping")
    public String ping(){
        log.debug("Processing GET on /ping");
        return "pong";
    }
}
