package com.zengzp.project.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.HistogramBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Highlight;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.json.JsonData;
import com.zengzp.project.contant.IndexContant;
import com.zengzp.project.entity.GoodsInfo;
import com.zengzp.project.entity.UserTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：zengzhipeng
 * @date ：Created in 2022/11/30 15:31
 * @description：elasticsearch业务类
 * @modified By：
 * @version: 1$
 */
@Service
@Slf4j
public class SearchService {
    @Autowired
    private ElasticsearchClient elasticsearchClient;
    /**
     * 搜索业务类
     * @param paramMap 搜索传递参数
     * @return
     */
    public Map<String,Object> search(Map<String,Object> paramMap) throws IOException {
        List<GoodsInfo> goodsInfos = new ArrayList<>();
        Map<String, Object> hashMap = new HashMap<>();
        //复合查询
        BoolQuery.Builder bool = QueryBuilders.bool();
        //关键字搜索
        if(paramMap.get("keyword")!=null){
            Query KeywordQuery = MatchQuery.of(m -> m.field("name").query(String.valueOf(paramMap.get("keyword"))))._toQuery();
            bool.must(KeywordQuery);
        }
        //价格范围
        if(paramMap.get("price")!=null){
            String[] prices = paramMap.get("price").toString().split("-");
            //开始为空0
            if(!prices[0].equals("0")) {
                Query priceQuery = RangeQuery.of(r -> r.field("price")
                        .gte(JsonData.of(prices[0]))
                )._toQuery();
                bool.must(priceQuery);
            }
            if (!prices[1].equals("*")){
                Query priceQuery = RangeQuery.of(r -> r.field("price")
                        .lte(JsonData.of(prices[1]))
                )._toQuery();
                bool.must(priceQuery);
            }
        }
        //分页
        Integer pageNo = Integer.parseInt(String.valueOf(paramMap.get("pageNo"))); //页码
        Integer pageSize = 30;  //页大小
        int fromIndex = (pageNo - 1) * pageSize;    //起始记录下标
        //1.7 排序
        String sort = (String)paramMap.get("sort");    //排序字段
        String sortOrder = (String)paramMap.get("sortOrder");  //排序规则
        //构建高亮查询
        HighlightField.Builder HighlightFieldBuilder = new HighlightField.Builder();
        HighlightField highlightField = HighlightFieldBuilder.build();
        //构建高亮字段
        Highlight.Builder highlightBuilder = new Highlight.Builder();
        Highlight highlight = highlightBuilder.fields("name", highlightField)
                            .requireFieldMatch(false)
                            .preTags("<span style='color:red'>")
                            .postTags("</span>")
                            .build();

        SearchResponse<GoodsInfo> searchResponse = elasticsearchClient.search(s -> s
                        .index(IndexContant.INDEX_TB_GOODS)
                        .query(q -> q
                                .bool(bool.build()
                                )
                        )
                        .highlight(highlight)
                        .from(fromIndex)
                        .size(pageSize)
                        .aggregations("price-histogram",h->h.histogram(f->f.field("price").interval(5000.00)))
                        .sort(SortOptions.of(b->b.field(FieldSort.of(t->t.field(sort)
                        .order(SortOrder.Desc))))),
                GoodsInfo.class
        );
        HitsMetadata<GoodsInfo> hits1 = searchResponse.hits();
        long total=hits1.total().value();
        List<Hit<GoodsInfo>> hits = hits1.hits();
        StringBuffer stringBuffer=new StringBuffer();
        if(!CollectionUtils.isEmpty(hits)){
            for (Hit<GoodsInfo> hit:hits){
                stringBuffer.setLength(0);
                GoodsInfo goodsInfo = hit.source();
                Map<String, List<String>> highlight1 = hit.highlight();
                if(!CollectionUtils.isEmpty(highlight1)) {
                    List<String> name = highlight1.get("name");
                    name.forEach(s -> stringBuffer.append(s));
                    goodsInfo.setName(stringBuffer.toString());
                }
                goodsInfos.add(goodsInfo);
            }
        }
        List<HistogramBucket> buckets = searchResponse.aggregations()
                .get("price-histogram")
                .histogram()
                .buckets().array();

        for (HistogramBucket bucket: buckets) {
            log.info("There are " + bucket.docCount() +
                    " bikes under " + bucket.key());
        }
        hashMap.put("goodsInfos",goodsInfos);
        hashMap.put("totalPage",total%pageSize==0?total/pageSize:(total/pageSize)+1);
        hashMap.put("total",total);
        return hashMap;

    }
    @RabbitHandler
    @RabbitListener(queues = "elasticsearch.add.queue")
    public void receiveElasticsearchAddMessage(String msg){
        log.info("=========rececive ElasticsearchAdd msg======{}",msg);
    }
    @RabbitHandler
    @RabbitListener(queues = "elasticsearch.del.queue")
    public void receiveElasticsearchDelMessage(String msg){
        log.info("=========rececive ElasticsearchDel msg======{}",msg);
    }
}
