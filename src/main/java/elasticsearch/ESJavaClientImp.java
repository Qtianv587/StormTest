//package elasticsearch;
//
//import org.elasticsearch.client.Client;
//import org.elasticsearch.index.mapper.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import java.net.InetSocketAddress;
//import java.util.Map;
//import java.util.concurrent.ExecutionException;
//
//import org.elasticsearch.action.ActionFuture;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
//import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
//import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
//import org.elasticsearch.action.delete.DeleteRequest;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.client.Client;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//
//import java.util.concurrent.ExecutionException;
//
///**
// * Created by qitian on 2018/1/22.
// */
//public class ESJavaClientImp implements ESJavaClient {
//
//    private final Logger logger = (Logger) LoggerFactory.getLogger(getClass());
//    private Client client;
//    private String serverName;//es服务器地址
//    private int port;//es服务器端口
//    private String clusterName;//es服务器名称
//    private volatile Map<String, Pair<String, String>> indexInfos;
//
//    @Override
//    public <T> void put(T object, Class<T> klass) {
//        String index = getIndex(klass);
//        String type = getType(klass);
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            String jsonStr = mapper.writeValueAsString(object);
//            ActionFuture<IndexResponse> future = client.index(new IndexRequest(index, type).source(jsonStr));
//            IndexResponse response = future.get();//此处会阻塞
//            logger.info(response.getId());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//
//    //获取注入的参数
//    public void setIndexInfos(Map<String, Pair<String, String>> indexInfos) {
//        this.indexInfos = indexInfos;
//    }
//
//    public Map<String, Pair<String, String>>  getIndexInfos(){
//        return this.indexInfos;
//    }
//
//    //获取index
//    private String getIndex(Class klass){
//        return indexInfos.get(klass.getName()).fst;
//    }
//
//    //获取type
//    private String getType(Class klass) {
//        return indexInfos.get(klass.getName()).snd;
//    }
//
//
//
//    //set,get方法
//    public Client getClient() {
//        return client;
//    }
//
//    public void setClient(Client client) {
//        this.client = client;
//    }
//
//    public String getServerName() {
//        return serverName;
//    }
//
//    public void setServerName(String serverName) {
//        this.serverName = serverName;
//    }
//
//    public int getPort() {
//        return port;
//    }
//
//    public void setPort(int port) {
//        this.port = port;
//    }
//
//    public String getClusterName() {
//        return clusterName;
//    }
//
//    public void setClusterName(String clusterName) {
//        this.clusterName = clusterName;
//    }
//}
