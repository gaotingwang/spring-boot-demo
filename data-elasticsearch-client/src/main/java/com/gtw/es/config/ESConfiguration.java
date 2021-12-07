package com.gtw.es.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESConfiguration {

    @Bean
    public RestHighLevelClient elasticsearchClient() {

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
            new UsernamePasswordCredentials("username", "password"));

        RestHighLevelClient client = new RestHighLevelClient(
            //传入RestClientBuilder
            RestClient.builder(
                new HttpHost("host", 9200)
            ).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                @Override
                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                    //这里可以设置一些参数，比如cookie存储、代理等等
                    httpClientBuilder.disableAuthCaching();
                    return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                }
            })
        );

        return client;
    }

}
