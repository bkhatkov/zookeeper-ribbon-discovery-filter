package com.bkhatkov.spring.cloud.ribbon.predicate;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class ZookeeperMetadataAwarePredicate extends AbstractZookeeperDiscoveryPredicate {

    private static final Logger LOG = LoggerFactory.getLogger(ZookeeperMetadataAwarePredicate.class);

    private List<String> headers = new ArrayList<>();

    public ZookeeperMetadataAwarePredicate(IRule rule, IClientConfig clientConfig) {
        super(rule, clientConfig);
        if (clientConfig != null) {
            String headersStr = (String)clientConfig.getProperties().getOrDefault("filteringProperties", null);
            if (headersStr != null && !headersStr.isEmpty()) {
                LOG.debug("Filter servers by the following properties: {}", headersStr);
                headers.addAll(Arrays.asList(headersStr.split(",")));
            }
        }
    }

    @Override
    protected boolean apply(ZookeeperServer server) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.
                currentRequestAttributes()).getRequest();
        Map<String, String> attrs = new HashMap<>();
        headers.forEach(header -> {
            String value = request.getHeader(header);
            if (value != null) {
                attrs.put(header, request.getHeader(header));
            }
        });

        Map<String, String> metadata = server.getInstance().getPayload().getMetadata();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Requested metadata values {}", attrs.toString());
            LOG.debug("Filtering instance of service {} with metadata values {}", server.getInstance().getAddress(), metadata.toString());
        }
        return metadata.entrySet().containsAll(attrs.entrySet());
    }
}
