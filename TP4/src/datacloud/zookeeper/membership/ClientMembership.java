package datacloud.zookeeper.membership;

import datacloud.zookeeper.ZkClient;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;


import java.io.IOException;
import java.util.List;

public class ClientMembership extends ZkClient {
    private List<String> members;

    public ClientMembership(String name, String servers) throws IOException, KeeperException, InterruptedException {
        super(name, servers);
        members = (super.zk().getChildren("/ids", true));
    }

    public synchronized List<String> getMembers() {
        return members;
    }

    @Override
    public void process(WatchedEvent event) {
        EventType type = event.getType();
        if (type == EventType.NodeChildrenChanged) {
            try {
                synchronized (this) {
                    this.members = this.zk().getChildren("/ids", true);
                }
            } catch(Exception e) {
                System.out.println("[ClientMembership.process()] Can't get children update status");
            }
        }
    }
}