package com.example.client.controller;

import com.example.client.dto.Req;
import com.example.client.dto.UserResponse;
import com.example.client.service.RestTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search")
    public String naverSearch(@RequestParam String keyword){
        return restTemplateService.naverSearch(keyword);
    }

}
