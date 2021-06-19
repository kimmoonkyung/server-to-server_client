package com.example.client.service;

import com.example.client.dto.Req;
import com.example.client.dto.UserResponse;
import com.example.client.dto.UserResquest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
public class RestTemplateService {

    // http://localhost:9090/api/server/hello 호출
    // response 받아옴

    public UserResponse hello(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/hello")
                .queryParam("name", "aaaa")
                .queryParam("age", 99)
                .encode()
                .build()
                .toUri();

        log.info("uri: {}", uri.toString());

        RestTemplate restTemplate = new RestTemplate();
        // String result = restTemplate.getForObject(uri, String.class);
        // getForObject => server의 return값 그대로, "hello server" 문자열이 return 된다.

//        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
//        log.info("result.getStatusCode: {}", result.getStatusCode());
//        log.info("result.getBody: {}", result.getBody());
        // getForEntity

        ResponseEntity<UserResponse> result = restTemplate.getForEntity(uri, UserResponse.class);

        return result.getBody();

    }

    public UserResponse post() {
        // http://localhost:9090/api/server/user/{userId}/name/{userName}

        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}/test/{test}")
                .encode()
                .build()
                .expand(100, "norotoo", "nochoongnae")
                .toUri();
        log.info("post.. uri: {}", uri);

        // http body -> object -> object mapper -> json -> rest template -> http body json
        UserResquest userResquest = new UserResquest();
        userResquest.setName("norotoo");
        userResquest.setAge(15);
        userResquest.setTest("hi");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> response = restTemplate.postForEntity(uri, userResquest, UserResponse.class);

        log.info("resonse getStatusCode: {}", response.getStatusCode());
        log.info("resonse getHeaders: {}", response.getHeaders());
        log.info("resonse getBody: {}", response.getBody());

        return response.getBody();

    }

    public UserResponse exchange() {

        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand(100, "norotoo")
                .toUri();
        log.info("post.. uri: {}", uri);

        UserResquest req = new UserResquest();
        req.setName("norotoo");
        req.setAge(15);

        RequestEntity<UserResquest> requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorization", "abcd")
                .header("custom-header", "fxxk")
                .body(req);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> responseEntity = restTemplate.exchange(requestEntity, UserResponse.class);

        return responseEntity.getBody();

    }

    public Req<UserResponse> genericExchange() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}/reqTest/{reqTest}")
                .encode()
                .build()
                .expand(100, "norotoo", "genericExchange")
                .toUri();
        log.info("post.. uri: {}", uri);

        UserResquest userResquest = new UserResquest();
        userResquest.setName("norotoo");
        userResquest.setAge(15);

        Req<UserResquest> req = new Req<UserResquest>();
        req.setHeader(
                new Req.Header()
        );

        req.setResponseBody(
                userResquest
        );


        RequestEntity<Req<UserResquest>> requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorization", "genericExchangeHeader")
                .header("custom-header", "fxxk")
                .body(req);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Req<UserResponse>> response
                = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Req<UserResponse>>(){});

        return response.getBody();
    }

}
