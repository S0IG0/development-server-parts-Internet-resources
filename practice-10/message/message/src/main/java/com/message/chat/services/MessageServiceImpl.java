package com.message.chat.services;

import io.grpc.stub.StreamObserver;
import messenger.Chat;
import messenger.MessageServiceGrpc;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class MessageServiceImpl extends MessageServiceGrpc.MessageServiceImplBase {

    private final List<StreamObserver<Chat.Message>> observers = new ArrayList<>();

    @Override
    public void joinChannel(Chat.Empty request, StreamObserver<Chat.Message> responseObserver) {
        observers.add(responseObserver);
    }

    @Override
    public void sendMessage(Chat.Message request, StreamObserver<Chat.Info> responseObserver) {
        responseObserver.onNext(Chat.Info.newBuilder()
                .setStatus("success")
                .build()
        );
        responseObserver.onCompleted();
        for (StreamObserver<Chat.Message> observer : observers) {
            try {
                observer.onNext(request);
            } catch (Exception exception) {

            }
        }
    }
}
