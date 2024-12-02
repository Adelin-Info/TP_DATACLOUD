package datacloud.zookeeper.barrier;

import datacloud.zookeeper.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;

import java.util.List;

import static datacloud.zookeeper.util.ConfConst.EMPTY_CONTENT;

public class BoundedBarrier {

    private final ZkClient zkc;
    private final String path;
    private final String child_prefix = "/child_";

    public BoundedBarrier(ZkClient zkc, String path, int n) throws InterruptedException, KeeperException {
        this.zkc = zkc;
        this.path = path;
        if (zkc.zk().exists(path, false) == null) {
            zkc.zk().create(path, Integer.toString(n).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    public int sizeBarrier() throws InterruptedException, KeeperException {
        return Integer.parseInt(new String(zkc.zk().getData(path, false, null)));
    }

    public synchronized void sync() {
        try {
            zkc.zk().create(path + child_prefix, EMPTY_CONTENT, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

            while (true) {
                List<String> children = zkc.zk().getChildren(path, event -> {
                    synchronized (BoundedBarrier.this) {
                        Watcher.Event.EventType type = event.getType();
                        if (type == Watcher.Event.EventType.NodeChildrenChanged) {
                            BoundedBarrier.this.notifyAll();
                        }
                    }
                });

                if (children.size() == sizeBarrier()) {
                    for (String child : children) {
                        zkc.zk().delete(path + "/" + child, -1);
                    }
                    zkc.zk().delete(path, -1);
                    break;
                }

                this.wait();
            }
        } catch (Exception e) {
            System.out.println("Failed");
        }
    }
}
