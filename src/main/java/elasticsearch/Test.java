package elasticsearch;

import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;


import com.alibaba.fastjson.JSONObject;


public class Test {
    public static void main(String[] args) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = sdf.parse("2018-01-25 10:59:06.195");
        try {
            //设置集群名称
            Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
            //创建client
            JSONObject json = new JSONObject();
            json.put("user", "hahaaaaaaaa");
            json.put("title", "Java Engineer");
            json.put("desc", "web 开发");
            json.put("another", "java开发");
            json.put("@timestamp", date);
            TransportClient client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.129.185"), 9300));
            IndexResponse Iresponse = client.prepareIndex("test-index-3", "htest").setSource(json, XContentType.JSON).get();
            //搜索数据
//            GetResponse response = client.prepareGet("logstash-entityinfo", "info", "1").execute().actionGet();
            //输出结果
            System.out.println(Iresponse.getIndex());
//            System.out.println(response.getSourceAsString());
            //关闭client
            client.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}