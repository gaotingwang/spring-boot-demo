package com.gtw.es.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gtw.es.model.Page;
import com.gtw.es.model.Product;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService1 implements ProductService {
    private final ObjectMapper mapper = new ObjectMapper();

    private static final String PRODUCT_INDEX = "ting_test";
    private static final String PRODUCT_TYPE = "product";

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public Product findById(String id) throws IOException {
        final GetResponse response = restHighLevelClient.get(new GetRequest(PRODUCT_INDEX, PRODUCT_TYPE, id));
        final Product product = mapper.readValue(response.getSourceAsBytes(), Product.class);
        product.setId(response.getId());
        return product;
    }

    @Override
    public Page<Product> search(String query) throws IOException {
        return createPage(createSearchRequest(query, 0, 10), query);
    }

    @Override
    public Page<Product> next(Page page) throws IOException {
        int from = page.getFrom() + page.getSize();
        final SearchRequest request =
            createSearchRequest(page.getInput(), from, page.getSize());

        return createPage(request, page.getInput());
    }

    @Override
    public void save(Product product) throws IOException {
        save(Collections.singletonList(product));
    }

    public void save(List<Product> products) throws IOException {
        BulkRequest request = new BulkRequest();
        for (Product product : products) {
            request.add(indexRequest(product));
        }
        final BulkResponse response = restHighLevelClient.bulk(request);
        for (int i = 0; i < products.size(); i++) {
            products.get(i).setId(response.getItems()[i].getId());
        }
    }

    private SearchRequest createSearchRequest(String input, int from, int size) {
        final SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder
            .from(from)
            .size(size)
            .query(QueryBuilders.multiMatchQuery(input, "name", "description"));
        return new SearchRequest(PRODUCT_INDEX).source(searchSourceBuilder);
    }

    private Page<Product> createPage(SearchRequest searchRequest, String input) throws IOException {
        final SearchResponse response = restHighLevelClient.search(searchRequest);
        if (response.getHits().getTotalHits() == 0) {
            return Page.EMPTY;
        }
        if (response.getHits().getHits().length == 0) {
            return Page.EMPTY;
        }

        List<Product> products = new ArrayList<>(response.getHits().getHits().length);

        for (SearchHit hit : response.getHits().getHits()) {
            final Product product = mapper.readValue(hit.getSourceAsString(), Product.class);
            product.setId(hit.getId());
            products.add(product);
        }

        final SearchSourceBuilder source = searchRequest.source();
        return new Page(products, input, source.from(), source.size());
    }

    private IndexRequest indexRequest(Product product) throws IOException {
        final byte[] bytes = mapper.writeValueAsBytes(product);
        final IndexRequest request = new IndexRequest(PRODUCT_INDEX, PRODUCT_TYPE);
        if (product.getId() != null) {
            request.id(product.getId());
        }
        request.source(bytes, XContentType.JSON);
        return request;
    }
}
