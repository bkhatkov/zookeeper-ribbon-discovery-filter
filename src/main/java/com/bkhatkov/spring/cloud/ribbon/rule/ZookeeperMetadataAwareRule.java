package com.bkhatkov.spring.cloud.ribbon.rule;

import com.bkhatkov.spring.cloud.ribbon.predicate.AbstractZookeeperDiscoveryPredicate;
import com.bkhatkov.spring.cloud.ribbon.predicate.ZookeeperMetadataAwarePredicate;
import com.netflix.loadbalancer.CompositePredicate;

public class ZookeeperMetadataAwareRule extends AbstractZookeeperDiscoveryRule {

    private CompositePredicate predicate;

    public ZookeeperMetadataAwareRule() {
        this(new ZookeeperMetadataAwarePredicate());
    }

    public ZookeeperMetadataAwareRule(AbstractZookeeperDiscoveryPredicate predicate) {
        super(predicate);
    }
}
