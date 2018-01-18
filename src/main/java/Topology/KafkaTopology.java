package Topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.spout.SchemeAsMultiScheme;
import bolts.ParseBolt;
import storm.kafka.*;

import java.util.HashMap;

/**
 * Created by qitian on 2018/1/15.
 */
public class KafkaTopology {
    public static int NUM_WORKERS = 1;
    public static int NUM_ACKERS = 1;
    public static int MSG_TIMEOUT = 180;
    public static int SPOUT_PARALLELISM_HINT = 1;
    public static int PARSE_BOLT_PARALLELISM_HINT = 1;

    public StormTopology buildTopology(HashMap map) {
        String zkServer = map.get("zookeeper").toString();
        System.out.println("zkServer: " + zkServer);
        final BrokerHosts zkHosts = new ZkHosts(zkServer);
        System.out.println("--------111111111----------");
        SpoutConfig kafkaConfig = new SpoutConfig(zkHosts, "test", "/test", "single-point-test");
        System.out.println("--------22222222222----------");
        kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
        System.out.println("--------333333333333----------");
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("kafkaSpout", new KafkaSpout(kafkaConfig), SPOUT_PARALLELISM_HINT);
        builder.setBolt("parseBolt", new ParseBolt(), PARSE_BOLT_PARALLELISM_HINT).shuffleGrouping("kafkaSpout");
        return builder.createTopology();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("===========start===========");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("zookeeper", "localhost:2181");
        KafkaTopology kafkaTopology = new KafkaTopology();
        StormTopology stormTopology = kafkaTopology.buildTopology(map);
        Config config = new Config();
        config.setNumWorkers(NUM_WORKERS);
        config.setNumAckers(NUM_ACKERS);
        config.setMessageTimeoutSecs(MSG_TIMEOUT);
        config.setMaxSpoutPending(5000);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("single-point-test", config, stormTopology);
        StormSubmitter.submitTopology("single-point-test", config, stormTopology);
    }
}
