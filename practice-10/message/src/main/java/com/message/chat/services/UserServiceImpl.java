package com.message.chat.services;

import com.message.chat.dto.request.LoginRequest;
import com.message.chat.dto.request.RefreshTokenRequest;
import com.message.chat.dto.request.RegisterRequest;
import com.message.chat.dto.response.AccessTokenResponse;
import com.message.chat.dto.response.JwtResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import messenger.Chat;
import messenger.UserServiceGrpc;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


@GrpcService
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    private JwtResponse getJwtToken(LoginRequest loginRequest) {
        return new RestTemplate().exchange(
                "http://nginx:80/api/token/",
                HttpMethod.POST,
                new HttpEntity<>(loginRequest),
                JwtResponse.class
        ).getBody();
    }

    private void login(StreamObserver<Chat.JwtResponse> responseObserver, LoginRequest loginRequest) {
        try {
            JwtResponse jwtResponse = getJwtToken(loginRequest);

            if (jwtResponse != null) {
                responseObserver.onNext(Chat.JwtResponse.newBuilder()
                        .setAccess(jwtResponse.getAccess())
                        .setRefresh(jwtResponse.getRefresh())
                        .build()
                );
            }
        } catch (HttpStatusCodeException exception) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription(exception.getMessage())
                    .asRuntimeException()
            );
        }

        responseObserver.onCompleted();
    }


    @Override
    public void registerUser(Chat.UserRequest request, StreamObserver<Chat.JwtResponse> responseObserver) {

        RegisterRequest registerDto = new RegisterRequest(
                request.getUsername(),
                request.getPassword()
        );

        try {
            new RestTemplate().exchange(
                    "http://nginx:80/api/register/",
                    HttpMethod.POST,
                    new HttpEntity<>(registerDto),
                    Object.class
            );
        } catch (HttpStatusCodeException exception) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription(exception.getMessage())
                    .asRuntimeException()
            );
        }

        login(responseObserver, registerDto);
    }

    @Override
    public void loginUser(Chat.UserRequest request, StreamObserver<Chat.JwtResponse> responseObserver) {
        LoginRequest loginRequest = new LoginRequest(
                request.getUsername(),
                request.getPassword()
        );

        login(responseObserver, loginRequest);
    }

    @Override
    public void updateAccessToken(Chat.RefreshToken request, StreamObserver<Chat.AccessToken> responseObserver) {
        try {
            AccessTokenResponse accessTokenResponse = new RestTemplate().exchange(
                    "http://nginx:80/api/token/refresh/",
                    HttpMethod.POST,
                    new HttpEntity<>(new RefreshTokenRequest(request.getRefresh())),
                    AccessTokenResponse.class
            ).getBody();
            if (accessTokenResponse != null) {
                responseObserver.onNext(Chat.AccessToken.newBuilder()
                        .setAccess(accessTokenResponse.getAccess())
                        .build()
                );
            }

        } catch (HttpStatusCodeException exception) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription(exception.getMessage())
                    .asRuntimeException()
            );
        }

        responseObserver.onCompleted();
    }
}
