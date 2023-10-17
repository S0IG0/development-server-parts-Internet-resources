package com.graphql.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import graphql.schema.DataFetchingEnvironment;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Controller
@Component
@CrossOrigin
@RestController
public class ApiController {

    final HttpServletRequest request;

    @Autowired
    public ApiController(HttpServletRequest request) {
        this.request = request;
    }

    @QueryMapping
    public Object findAllCategories(DataFetchingEnvironment environment) throws JsonProcessingException {
        return makeRequest(environment);
    }


    @QueryMapping
    public Object findCategoryById(DataFetchingEnvironment environment) throws JsonProcessingException {
        return makeRequest(environment);
    }


    public Object makeRequest(DataFetchingEnvironment environment) throws JsonProcessingException {
        JsonNode jsonNode = new ObjectMapper().readTree(request.getAttribute("query").toString());
        String query = jsonNode.get("query")
                .toString()
                .replace("\\n", "")
                .replace("\\t", " ")
                .replace("\"", "");
        try {
            query.substring(0, query.indexOf("#"));
        } catch (StringIndexOutOfBoundsException ignored) {

        }

        HttpGraphQlClient graphQlClient = HttpGraphQlClient.builder()
                .url("http://localhost:8080/graphql")
                .build();

        ParameterizedTypeReference<?> responseType = createParameterizedTypeReference();

        Mono<?> responseMono = graphQlClient
                .document(query)
                .retrieve(environment.getField().getName())
                .toEntity(responseType);

        return responseMono.block();
    }

    private ParameterizedTypeReference<?> createParameterizedTypeReference() {
        Type returnType = getClass()
                .getMethods()[0]
                .getGenericReturnType();

        if (returnType instanceof ParameterizedType parameterizedType) {
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

            if (actualTypeArguments.length > 0) {
                Type elementType = actualTypeArguments[0];
                return ParameterizedTypeReference.forType(elementType);
            }
        }

        return ParameterizedTypeReference.forType(Object.class);
    }
}