package com.gtw.webflux.client.handler;

import com.gtw.webflux.client.handler.bean.MethodInfo;
import com.gtw.webflux.client.handler.bean.ServerInfo;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientRestHandler implements RestHandler {

    private WebClient webClient;

    /**
     * 初始化WebClient
     */
    @Override
    public void init(ServerInfo serverInfo) {
        webClient = WebClient.create(serverInfo.getUrl());
    }

    /**
     * 处理请求
     */
    @Override
    public Object invoke(MethodInfo methodInfo) {
        Object result;

        WebClient.RequestBodySpec requestBody = this.webClient
                .method(methodInfo.getMethod())
                .uri(methodInfo.getUrl(), methodInfo.getParams())
                .accept(MediaType.APPLICATION_JSON);

        WebClient.ResponseSpec responseSpec;
        if(methodInfo.getBody() != null) {
            responseSpec = requestBody.body(methodInfo.getBody(), methodInfo.getBodyElementType()).retrieve();
        }else {
            responseSpec = requestBody.retrieve();
        }

        // 异常处理
        responseSpec.onStatus(status -> status.value() == 404, response -> Mono.just(new RuntimeException("资源未找到")));

        if(methodInfo.isFluxFlag()) {
            result = responseSpec.bodyToFlux(methodInfo.getReturnElementType());
        }else {
            result = responseSpec.bodyToMono(methodInfo.getReturnElementType());
        }

        return result;
    }
}
