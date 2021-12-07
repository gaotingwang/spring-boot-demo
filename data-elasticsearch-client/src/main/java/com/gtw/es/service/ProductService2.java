package com.gtw.es.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.gtw.es.common.EsService;
import com.gtw.es.model.Product;
import com.gtw.es.util.DateUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductService2 {

    @Autowired
    private EsService esService;

    /**
     * id查询
     * GET /product_index/product_type/1
     */
    public Product findById(String index, String type, String id) throws IOException {
        JSONObject jsonObject = esService.findById(index, type, id);
        if (jsonObject == null) {
            return null;
        }
        return JSON.parseObject(jsonObject.toJSONString(), Product.class);
    }

    /**
     * 范围查询
     * GET /product_index/product_type/_search
     * {
     *   "query": {
     *     "bool": {
     *       "must": [
     *         {
     *           "terms": {
     *             "pageId": [84082656]
     *           }
     *         },
     *         {
     *           "range": {
     *             "submitTime": {
     *               "gte": "2021-11-29 11:13:36",
     *               "lte": "2021-12-01 11:13:36"
     *             }
     *           }
     *         }
     *       ]
     *     }
     *   }
     * }
     */
    public List<Product> findByPageIdAndSubmitTime(String index, String type, List<Long> pageIds
        , Date startTime, Date endTime) {
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        query.must(QueryBuilders.termsQuery("pageId", pageIds));
        query.must(QueryBuilders.rangeQuery("submitTime").gte(DateUtils.formatDate(startTime))
            .lte(DateUtils.formatDate(endTime)));
        SearchResponse response = esService.search(index, type, query);
        if (response != null) {
            if (response.getHits().getTotalHits() == 0) {
                Collections.emptyList();
            }
            if (response.getHits().getHits().length == 0) {
                Collections.emptyList();
            }
            List<Product> products = new ArrayList<>(response.getHits().getHits().length);

            for (SearchHit hit : response.getHits().getHits()) {
                final Product product = JSON.parseObject(hit.getSourceAsString(),
                    Product.class);
                products.add(product);
            }
            return products;
        }
        return Collections.emptyList();
    }

    /**
     * 嵌套对象查询
     * GET /product_index/product_type/_search
     * {
     *   "query": {
     *     "bool": {
     *       "must": [
     *         {
     *           "range": {
     *             "submitTime": {
     *               "gte": "2021-11-29 11:13:36",
     *               "lte": "2021-12-01 11:13:36"
     *             }
     *           }
     *         },
     *         {
     *           "nested": {
     *             "path": "crossList",
     *             "query": {
     *               "bool": {
     *                 "must": [
     *                   {
     *                     "term": {
     *                       "crossList.datatype": "RoadCross"
     *                     }
     *                   },
     *                   {
     *                     "terms": {
     *                       "crossList.id": [2521876]
     *                     }
     *                   }
     *                 ]
     *               }
     *             }
     *           }
     *         }
     *       ]
     *     }
     *   }
     * }
     */
    public List<Product> findByCrossIdAndSubmitTime(String index, String type, String crossType,
        List<Long> crossIds, Date startTime, Date endTime) {
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        query.must(QueryBuilders.rangeQuery("submitTime").gte(DateUtils.formatDate(startTime))
            .lte(DateUtils.formatDate(endTime)));
        query.must(QueryBuilders.nestedQuery("crossList",
            QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("crossList.datatype", crossType))
                .must(QueryBuilders.termsQuery("crossList.id", crossIds)),
            ScoreMode.None));

        SearchResponse response = esService.search(index, type, query);
        if (response != null) {
            if (response.getHits().getTotalHits() == 0) {
                Collections.emptyList();
            }
            if (response.getHits().getHits().length == 0) {
                Collections.emptyList();
            }
            List<Product> products = new ArrayList<>(response.getHits().getHits().length);

            for (SearchHit hit : response.getHits().getHits()) {
                final Product product = JSON.parseObject(hit.getSourceAsString(),
                    Product.class);
                products.add(product);
            }
            return products;
        }
        return Collections.emptyList();
    }
}
