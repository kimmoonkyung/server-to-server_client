package com.example.client.controller;

import com.example.client.dto.Req;
import com.example.client.dto.UserResponse;
import com.example.client.service.RestTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ApiController {

    private final RestTemplateService restTemplateService;

    @GetMapping("/hello")
    public UserResponse getHello(){
        return restTemplateService.hello();
    }

    @GetMapping("")
    public UserResponse post(){
        return restTemplateService.post();
    }

    @GetMapping("/exchange")
    public UserResponse exchange(){
        return restTemplateService.exchange();
    }

    @GetMapping("/genericExchange")
    public Req<UserResponse> genericExchange(){
        return restTemplateService.genericExchange();
    }

}
