//package com.xyl.fast.es;
//
//import java.io.IOException;
//import java.util.Map;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.xyl.fast.entity.User;
//import org.apache.http.HttpHost;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.delete.DeleteRequest;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.get.GetRequest;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.support.master.AcknowledgedResponse;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.action.update.UpdateResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.CreateIndexRequest;
//import org.elasticsearch.client.indices.CreateIndexResponse;
//import org.elasticsearch.client.indices.GetIndexRequest;
//import org.elasticsearch.client.indices.GetIndexResponse;
//import org.elasticsearch.common.unit.Fuzziness;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.FuzzyQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.query.RangeQueryBuilder;
//import org.elasticsearch.index.query.TermsQueryBuilder;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.elasticsearch.search.sort.SortOrder;
//
///**
// * @author xiangyanlin
// * @date 2021/11/3
// */
//public class EsTestClient {
//
//    /**
//     * 客户端对象
//     */
//    static RestHighLevelClient client = new RestHighLevelClient(
//            RestClient.builder(
//                    new HttpHost("localhost", 9200, "http")));
//
//    public static void main(String[] args) throws IOException {
//
//        //创建索引
//        //testCreateIndex();
//
//        //查看索引
//        //testGetIndex();
//
//        //删除索引
//        //testDeleteIndex();
//
//        //创建文档
//        //testCreateDocument();
//
//        //修改文档
//        //testUpdateDocument();
//
//        //查询文档
//        //testGetDocument();
//
//        //删除文档
//        //testDeleteDocument();
//
//        //批量新增
//        //testBatchCreate();
//
//        //批量删除
//        //testBatchDeleteDocument();
//
//        //高级查询
//        testSearch();
//        System.out.println("<<========");
//        client.close();
//    }
//
//    private static void testSearch() throws IOException {
//        // 创建搜索请求对象
//        SearchRequest request = new SearchRequest();
//        request.indices("kibana_sample_data_flights");
//        // 构建查询的请求体
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//
//        // 查询所有数据
//        //sourceBuilder.query(QueryBuilders.matchAllQuery());
//
//        // 关键字查询
//        sourceBuilder.query(QueryBuilders.termQuery("OriginCityName", "Frankfurt am Main"));
//
//        //分页查询
//        sourceBuilder.from(0);
//        sourceBuilder.size(10);
//
//        //排序
//        sourceBuilder.sort("FlightTimeMin", SortOrder.ASC);
//
//        //查询字段过滤
//        String[] excludes = {};
//        String[] includes = {"OriginWeather", "FlightTimeMin", "dayOfWeek", "Dest"};
//        sourceBuilder.fetchSource(includes, excludes);
//
//        //bool查询
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        // 必须包含
//        //boolQueryBuilder.must(QueryBuilders.matchQuery("OriginWeather", "Sunny"));
//        // 一定不含
//        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("dayOfWeek", 7));
//        // 可能包含
//        boolQueryBuilder.should(QueryBuilders.matchQuery("Cancelled", false));
//        sourceBuilder.query(boolQueryBuilder);
//
//        //范围查询
//        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("dayOfWeek");
//        // 大于等于
//        rangeQuery.gte("0");
//        // 小于等于
//        rangeQuery.lte("7");
//        sourceBuilder.query(rangeQuery);
//
//        //模糊查询
////        FuzzyQueryBuilder fuzziness = QueryBuilders
////                .fuzzyQuery("Dest", "Zuri")
////                .fuzziness(Fuzziness.ONE);
////        sourceBuilder.query(fuzziness);
//
//        //高亮查询
//        //构建查询方式：高亮查询
//        TermsQueryBuilder termsQueryBuilder =
//                QueryBuilders.termsQuery("OriginWeather","Sunny");
//        //设置查询方式
//        sourceBuilder.query(termsQueryBuilder);
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.preTags("<font color='red'>");//设置标签前缀
//        highlightBuilder.postTags("</font>");//设置标签后缀
//        highlightBuilder.field("OriginWeather");//设置高亮字段
//        //设置高亮构建对象
//        sourceBuilder.highlighter(highlightBuilder);
//
//        // 聚合查询
//        sourceBuilder.aggregation(AggregationBuilders.max("maxAge").field("dayOfWeek"));
//
//        //分组统计
//        sourceBuilder.aggregation(AggregationBuilders.terms("dayOfWeek_groupby").field("dayOfWeek"));
//
//        request.source(sourceBuilder);
//        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
//
//
//
//        // 查询匹配
//        SearchHits hits = response.getHits();
//        System.out.println("took:" + response.getTook());
//        System.out.println("timeout:" + response.isTimedOut());
//        System.out.println("total:" + hits.getTotalHits());
//        System.out.println("MaxScore:" + hits.getMaxScore());
//        System.out.println("hits========>>");
//        for (SearchHit hit : hits) {
//            //输出每条查询的结果信息
//            String sourceAsString = hit.getSourceAsString();
//            System.out.println(sourceAsString);
//            //打印高亮结果
////            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
////            System.out.println(highlightFields);
//        }
//        System.out.println(response);
//    }
//
//    /**
//     * 批量删除
//     */
//    private static void testBatchDeleteDocument() throws IOException {
//        //创建批量删除请求对象
//        BulkRequest request = new BulkRequest();
//        request.add(new DeleteRequest().index("user_index").id("1001"));
//        request.add(new DeleteRequest().index("user_index").id("1002"));
//        request.add(new DeleteRequest().index("user_index").id("1003"));
//        //客户端发送请求，获取响应对象
//        BulkResponse responses = client.bulk(request, RequestOptions.DEFAULT);
//        //打印结果信息
//        System.out.println("took:" + responses.getTook());
//        System.out.println("items:" + responses.getItems());
//    }
//
//    /**
//     * 批量新增
//     */
//    private static void testBatchCreate() throws IOException {
//        BulkRequest request = new BulkRequest();
//        request.add(new
//                IndexRequest().index("user_index").id("1001").source(XContentType.JSON, "name",
//                "zhangsan"));
//        request.add(new
//                IndexRequest().index("user_index").id("1002").source(XContentType.JSON, "name",
//                "lisi"));
//        request.add(new
//                IndexRequest().index("user_index").id("1003").source(XContentType.JSON, "name",
//                "wangwu"));
//        //客户端发送请求，获取响应对象
//        BulkResponse responses = client.bulk(request, RequestOptions.DEFAULT);
//        //打印结果信息
//        System.out.println("took:" + responses.getTook());
//        System.out.println("items:" + responses.getItems());
//    }
//
//    /**
//     * 删除文档
//     */
//    private static void testDeleteDocument() throws IOException {
//        DeleteRequest request = new DeleteRequest();
//        request.index("user_index").id("1009");
//        //2.客户端发送请求，获取响应对象
//        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
//        //打印信息
//        System.out.println(response.toString());
//    }
//
//    /**
//     * 查询文档
//     */
//    private static void testGetDocument() throws IOException {
//        GetRequest getRequest = new GetRequest();
//        getRequest.index("user_index").id("1009");
//        //2.客户端发送请求，获取响应对象
//        GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
//        //3.打印结果信息
//        System.out.println("_index:" + response.getIndex());
//        System.out.println("_type:" + response.getType());
//        System.out.println("_id:" + response.getId());
//        System.out.println("source:" + response.getSourceAsString());
//    }
//
//    /**
//     * 修改文档
//     */
//    private static void testUpdateDocument() throws IOException {
//        UpdateRequest updateRequest = new UpdateRequest();
//        // 配置修改参数
//        updateRequest.index("user_index").id("1009");
//        // 设置请求体，对数据进行修改
//        updateRequest.doc(XContentType.JSON, "realName", "张三");
//        // 客户端发送请求，获取响应对象
//        UpdateResponse response = client.update(updateRequest, RequestOptions.DEFAULT);
//        System.out.println("_index:" + response.getIndex());
//        System.out.println("_id:" + response.getId());
//        System.out.println("_result:" + response.getResult());
//    }
//
//    /**
//     * 创建文档
//     */
//    private static void testCreateDocument() throws IOException {
//        IndexRequest indexRequest = new IndexRequest();
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(new User(1009, "test_doc", "男"));
//        indexRequest.source(json, XContentType.JSON);
//        indexRequest.index("user_index").id("1009");
//        client.index(indexRequest, RequestOptions.DEFAULT);
//        // 客户端发送请求，获取响应对象
//        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
//        //3.打印结果信息
//        System.out.println("_index:" + response.getIndex());
//        System.out.println("_id:" + response.getId());
//        System.out.println("_result:" + response.getResult());
//    }
//
//    /**
//     * 删除索引
//     */
//    private static void testDeleteIndex() throws IOException {
//        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("user_index");
//        AcknowledgedResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
//        System.out.println("操作结果 ： " + deleteIndexResponse.isAcknowledged());
//    }
//
//    /**
//     * 查看索引
//     */
//    private static void testGetIndex() throws IOException {
//        GetIndexRequest userIndex = new GetIndexRequest("user_index");
//        GetIndexResponse getIndexResponse = client.indices().get(userIndex, RequestOptions.DEFAULT);
//        System.out.println("aliases:" + getIndexResponse.getAliases());
//        System.out.println("mappings:" + getIndexResponse.getMappings());
//        System.out.println("settings:" + getIndexResponse.getSettings());
//    }
//
//    /**
//     * 创建索引
//     */
//    private static void testCreateIndex() throws IOException {
//        //创建索引
//        CreateIndexRequest user = new CreateIndexRequest("user_index");
//        CreateIndexResponse createIndexResponse = client.indices().create(user, RequestOptions.DEFAULT);
//        boolean acknowledged = createIndexResponse.isAcknowledged();
//        // 响应状态
//        System.out.println("操作状态 = " + acknowledged);
//    }
//}
