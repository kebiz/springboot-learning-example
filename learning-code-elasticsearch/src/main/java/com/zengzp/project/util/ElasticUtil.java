package com.zengzp.project.util;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.*;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;

import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zengzp.project.entity.UserTest;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import javax.swing.text.Highlighter;
import javax.xml.soap.Text;
import java.io.IOException;
import java.lang.annotation.Native;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/11/23 16:22
 * @description：elasticsearch工具类
 * @modified By：
 * @version: 1$
 */
public class ElasticUtil {
/*    @Autowired
    private ElasticsearchClient elasticsearchClient;*/

    public void createIndex() throws IOException {
        RestClient restClient=RestClient.builder(new HttpHost("localhost",9200)).build();
        RestClientTransport restClientTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient elasticsearchClient = new ElasticsearchClient(restClientTransport);
        CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(c -> c.index("newapi"));
        System.out.println(createIndexResponse.acknowledged());
        restClientTransport.close();
        restClient.close();
    }

    public void  qurey() throws IOException {
        RestClient restClient=RestClient.builder(new HttpHost("localhost",9200)).build();
        RestClientTransport restClientTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient elasticsearchClient = new ElasticsearchClient(restClientTransport);
        GetIndexResponse getIndexResponse = elasticsearchClient.indices().get(c -> c.index("newapi"));
        System.out.println("result===="+String.join(",",getIndexResponse.result().keySet()));
        restClientTransport.close();
        restClient.close();
    }
    public void create() throws IOException {
        RestClient restClient=RestClient.builder(new HttpHost("localhost",9200)).build();
        RestClientTransport restClientTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient elasticsearchClient = new ElasticsearchClient(restClientTransport);
        UserTest test=new UserTest();
        test.setId(1003L);
        test.setAge(60L);
        test.setSex("男");
        test.setName("测试用户3");
        CreateResponse createResponse = elasticsearchClient.create(s -> s.index("newapi").id(test.getId().toString()).document(test));
        System.out.println("newapi===create:"+createResponse.result());
        restClientTransport.close();
        restClient.close();
    }
    public void get() throws IOException {
        RestClient restClient=RestClient.builder(new HttpHost("localhost",9200)).build();
        RestClientTransport restClientTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient elasticsearchClient = new ElasticsearchClient(restClientTransport);
        GetResponse<UserTest> getResponse = elasticsearchClient.get(s -> s.index("newapi").id("1000"), UserTest.class);
        System.out.println("newapi===get:"+getResponse.source());
        restClientTransport.close();
        restClient.close();
    }
    public void update() throws IOException {
        RestClient restClient=RestClient.builder(new HttpHost("localhost",9200)).build();
        RestClientTransport restClientTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient elasticsearchClient = new ElasticsearchClient(restClientTransport);
        Map<String,String> userMap=new HashMap<String,String>(2);
        userMap.put("age","18");
        userMap.put("userName","用户测试2");
        UpdateResponse<UserTest> updateResponse = elasticsearchClient.update(s -> s.index("newapi").id("1000").doc(userMap), UserTest.class);
        System.out.println("newapi===update:"+updateResponse.result());
        restClientTransport.close();
        restClient.close();
    }
    public void search() throws IOException {
        RestClient restClient=RestClient.builder(new HttpHost("localhost",9200)).build();
        RestClientTransport restClientTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient elasticsearchClient = new ElasticsearchClient(restClientTransport);
        String searchText = "张三";
        int age = 18;

// Search by product name
        Query byName = MatchQuery.of(m -> m
                .field("name")
                .query(searchText)
        )._toQuery();

// Search by max price
        Query byMaxPrice = RangeQuery.of(r -> r
                .field("age")
                .gte(JsonData.of(age))
        )._toQuery();
        //构建高亮查询
        HighlightField.Builder HighlightFieldBuilder = new HighlightField.Builder();
        HighlightField highlightField = HighlightFieldBuilder.build();
        //构建高亮字段
        Highlight.Builder highlightBuilder = new Highlight.Builder();
        Highlight  highlight = highlightBuilder.fields("name", highlightField).preTags("<span style='color:red'>").postTags("</span>").build();
// Combine name and price queries to search the product index
        SearchResponse<UserTest> response = elasticsearchClient.search(s -> s
                        .index("es_db2")
                        .query(q -> q
                                .bool(b -> b
                                        .must(byName)
                                        .must(byMaxPrice)
                                )
                        ).highlight(highlight),
                UserTest.class
        );

        List<Hit<UserTest>> hits = response.hits().hits();
        for (Hit<UserTest> hit: hits) {
            UserTest userTest = hit.source();
            Map<String, List<String>> highlightMap = hit.highlight();
            //name高亮处理
            List<String> stringList = highlightMap.get("name");
            userTest.setName(stringList.get(0)); //用高亮的内容替换原内容
            System.out.println("userTest=======" + userTest);
        }
        restClientTransport.close();
        restClient.close();
    }
}
