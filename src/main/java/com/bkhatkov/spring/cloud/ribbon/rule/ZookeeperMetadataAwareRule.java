package com.bkhatkov.spring.cloud.ribbon.rule;

import com.bkhatkov.spring.cloud.ribbon.predicate.AbstractZookeeperDiscoveryPredicate;
import com.bkhatkov.spring.cloud.ribbon.predicate.ZookeeperMetadataAwarePredicate;
import com.netflix.client.config.IClientConfig;

public class ZookeeperMetadataAwareRule extends AbstractZookeeperDiscoveryRule {

    @Override
    protected AbstractZookeeperDiscoveryPredicate createZookeeperPredicate(IClientConfig clientConfig) {
        return new ZookeeperMetadataAwarePredicate(this, clientConfig);
    }
}
