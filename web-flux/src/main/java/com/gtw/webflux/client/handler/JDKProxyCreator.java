package com.gtw.webflux.client.handler;

import com.gtw.webflux.client.annotation.ApiService;
import com.gtw.webflux.client.handler.bean.MethodInfo;
import com.gtw.webflux.client.handler.bean.ServerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JDKProxyCreator implements ProxyCreator{

    @Override
    public Object createProxy(Class<?> type) {
        log.info("createProxy:{}", type);
        // 根据接口得到API服务器信息
        ServerInfo serverInfo = extractServerInfo(type);
        log.info("serverInfo:{}", serverInfo);

        RestHandler restHandler = new WebClientRestHandler();
        restHandler.init(serverInfo);

        // 创建动态代理类
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{type}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                // 根据方法和参数得到调用信息
                MethodInfo methodInfo = extractMethodInfo(method, args);
                log.info("methodInfo:{}", methodInfo);

                // rest调用
                return restHandler.invoke(methodInfo);
            }
        });
    }

    private ServerInfo extractServerInfo(Class<?> type) {
        ServerInfo serverInfo = new ServerInfo();
        ApiService apiService = type.getAnnotation(ApiService.class);
        serverInfo.setUrl(apiService.value());
        return serverInfo;
    }

    private MethodInfo extractMethodInfo(Method method, Object[] args) {
        MethodInfo methodInfo = new MethodInfo();
        // 得到请求URL和方法
        Annotation[] annotations = method.getAnnotations();
        for(Annotation annotation :annotations) {
            if(annotation instanceof GetMapping) {
                GetMapping mapping = (GetMapping) annotation;
                methodInfo.setUrl(mapping.value().length == 0 ? "/" : mapping.value()[0]);
                methodInfo.setMethod(HttpMethod.GET);
            }if(annotation instanceof PostMapping) {
                PostMapping mapping = (PostMapping) annotation;
                methodInfo.setUrl(mapping.value().length == 0 ? "/" : mapping.value()[0]);
                methodInfo.setMethod(HttpMethod.POST);
            }if(annotation instanceof PutMapping) {
                PutMapping mapping = (PutMapping) annotation;
                methodInfo.setUrl(mapping.value().length == 0 ? "/" : mapping.value()[0]);
                methodInfo.setMethod(HttpMethod.PUT);
            }if(annotation instanceof DeleteMapping) {
                DeleteMapping mapping = (DeleteMapping) annotation;
                methodInfo.setUrl(mapping.value().length == 0 ? "/" : mapping.value()[0]);
                methodInfo.setMethod(HttpMethod.DELETE);
            }

            // 获取请求参数和body
            Map<String, Object> params = new HashMap<>();
            Parameter[] parameters = method.getParameters();
            for(int i=0; i < parameters.length; i++) {
                // PathVariable
                PathVariable pathVariable = parameters[i].getAnnotation(PathVariable.class);
                if(pathVariable != null) {
                    params.put("".equals(pathVariable.value()) ? parameters[i].getName() : pathVariable.value(), args[i]);
                }

                // requestBody
                RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
                if(requestBody != null) {
                    methodInfo.setBody((Mono<?>) args[i]);
                    Type[] types = ((ParameterizedType)parameters[i].getParameterizedType()).getActualTypeArguments();
                    Class<?> elementType = (Class<?>) types[0];
                    methodInfo.setBodyElementType(elementType);
                }
            }
            methodInfo.setParams(params);
        }

        // 获取返回信息
        methodInfo.setFluxFlag(method.getReturnType().isAssignableFrom(Flux.class));
        Type[] types = ((ParameterizedType)method.getGenericReturnType()).getActualTypeArguments();
        try {
            Class<?> elementType = (Class<?>) types[0];
            methodInfo.setReturnElementType(elementType);
        }catch (ClassCastException e) {
            methodInfo.setReturnElementType(Void.class);
        }

        return methodInfo;
    }

}
