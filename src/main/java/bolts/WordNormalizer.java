package bolts;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by qitian on 2018/1/11.
 */
public class WordNormalizer implements IRichBolt {

    private OutputCollector collector;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector collector) {
        this.collector = collector;
    }

    /**
     * *bolt*从单词文件接收到文本行，并标准化它。
     * 文本行会全部转化成小写，并切分它，从中得到所有单词。
     */
    @Override
    public void execute(Tuple input) {
        String sentence = input.getString(0);
        String[] words = sentence.split(" ");
        for (String word: words) {
            word = word.trim();
            if(!word.isEmpty()){
                word = word.toLowerCase();
                //发布这个单词
                List a = new ArrayList();
                a.add(input);
                collector.emit(a, new Values(word));
            }
        }

        //对元组做出应答
        collector.ack(input);
    }

    @Override
    public void cleanup() {

    }

    /**
     * 这个*bolt*只会发布“word”域
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
}
