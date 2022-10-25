package com.gtw.webflux.client.handler.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodInfo {
    private String url;
    private HttpMethod method;
    private Map<String, Object> params;
    private Mono<?> body;
    private Class<?> bodyElementType;
    private boolean fluxFlag;
    private Class<?> returnElementType;
}
