package com.gtw.es.controller;

import java.io.IOException;

import com.gtw.es.model.Product;
import com.gtw.es.service.ProductService1;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ESController {

    @Autowired
    private RestHighLevelClient elasticsearchClient;
    @Autowired
    private ProductService1 productService;

    @GetMapping("/test")
    public boolean test() throws IOException {
        GetIndexRequest request = new GetIndexRequest();
        request.indices("ting_test");
        return elasticsearchClient.indices().exists(request);
    }

    @GetMapping("/save")
    public boolean save() {
        Product product = new Product();
        product.setId("3");
        product.setName("book");
        product.setDescription("测试22");
        product.setPrice(32.5);
        product.setColor("red");
        product.setStockAvailable(1);
        try {
            productService.save(product);
        } catch (Exception e) {
            log.error("insert error !", e);
            return false;
        }
        return true;
    }

}
