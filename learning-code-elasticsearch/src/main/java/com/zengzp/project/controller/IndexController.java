package com.zengzp.project.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.*;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.IndexSettings;
import com.zengzp.project.entity.GoodsInfo;
import com.zengzp.project.util.ParseJDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/11/30 15:20
 * @description：elasticsearch创建索引控制类
 * @modified By：
 * @version: 1$
 */
@Controller
@Slf4j
public class IndexController {
    @Autowired
    private ElasticsearchClient elasticsearchClient;
    @Autowired
    private ParseJDUtil parseJDUtil;
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * 创建索引
     *
     * @param indexName 索引名称
     * @throws IOException
     */
    @PostMapping("/createIndex")
    public String createIndex(String indexName) throws IOException {
        Map<String, Property> propertyHashMap = new HashMap<>();
        //构建索引字段类型
        propertyHashMap.put("id", new Property(new IntegerNumberProperty.Builder().index(true).build()));
        propertyHashMap.put("name", new Property(new TextProperty.Builder().analyzer("ik_max_word").index(true).build()));
        propertyHashMap.put("imgUrl", new Property(new TextProperty.Builder().index(true).build()));
        propertyHashMap.put("price", new Property(new LongNumberProperty.Builder().index(true).build()));
        //构建索引
        TypeMapping typeMapping = new TypeMapping.Builder().properties(propertyHashMap).build();
        IndexSettings indexSettings = new IndexSettings.Builder().numberOfShards("1").numberOfReplicas("0").build();
        CreateIndexRequest createIndexRequest = new CreateIndexRequest.Builder().index(indexName)
                .mappings(typeMapping)
                .settings(indexSettings)
                .build();
        CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(createIndexRequest);
        log.info("create is ==" + createIndexResponse.acknowledged());
        return "success";
    }

    /**
     * 批量初始化ES数据
     */
    @PostMapping("/batchInit")
    public String batchInitEsData(String keyword,String indexName) throws IOException {
        BulkRequest.Builder br = new BulkRequest.Builder();
        List<GoodsInfo> goodsInfos = parseJDUtil.parseHTML(keyword);
        if(!CollectionUtils.isEmpty(goodsInfos)){
            goodsInfos.forEach(s->br.operations(p->p.index(idx->idx.index(indexName)
                                /*.id(String.valueOf(s.getId()))*/
                                .document(s))));
            BulkResponse bulkResponse = elasticsearchClient.bulk(br.build());
            // Log errors, if any
            if (bulkResponse.errors()) {
                log.info("Bulk had errors");
                for (BulkResponseItem item: bulkResponse.items()) {
                    if (item.error() != null) {
                        log.error(item.error().reason());
                    }
                }
            }
        }
        return "success";
    }
}
