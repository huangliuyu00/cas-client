基于cas-client-autoconfig-support调整
=============================
原作者仓库地址：
https://github.com/Unicon/cas-client-autoconfig-support

> 修改了CasClientConfigurationProperties.java、CasClientConfiguration.java 文件，添加了ignorePattern属性，在CasClientConfiguration初始化Filter参数时，设置ignorePattern值，内容为正则表达式。


* Available optional properties

> in Spring Boot's `application.properties` or `application.yml` Example:

```bash
# 示例 /js/*|/img/*|/view/*|/css/*
cas.ignore-pattern=/api/v1/data-service-api/exchange/*
```


* 说明
参数ignorePattern值对应 AuthenticationFilter.java的init-param。

doFilter方法
```java
if (this.isRequestUrlExcluded(request)) {
   this.logger.debug("Request is ignored.");
   filterChain.doFilter(request, response);
}
```

isRequestUrlExcluded方法
```java
String requestUri = urlBuffer.toString();
return this.ignoreUrlPatternMatcherStrategyClass.matches(requestUri);
```

ignoreUrlPatternMatcherStrategyClass对应RegexUrlPatternMatcherStrategy.java

matches正则表达式匹配不拦截url
```
public boolean matches(String url) {
        return this.pattern.matcher(url).find();
}
```

















cas-client-autoconfig-support
=============================
## Usage

Library providing annotation-based configuration support for CAS Java clients. Primarily designed for super easy CASification of Spring Boot apps.

* Add the following required properties

> in Spring Boot's `application.properties` or `application.yml` Example:

```bash
   cas.server-url-prefix=https://cashost.com/cas
   cas.server-login-url=https://cashost.com/cas/login
   cas.client-host-url=https://casclient.com
```

* Annotate Spring Boot application (or any @Configuration class) with `@EnableCasClient` annotation

```java
    @SpringBootApplication
    @Controller
    @EnableCasClient
    public class MyApplication { .. }
```

> For CAS3 protocol (authentication and validation filters) - which is default if nothing is specified

```bash
   cas.validation-type=CAS3
```

> For CAS2 protocol (authentication and validation filters)

```bash
   cas.validation-type=CAS
```

> For SAML protocol (authentication and validation filters)

```bash
   cas.validation-type=SAML
```

## Available optional properties

* `cas.authentication-url-patterns`
* `cas.validation-url-patterns`
* `cas.request-wrapper-url-patterns`
* `cas.assertion-thread-local-url-patterns`
* `cas.gateway`
* `cas.use-session`
* `cas.redirect-after-validation`
* `cas.allowed-proxy-chains`
* `cas.proxy-callback-url`
* `cas.proxy-receptor-url`
* `cas.accept-any-proxy`
* `server.context-parameters.renew`

## Advanced configuration

This library does not expose ALL the CAS client configuration options via standard Spring property sources, but only most commonly used ones.
If there is a need however, to set any number of not exposed, 'exotic' properties, there is a way: just extend `CasClientConfigurerAdapter`
class in your `@EnableCasClient` annotated class and override appropriate configuration method(s) for CAS client filter(s) in question.
For example:

```java
    @SpringBootApplication
    @EnableCasClient
    class CasProtectedApplication extends CasClientConfigurerAdapter {    
        @Override
        void configureValidationFilter(FilterRegistrationBean validationFilter) {           
            validationFilter.getInitParameters().put("millisBetweenCleanUps", "120000");
        }        
        @Override
        void configureAuthenticationFilter(FilterRegistrationBean authenticationFilter) {
            authenticationFilter.getInitParameters().put("artifactParameterName", "casTicket");
            authenticationFilter.getInitParameters().put("serviceParameterName", "targetService");
        }                                
    }
```        
