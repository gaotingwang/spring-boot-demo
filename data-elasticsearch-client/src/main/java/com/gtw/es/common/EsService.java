package com.gtw.es.common;

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EsService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public void save(String index, String type, String id, String data) {
        IndexRequest request = this.indexRequest(index, type, id, data);
        IndexResponse response = null;
        try {
            response = restHighLevelClient.index(request);
        } catch (Exception e) {
            log.error("EsService save exception", e);
        }
        if (response != null) {
            String reIndex = response.getIndex();
            log.info("EsService插入一条记录, data: " + data + " ,reIndex: " + reIndex);
        }
    }

    public void save(String index, String type, JSONArray dataArray) {
        BulkRequest request = new BulkRequest();
        for (int i = 0; i < dataArray.size(); i++) {
            request.add(indexRequest(index, type, null, dataArray.getJSONObject(i).toJSONString()));
        }
        try {
            restHighLevelClient.bulk(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject findById(String index, String type, String id) throws IOException {
        GetResponse response = restHighLevelClient.get(new GetRequest(index, type, id));
        return JSON.parseObject(response.getSourceAsString());
    }

    public SearchResponse search(String index, String type, QueryBuilder query) {
        SearchRequest request = new SearchRequest(index).types(type);

        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder
                .query(query)
                .size(10000);
            request.source(searchSourceBuilder);
            return restHighLevelClient.search(request);
        } catch (Exception e) {
            log.error("EsService search exception", e);
            return null;
        }
    }

    private IndexRequest indexRequest(String index, String type, String id, String data) {
        final IndexRequest request = new IndexRequest(index, type);
        if (id != null && !"".equals(id.trim())) {
            request.id(id);
        }
        request.source(data, XContentType.JSON);
        return request;
    }
}
