import com.wen.elasticsearch.ElasticSearchApplication;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static com.wen.elasticsearch.constants.HotelIndexConstants.MAPPING_TEMPLATE;

@SpringBootTest(classes = ElasticSearchApplication.class)
public class IndexTest {

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

    /**
     * 创建索引库
     */
    @Test
    public void testCreateHotelIndex() throws IOException {
        //（1）指定索引库名
        CreateIndexRequest request = new CreateIndexRequest("hotel");

        //（2）请求参数，MAPPING_TEMPLATE是静态常量字符串，内容是创建索引库的DSL语句
        request.source(MAPPING_TEMPLATE, XContentType.JSON);

        //（3）发起请求，进行创建索引
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    /**
     * 查询索引库
     */
    @Test
    void testExistsIndex() throws IOException {
        //（1）准备Request
        GetIndexRequest request = new GetIndexRequest("hotel");

        //（2）发送请求
        boolean isExists = client.indices().exists(request, RequestOptions.DEFAULT);

        System.out.println(isExists ? "存在" : "不存在");
    }

    /**
     * 删除索引库
     */
    @Test
    void testDeleteIndex() throws IOException {
        //（1）准备Request
        DeleteIndexRequest request = new DeleteIndexRequest("hotel");

        //（2）发送请求
        client.indices().delete(request, RequestOptions.DEFAULT);
    }

    /**
     * 关闭客户端
     */
    @AfterEach
    void tearDown() throws IOException {
        client.close();
    }

}

