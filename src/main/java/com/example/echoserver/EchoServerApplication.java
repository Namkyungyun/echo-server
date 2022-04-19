package com.example.echoserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@Slf4j
public class EchoServerApplication {

    @Autowired
    private HttpServletRequest request;

    public static void main(String[] args) {

        SpringApplication.run(EchoServerApplication.class, args);
    }


    @RequestMapping(value = "/**", consumes = MediaType.ALL_VALUE,
            method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Map<String, Object>> echoBack(@RequestBody(required = false) String rawBody) throws IOException {

        Map<String, String> headers = new HashMap<>();
        for (String headerName : Collections.list(request.getHeaderNames())) {
            headers.put(headerName, request.getHeader(headerName));
        }

        Map<String, Object> responseMap = new HashMap<String,Object>();
        responseMap.put("protocol", request.getProtocol());
        responseMap.put("method", request.getMethod());
        responseMap.put("headers", headers);
        responseMap.put("cookies", request.getCookies());
        responseMap.put("parameters", request.getParameterMap());
        responseMap.put("path", request.getServletPath());
        responseMap.put("body", rawBody != null ? rawBody : null);
        log.info("REQUEST: " + request.getParameterMap());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMap);
    }

}
