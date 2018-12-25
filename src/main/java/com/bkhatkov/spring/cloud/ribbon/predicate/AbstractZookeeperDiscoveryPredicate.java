package com.bkhatkov.spring.cloud.ribbon.predicate;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateKey;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;

import javax.annotation.Nullable;

public abstract class AbstractZookeeperDiscoveryPredicate extends AbstractServerPredicate {
    @Override
    public boolean apply(@Nullable PredicateKey predicateKey) {
        return predicateKey != null
                && predicateKey.getServer() instanceof ZookeeperServer
                && apply((ZookeeperServer) predicateKey.getServer());
    }

    protected abstract boolean apply(ZookeeperServer server);
}
