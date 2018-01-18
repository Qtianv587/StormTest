package spouts;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

/**
 * Created by qitian on 2018/1/11.
 */
public class WordReader implements IRichSpout {
    private SpoutOutputCollector collector;
    private FileReader fileReader;
    private boolean completed = false;
    private TopologyContext context;

    /**
     * 我们将创建一个文件并维持一个collector对象
     */
    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        try {
            this.context = context;
            this.fileReader = new FileReader(conf.get("wordsFile").toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error reading file [" + conf.get("wordFile") + "]");
        }
        this.collector = collector;
    }

    @Override
    public void close() {

    }

    /**
     * 这个方法做的惟一一件事情就是分发文件中的文本行
     */
    @Override
    /**
     * 这个方法会不断的被调用，直到整个文件都读完了，我们将等待并返回。
     */
    public void nextTuple() {
        if (completed) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }

        String str;
        BufferedReader reader = new BufferedReader(fileReader);
        try {
            //读所有文本行
            while ((str = reader.readLine()) != null) {
                /**
                 * 按行发布一个新值
                 */
                this.collector.emit(new Values(str), str);
            }
        } catch (Exception e) {
            throw new RuntimeException("error reading tuple", e);
        } finally {
            completed = true;
        }
    }

    @Override
    public void ack(Object msgId) {
        System.out.println("OK:" + msgId);
    }

    @Override
    public void fail(Object msgId) {
        System.out.println("FAIL:" + msgId);
    }

    @Override
    public boolean isDistributed() {
        return false;
    }

    /**
     * 声明输入域"word"
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("line"));
    }
}
