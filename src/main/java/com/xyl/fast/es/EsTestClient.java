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
//     * ???????????????
//     */
//    static RestHighLevelClient client = new RestHighLevelClient(
//            RestClient.builder(
//                    new HttpHost("localhost", 9200, "http")));
//
//    public static void main(String[] args) throws IOException {
//
//        //????????????
//        //testCreateIndex();
//
//        //????????????
//        //testGetIndex();
//
//        //????????????
//        //testDeleteIndex();
//
//        //????????????
//        //testCreateDocument();
//
//        //????????????
//        //testUpdateDocument();
//
//        //????????????
//        //testGetDocument();
//
//        //????????????
//        //testDeleteDocument();
//
//        //????????????
//        //testBatchCreate();
//
//        //????????????
//        //testBatchDeleteDocument();
//
//        //????????????
//        testSearch();
//        System.out.println("<<========");
//        client.close();
//    }
//
//    private static void testSearch() throws IOException {
//        // ????????????????????????
//        SearchRequest request = new SearchRequest();
//        request.indices("kibana_sample_data_flights");
//        // ????????????????????????
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//
//        // ??????????????????
//        //sourceBuilder.query(QueryBuilders.matchAllQuery());
//
//        // ???????????????
//        sourceBuilder.query(QueryBuilders.termQuery("OriginCityName", "Frankfurt am Main"));
//
//        //????????????
//        sourceBuilder.from(0);
//        sourceBuilder.size(10);
//
//        //??????
//        sourceBuilder.sort("FlightTimeMin", SortOrder.ASC);
//
//        //??????????????????
//        String[] excludes = {};
//        String[] includes = {"OriginWeather", "FlightTimeMin", "dayOfWeek", "Dest"};
//        sourceBuilder.fetchSource(includes, excludes);
//
//        //bool??????
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        // ????????????
//        //boolQueryBuilder.must(QueryBuilders.matchQuery("OriginWeather", "Sunny"));
//        // ????????????
//        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("dayOfWeek", 7));
//        // ????????????
//        boolQueryBuilder.should(QueryBuilders.matchQuery("Cancelled", false));
//        sourceBuilder.query(boolQueryBuilder);
//
//        //????????????
//        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("dayOfWeek");
//        // ????????????
//        rangeQuery.gte("0");
//        // ????????????
//        rangeQuery.lte("7");
//        sourceBuilder.query(rangeQuery);
//
//        //????????????
////        FuzzyQueryBuilder fuzziness = QueryBuilders
////                .fuzzyQuery("Dest", "Zuri")
////                .fuzziness(Fuzziness.ONE);
////        sourceBuilder.query(fuzziness);
//
//        //????????????
//        //?????????????????????????????????
//        TermsQueryBuilder termsQueryBuilder =
//                QueryBuilders.termsQuery("OriginWeather","Sunny");
//        //??????????????????
//        sourceBuilder.query(termsQueryBuilder);
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.preTags("<font color='red'>");//??????????????????
//        highlightBuilder.postTags("</font>");//??????????????????
//        highlightBuilder.field("OriginWeather");//??????????????????
//        //????????????????????????
//        sourceBuilder.highlighter(highlightBuilder);
//
//        // ????????????
//        sourceBuilder.aggregation(AggregationBuilders.max("maxAge").field("dayOfWeek"));
//
//        //????????????
//        sourceBuilder.aggregation(AggregationBuilders.terms("dayOfWeek_groupby").field("dayOfWeek"));
//
//        request.source(sourceBuilder);
//        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
//
//
//
//        // ????????????
//        SearchHits hits = response.getHits();
//        System.out.println("took:" + response.getTook());
//        System.out.println("timeout:" + response.isTimedOut());
//        System.out.println("total:" + hits.getTotalHits());
//        System.out.println("MaxScore:" + hits.getMaxScore());
//        System.out.println("hits========>>");
//        for (SearchHit hit : hits) {
//            //?????????????????????????????????
//            String sourceAsString = hit.getSourceAsString();
//            System.out.println(sourceAsString);
//            //??????????????????
////            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
////            System.out.println(highlightFields);
//        }
//        System.out.println(response);
//    }
//
//    /**
//     * ????????????
//     */
//    private static void testBatchDeleteDocument() throws IOException {
//        //??????????????????????????????
//        BulkRequest request = new BulkRequest();
//        request.add(new DeleteRequest().index("user_index").id("1001"));
//        request.add(new DeleteRequest().index("user_index").id("1002"));
//        request.add(new DeleteRequest().index("user_index").id("1003"));
//        //??????????????????????????????????????????
//        BulkResponse responses = client.bulk(request, RequestOptions.DEFAULT);
//        //??????????????????
//        System.out.println("took:" + responses.getTook());
//        System.out.println("items:" + responses.getItems());
//    }
//
//    /**
//     * ????????????
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
//        //??????????????????????????????????????????
//        BulkResponse responses = client.bulk(request, RequestOptions.DEFAULT);
//        //??????????????????
//        System.out.println("took:" + responses.getTook());
//        System.out.println("items:" + responses.getItems());
//    }
//
//    /**
//     * ????????????
//     */
//    private static void testDeleteDocument() throws IOException {
//        DeleteRequest request = new DeleteRequest();
//        request.index("user_index").id("1009");
//        //2.??????????????????????????????????????????
//        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
//        //????????????
//        System.out.println(response.toString());
//    }
//
//    /**
//     * ????????????
//     */
//    private static void testGetDocument() throws IOException {
//        GetRequest getRequest = new GetRequest();
//        getRequest.index("user_index").id("1009");
//        //2.??????????????????????????????????????????
//        GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
//        //3.??????????????????
//        System.out.println("_index:" + response.getIndex());
//        System.out.println("_type:" + response.getType());
//        System.out.println("_id:" + response.getId());
//        System.out.println("source:" + response.getSourceAsString());
//    }
//
//    /**
//     * ????????????
//     */
//    private static void testUpdateDocument() throws IOException {
//        UpdateRequest updateRequest = new UpdateRequest();
//        // ??????????????????
//        updateRequest.index("user_index").id("1009");
//        // ???????????????????????????????????????
//        updateRequest.doc(XContentType.JSON, "realName", "??????");
//        // ??????????????????????????????????????????
//        UpdateResponse response = client.update(updateRequest, RequestOptions.DEFAULT);
//        System.out.println("_index:" + response.getIndex());
//        System.out.println("_id:" + response.getId());
//        System.out.println("_result:" + response.getResult());
//    }
//
//    /**
//     * ????????????
//     */
//    private static void testCreateDocument() throws IOException {
//        IndexRequest indexRequest = new IndexRequest();
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(new User(1009, "test_doc", "???"));
//        indexRequest.source(json, XContentType.JSON);
//        indexRequest.index("user_index").id("1009");
//        client.index(indexRequest, RequestOptions.DEFAULT);
//        // ??????????????????????????????????????????
//        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
//        //3.??????????????????
//        System.out.println("_index:" + response.getIndex());
//        System.out.println("_id:" + response.getId());
//        System.out.println("_result:" + response.getResult());
//    }
//
//    /**
//     * ????????????
//     */
//    private static void testDeleteIndex() throws IOException {
//        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("user_index");
//        AcknowledgedResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
//        System.out.println("???????????? ??? " + deleteIndexResponse.isAcknowledged());
//    }
//
//    /**
//     * ????????????
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
//     * ????????????
//     */
//    private static void testCreateIndex() throws IOException {
//        //????????????
//        CreateIndexRequest user = new CreateIndexRequest("user_index");
//        CreateIndexResponse createIndexResponse = client.indices().create(user, RequestOptions.DEFAULT);
//        boolean acknowledged = createIndexResponse.isAcknowledged();
//        // ????????????
//        System.out.println("???????????? = " + acknowledged);
//    }
//}
