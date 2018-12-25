**Zookeeper Discovery - Ribbon Rule Filtering**

Example for Feign Client

**Server application.yml**

`spring:
  application:
    name: zookeeper-backend-service-demo
  cloud:
    zookeeper:
      discovery:
        metadata:
          version: 1.0
          tenant: default`
          
**Client application.yml**

`zookeeper-backend-service-demo:
  ribbon:
    NFLoadBalancerRuleClassName: com.bkhatkov.spring.cloud.ribbon.rule.ZookeeperMetadataAwareRule`
    
**Client code (canary example)**
Version and Tenant attributes are expected to be present in request's header (e.g. injected by ingress gateway)


`Map<String, String> attrs = new HashMap<>();
attrs.put("version",  ((ServletRequestAttributes) RequestContextHolder.
        currentRequestAttributes()).getRequest().getHeader("version"));
attrs.put("tenant",  ((ServletRequestAttributes) RequestContextHolder.
        currentRequestAttributes()).getRequest().getHeader("tenant"));
RequestContextHolder.currentRequestAttributes().setAttribute("filteringAttributes", attrs, 0);`
