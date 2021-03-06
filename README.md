# Zookeeper Discovery - Ribbon Filter Rule (Metadata Based)

## Features

> Spring Cloud Zookeeper Discovery extension to filter list of servers by metadata.

## Usage

### Example - Canary Deployment

Scenario - canary routing to version "2.0" of backend service belonging to "default" tenant.

#### Server application.yml

```
spring:
  application:
    name: zookeeper-backend-service-demo
  cloud:
    zookeeper:
      discovery:
        metadata:
          version: 2.0
          tenant: default
```

#### Client application.yml

```
zookeeper-backend-service-demo:
  ribbon:
    NFLoadBalancerRuleClassName: com.bkhatkov.spring.cloud.ribbon.rule.ZookeeperMetadataAwareRule
    filteringProperties: version, tenant
```

#### Client balancing

Canary version and tenant are injected into request header (e.g. by ingress gateway).
