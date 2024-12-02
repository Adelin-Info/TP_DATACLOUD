package datacloud.zookeeper.pubsub;

import datacloud.zookeeper.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;

import java.io.IOException;

import static datacloud.zookeeper.util.ConfConst.EMPTY_CONTENT;

public class Publisher extends ZkClient {

    public Publisher(String name, String servers) throws IOException, InterruptedException, KeeperException {
        super(name, servers);
    }

    public void publish(String topic, String message) throws InterruptedException, KeeperException {
        if (super.zk().exists("/" + topic, false) == null) {
            super.zk().create("/" + topic, EMPTY_CONTENT, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        super.zk().create("/" + topic + "/message_", message.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
    }

    @Override
    public void process(WatchedEvent event) {
    }
}
