package datacloud.zookeeper.pubsub;

import datacloud.zookeeper.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;

import java.io.IOException;
import java.util.*;

import static datacloud.zookeeper.util.ConfConst.EMPTY_CONTENT;

public class Subscriber extends ZkClient {
    private final Map<String, List<String>> subs;
    private final Map<String, Integer> index;

    public Subscriber(String name, String servers) throws IOException, InterruptedException, KeeperException {
        super(name, servers);
        subs =  new HashMap<>();
        index = new HashMap<>();
    }

    public void subscribe(String topic) throws InterruptedException, KeeperException {
        if (super.zk().exists("/" + topic, false) ==  null) {
            super.zk().create("/" + topic, EMPTY_CONTENT, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        subs.putIfAbsent(topic, new ArrayList<>());
        index.putIfAbsent(topic, super.zk().getChildren("/" + topic, true).size());
    }

    public List<String> received(String topic) {
        return subs.getOrDefault(topic, Collections.emptyList());
    }


    @Override
    public void process(WatchedEvent event) {
        Event.EventType type = event.getType();
        if (type == Event.EventType.NodeChildrenChanged) {
            String topic = event.getPath();
            if (subs.containsKey(topic.substring(1))) {
                try {
                    List<String> children = super.zk().getChildren(topic, true);
                    Collections.sort(children);
                    int idx = index.get(topic.substring(1));
                    for (int i = idx; i < children.size(); i++) {
                        String message = new String(super.zk().getData(topic + "/" + children.get(i), false, null));
                        if (!subs.get(topic.substring(1)).contains(message)) {
                            subs.get(topic.substring(1)).add(message);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Failed");
                }
            }
        }
    }
}
