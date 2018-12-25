package com.bkhatkov.spring.cloud.ribbon.rule;

import com.bkhatkov.spring.cloud.ribbon.predicate.AbstractZookeeperDiscoveryPredicate;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.AvailabilityPredicate;
import com.netflix.loadbalancer.CompositePredicate;
import com.netflix.loadbalancer.PredicateBasedRule;
import org.springframework.util.Assert;

public abstract class AbstractZookeeperDiscoveryRule extends PredicateBasedRule {
    private final CompositePredicate predicate;

    public AbstractZookeeperDiscoveryRule(AbstractZookeeperDiscoveryPredicate zookeeperDiscoveryPredicate) {
        Assert.notNull(zookeeperDiscoveryPredicate, "Parameter 'zookeeperDiscoveryPredicate' can't be null");
        this.predicate = createCompositePredicate(zookeeperDiscoveryPredicate, new AvailabilityPredicate(this, null));
    }

    @Override
    public AbstractServerPredicate getPredicate() {
        return predicate;
    }

    private CompositePredicate createCompositePredicate(AbstractZookeeperDiscoveryPredicate zookeeperDiscoveryPredicate, AvailabilityPredicate availabilityPredicate) {
        return CompositePredicate.withPredicates(zookeeperDiscoveryPredicate, availabilityPredicate)
                .build();
    }
}
