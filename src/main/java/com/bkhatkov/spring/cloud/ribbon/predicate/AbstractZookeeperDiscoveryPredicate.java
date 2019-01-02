package com.bkhatkov.spring.cloud.ribbon.predicate;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PredicateKey;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;

import javax.annotation.Nullable;

public abstract class AbstractZookeeperDiscoveryPredicate extends AbstractServerPredicate {
    public AbstractZookeeperDiscoveryPredicate() {
        super();
    }

    public AbstractZookeeperDiscoveryPredicate(IRule rule, IClientConfig clientConfig) {
        super(rule, clientConfig);
    }

    @Override
    public boolean apply(@Nullable PredicateKey predicateKey) {
        return predicateKey != null && predicateKey.getServer() instanceof ZookeeperServer && apply((ZookeeperServer) predicateKey.getServer());
    }

    protected abstract boolean apply(ZookeeperServer server);
}
