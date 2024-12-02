package datacloud.zookeeper.barrier;

import datacloud.zookeeper.ZkClient;
import org.apache.zookeeper.*;
import static datacloud.zookeeper.util.ConfConst.*;
import org.apache.zookeeper.Watcher.Event.EventType;

public class SimpleBarrier{

    private final ZkClient zkc;
    private final String path;

    public SimpleBarrier(ZkClient zkc, String path) throws InterruptedException, KeeperException {
        this.zkc = zkc;
        this.path = path;
        if (zkc.zk().exists(path, false) == null) {
            zkc.zk().create(path, EMPTY_CONTENT, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    public synchronized void sync(){
        try {
            while ((zkc.zk().exists(path, event -> {
                synchronized (SimpleBarrier.this) {
                    EventType type = event.getType();
                    if (type == EventType.NodeDeleted) {
                        SimpleBarrier.this.notifyAll();
                    }
                }
            }) != null)) {
                this.wait();
            }
        } catch (Exception e) {
            System.out.println("Failed");
        }
    }
}
