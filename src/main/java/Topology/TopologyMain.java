package Topology;

import bolts.WordCounter;
import bolts.WordNormalizer;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import spouts.WordReader;


public class TopologyMain {
    public static void main(String[] args) throws InterruptedException {
        //定义拓扑
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word-reader", new WordReader());
        builder.setBolt("word-normalizer", new WordNormalizer()).shuffleGrouping("word-reader");
        builder.setBolt("word-counter", new WordCounter(),2).fieldsGrouping("word-normalizer", new Fields("word"));

        //配置
        Config conf = new Config();
        String path = "words.txt";
        conf.put("wordsFile", args[0]);
        System.out.println("args[0]----------");
        System.out.println(args[0]);
        System.out.println("args[0]----------");
//        conf.put("wordsFile", path);
        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
        conf.setDebug(false);

        //运行拓扑
        System.out.println("-------111111111");

        LocalCluster cluster = new LocalCluster();
        System.out.println("-------2222222222");
        cluster.submitTopology("Getting-Started-Topologie", conf, builder.createTopology());
        System.out.println("-------3333333333");
        Thread.sleep(1000);
        System.out.println("-------44444444444");
        cluster.shutdown();
    }
}
