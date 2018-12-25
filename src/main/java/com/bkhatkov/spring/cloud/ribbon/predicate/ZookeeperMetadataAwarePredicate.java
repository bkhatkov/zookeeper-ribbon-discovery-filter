package com.bkhatkov.spring.cloud.ribbon.predicate;

import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class ZookeeperMetadataAwarePredicate extends AbstractZookeeperDiscoveryPredicate {

    @Override
    protected boolean apply(ZookeeperServer server) {
        RequestAttributes context = RequestContextHolder.currentRequestAttributes();
        Set<Map.Entry<String, String>> attributes
                = Collections.unmodifiableSet(((Map<String, String>) context.getAttribute("filteringAttributes", 0)).entrySet());
        Map<String, String> metadata = server.getInstance().getPayload().getMetadata();
        return metadata.entrySet().containsAll(attributes);
    }
}
