package com.graphql.api.controllers;

import com.graphql.api.security.operations.SecureOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
@Component
@CrossOrigin
@RestController
public class ApiController {

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(method = RequestMethod.POST, path = "/graphql")
    String universalController(@RequestBody String graphqlQuery) {

//        String error = validateQuery(graphqlQuery);
//        if (error != null) {
//            return error;
//        }

        RestTemplate restTemplate = new RestTemplate();
        String serviceUrl = "http://localhost:8080/graphql";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(graphqlQuery, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(serviceUrl, request, String.class);
        return response.getBody();
    }

    String buildError(String message, String classification) {
        String template = "{\"errors\":[{\"message\":\"%s\",\"locations\":null,\"extensions\":{\"classification\":\"%s\"}}]}";
        return String.format(template, message, classification);
    }

    String validateQuery(String graphqlQuery) {
        List<String> operations = new ArrayList<>();
        for (SecureOperations operation : SecureOperations.values()) {
            if (graphqlQuery.contains(operation.toString())) {
                operations.add(operation.toString());
            }
        }

        if (!operations.isEmpty()) {
            return buildError(operations + ": required authorization", "Authorization");
        }
        return null;
    }
}

