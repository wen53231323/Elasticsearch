import com.alibaba.fastjson.JSON;
import com.wen.elasticsearch.ElasticSearchApplication;
import com.wen.elasticsearch.entity.Hotel;
import com.wen.elasticsearch.entity.HotelDoc;
import com.wen.elasticsearch.service.HotelService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest(classes = ElasticSearchApplication.class)
class DocumentTest {

    private RestHighLevelClient client;

    /**
     * junit测试的执行执行顺序：@Before—>@Test—>@After
     * 初始化客户端
     */
    @BeforeEach
    void setUp() {
        this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.10.100:9200")
        ));
    }

    @Autowired
    private HotelService hotelService;

    /**
     * 新增文档
     */
    @Test
    void testAddDocument() throws IOException {
        //（1）查询数据库hotel数据
        Hotel hotel = hotelService.getById(36934L);

        System.out.println(hotel);
        //（2）转换为HotelDoc对象
        HotelDoc hotelDoc = new HotelDoc(hotel);

        //（3）转为JSON字符串
        String json = JSON.toJSONString(hotelDoc);

        //（4）获取id，新增文档 POST /索引库名/_doc/文档id
        IndexRequest request = new IndexRequest("hotel").id(hotelDoc.getId().toString());

        //（5）准备请求参数DSL，其实就是文档的JSON字符串
        request.source(json, XContentType.JSON);

        //（6）发送请求
        client.index(request, RequestOptions.DEFAULT);
    }

    /**
     * 查询文档
     */
    @Test
    void testGetDocumentById() throws IOException {
        //（1）准备Request      // GET /hotel/_doc/{id}
        GetRequest request = new GetRequest("hotel", "36934");

        //（2）发送请求
        GetResponse response = client.get(request, RequestOptions.DEFAULT);

        //（3）解析响应结果
        String json = response.getSourceAsString();

        HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
        System.out.println("hotelDoc = " + hotelDoc);
    }

    /**
     * 删除文档
     */
    @Test
    void testDeleteDocumentById() throws IOException {
        //（1）准备Request      // DELETE /hotel/_doc/{id}
        DeleteRequest request = new DeleteRequest("hotel", "36934");

        //（2）发送请求
        client.delete(request, RequestOptions.DEFAULT);
    }

    /**
     * 增量修改：修改指定字段值
     */
    @Test
    void testUpdateById() throws IOException {
        //（1）准备Request
        UpdateRequest request = new UpdateRequest("hotel", "36934");

        //（2）准备要修改的参数
        request.doc(
                "price", "870"
        );

        //（3）发送请求
        client.update(request, RequestOptions.DEFAULT);
    }

    /**
     * 批量导入
     */
    @Test
    void testBulkRequest() throws IOException {
        //（1）查询所有的酒店数据
        List<Hotel> list = hotelService.list();

        //（2）准备Request
        BulkRequest request = new BulkRequest();

        //（3）准备参数
        for (Hotel hotel : list) {
            // 转为HotelDoc
            HotelDoc hotelDoc = new HotelDoc(hotel);
            // 转json
            String json = JSON.toJSONString(hotelDoc);
            // 添加请求
            request.add(new IndexRequest("hotel").id(hotel.getId().toString()).source(json, XContentType.JSON));
        }

        //（4）发送请求
        client.bulk(request, RequestOptions.DEFAULT);
    }

    /**
     * 关闭客户端
     */
    @AfterEach
    void tearDown() throws IOException {
        client.close();
    }

}
