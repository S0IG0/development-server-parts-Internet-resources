//package com.graphql.api;
//
//
//import graphql.schema.GraphQLSchema;
//import graphql.schema.idl.RuntimeWiring;
//import graphql.schema.idl.SchemaGenerator;
//import graphql.schema.idl.SchemaParser;
//import graphql.schema.idl.TypeDefinitionRegistry;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.graphql.client.HttpGraphQlClient;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import org.springframework.graphql.data.method.annotation.QueryMapping;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//
//@Controller
//@Component
//@CrossOrigin
//@RestController
//public class GraphQLController {
////    @QueryMapping
////    JsonNode findAllCategories() throws IOException {
////        String graphqlQuery = "{\"query\": \"{ findAllCategories { id, name, } }\"}\n";
////        RestTemplate restTemplate = new RestTemplate();
////        String serviceUrl = "http://localhost:8080/graphql";
////
////        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(MediaType.APPLICATION_JSON);
////
////        HttpEntity<String> request = new HttpEntity<>(graphqlQuery, headers);
////        ResponseEntity<JsonNode> response = restTemplate.postForEntity(serviceUrl, request, JsonNode.class);
////        return response.getBody().get("data").get("findAllCategories");
////    }
//
//
////    @QueryMapping
////    List<Category> findAllCategories() {
////        String graphqlQuery = "{ findAllCategories { id, name, } }";
////        HttpGraphQlClient graphQlClient = HttpGraphQlClient.builder()
////                .url("http://localhost:8080/graphql")
////                .build();
////        Mono<List<Category>> categoryMono = graphQlClient
////                .document(graphqlQuery)
////                .retrieve("findAllCategories")
////                .toEntityList(Category.class);
////        System.out.println("Отработало findAllCategories");
////        return categoryMono.block();
////    }
////
////@QueryMapping
////Object findAllCategories() {
////    String graphqlQuery = "{ findAllCategories { id, name, } }";
////    HttpGraphQlClient graphQlClient = HttpGraphQlClient.builder()
////            .url("http://localhost:8080/graphql")
////            .build();
////    return  graphQlClient
////            .document(graphqlQuery)
////            .retrieve("findAllCategories");
////}
//
//
//    public class Category {
//        private String id;
//        private String name;
//    }
//
//    public class GraphQLResponse {
//        private Map<String, List<Category>> data;
//    }
//
//    @QueryMapping
//    public Mono<List<Category>> findAllCategories() {
//        String graphqlQuery = "{\"query\": \"{ findAllCategories { id, name } }\"}";
//        String serviceUrl = "http://localhost:8080/graphql";
//
//        return WebClient.builder().build()
//                .post()
//                .uri(serviceUrl)
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(Mono.just(graphqlQuery), String.class)
//                .retrieve()
//                .bodyToMono(GraphQLResponse.class)
//                .map(response -> response.data.get("findAllCategories"));
//    }
//
////    @RequestMapping(method = RequestMethod.POST, path = "/graphql")
////    String universalController(@RequestBody String graphqlQuery){
////        RestTemplate restTemplate = new RestTemplate();
////        String serviceUrl = "http://localhost:8080/graphql";
////
////        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(MediaType.APPLICATION_JSON);
////
////        HttpEntity<String> request = new HttpEntity<>(graphqlQuery, headers);
////        ResponseEntity<String> response = restTemplate.postForEntity(serviceUrl, request, String.class);
////        return response.getBody();
////    }
//}
