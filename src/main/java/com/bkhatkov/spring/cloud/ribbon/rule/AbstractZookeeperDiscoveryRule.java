package com.bkhatkov.spring.cloud.ribbon.rule;

import com.bkhatkov.spring.cloud.ribbon.predicate.AbstractZookeeperDiscoveryPredicate;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.AvailabilityPredicate;
import com.netflix.loadbalancer.CompositePredicate;
import com.netflix.loadbalancer.PredicateBasedRule;
import org.springframework.util.Assert;

public abstract class AbstractZookeeperDiscoveryRule extends PredicateBasedRule {
    private AbstractServerPredicate predicate;

    public AbstractZookeeperDiscoveryRule() {
        this.predicate = createCompositePredicate(createZookeeperPredicate(null), new AvailabilityPredicate(this, null));
    }

    @Override
    public AbstractServerPredicate getPredicate() {
        return predicate;
    }

    private AbstractServerPredicate createCompositePredicate(AbstractZookeeperDiscoveryPredicate discoveryEnabledPredicate, AvailabilityPredicate availabilityPredicate) {
        Assert.notNull(discoveryEnabledPredicate, "Parameter 'discoveryEnabledPredicate' can't be null");
        return CompositePredicate.withPredicates(discoveryEnabledPredicate, availabilityPredicate)
                .build();
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        if (clientConfig != null) {
            this.predicate = createCompositePredicate(createZookeeperPredicate(clientConfig), new AvailabilityPredicate(this, clientConfig));
        }
        super.initWithNiwsConfig(clientConfig);
    }

    abstract protected AbstractZookeeperDiscoveryPredicate createZookeeperPredicate(IClientConfig clientConfig);
}
