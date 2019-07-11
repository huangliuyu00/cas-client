//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.unicon.cas.client.configuration;

import net.unicon.cas.client.configuration.EnableCasClient.ValidationType;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.authentication.Saml11AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.jasig.cas.client.validation.Cas30ProxyReceivingTicketValidationFilter;
import org.jasig.cas.client.validation.Saml11TicketValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import java.util.*;


/**
 * @author huangliuyu
 * @Description cas客户端配置
 * @create 2019-06-13
 **/
@Configuration
@EnableConfigurationProperties({CasClientConfigurationProperties.class})
public class CasClientConfiguration {
    @Autowired
    CasClientConfigurationProperties configProps;
    private CasClientConfigurer casClientConfigurer;

    public CasClientConfiguration() {
    }

    @Bean
    public FilterRegistrationBean singleSignOutFilter(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SingleSignOutFilter());
        registration.setName("singleSignOutFilter");
        registration.setUrlPatterns(Arrays.asList("/*"));
        //设置过滤器参数
        Map<String, String> filterInitParams=new HashMap<String, String>();
        filterInitParams.put("casServerUrlPrefix",this.configProps.getServerUrlPrefix());
        registration.setInitParameters(filterInitParams);
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public ServletListenerRegistrationBean singleSignOutHttpSessionListener(){
        ServletListenerRegistrationBean listenerRegistration=new ServletListenerRegistrationBean();
        listenerRegistration.setListener(new SingleSignOutHttpSessionListener());
        return listenerRegistration;
    }

    @Bean
    public FilterRegistrationBean casValidationFilter() {
        FilterRegistrationBean validationFilter = new FilterRegistrationBean();
        Object targetCasValidationFilter;
        switch(this.configProps.getValidationType()) {
            case CAS:
                targetCasValidationFilter = new Cas20ProxyReceivingTicketValidationFilter();
                break;
            case CAS3:
                targetCasValidationFilter = new Cas30ProxyReceivingTicketValidationFilter();
                break;
            case SAML:
                targetCasValidationFilter = new Saml11TicketValidationFilter();
                break;
            default:
                throw new IllegalStateException("Unknown CAS validation type");
        }

        this.initFilter(validationFilter, (Filter)targetCasValidationFilter, 2, this.constructInitParams("casServerUrlPrefix", this.configProps.getServerUrlPrefix(), this.configProps.getClientHostUrl()), this.configProps.getValidationUrlPatterns());
        if (this.configProps.getUseSession() != null) {
            validationFilter.getInitParameters().put("useSession", String.valueOf(this.configProps.getUseSession()));
        }

        //忽略不拦截的 url正则表达式
        if(this.configProps.getIgnorePattern()!=null){
            validationFilter.getInitParameters().put("ignorePattern", String.valueOf(this.configProps.getIgnorePattern()));
        }

        if (this.configProps.getRedirectAfterValidation() != null) {
            validationFilter.getInitParameters().put("redirectAfterValidation", String.valueOf(this.configProps.getRedirectAfterValidation()));
        }

        if (this.configProps.getAcceptAnyProxy() != null) {
            validationFilter.getInitParameters().put("acceptAnyProxy", String.valueOf(this.configProps.getAcceptAnyProxy()));
        }

        if (this.configProps.getAllowedProxyChains().size() > 0) {
            validationFilter.getInitParameters().put("allowedProxyChains", StringUtils.collectionToDelimitedString(this.configProps.getAllowedProxyChains(), " "));
        }

        if (this.configProps.getProxyCallbackUrl() != null) {
            validationFilter.getInitParameters().put("proxyCallbackUrl", this.configProps.getProxyCallbackUrl());
        }

        if (this.configProps.getProxyReceptorUrl() != null) {
            validationFilter.getInitParameters().put("proxyReceptorUrl", this.configProps.getProxyReceptorUrl());
        }

        if (this.casClientConfigurer != null) {
            this.casClientConfigurer.configureValidationFilter(validationFilter);
        }

        return validationFilter;
    }

    @Bean
    public FilterRegistrationBean casAuthenticationFilter() {
        FilterRegistrationBean authnFilter = new FilterRegistrationBean();
        Filter targetCasAuthnFilter = this.configProps.getValidationType() != ValidationType.CAS && this.configProps.getValidationType() != ValidationType.CAS3 ? new Saml11AuthenticationFilter() : new AuthenticationFilter();
        this.initFilter(authnFilter, (Filter)targetCasAuthnFilter, 3, this.constructInitParams("casServerLoginUrl", this.configProps.getServerLoginUrl(), this.configProps.getClientHostUrl()), this.configProps.getAuthenticationUrlPatterns());
        if (this.configProps.getGateway() != null) {
            authnFilter.getInitParameters().put("gateway", String.valueOf(this.configProps.getGateway()));
        }

        if (this.casClientConfigurer != null) {
            this.casClientConfigurer.configureAuthenticationFilter(authnFilter);
        }

        return authnFilter;
    }

    @Bean
    public FilterRegistrationBean casHttpServletRequestWrapperFilter() {
        FilterRegistrationBean reqWrapperFilter = new FilterRegistrationBean();
        reqWrapperFilter.setFilter(new HttpServletRequestWrapperFilter());
        if (this.configProps.getRequestWrapperUrlPatterns().size() > 0) {
            reqWrapperFilter.setUrlPatterns(this.configProps.getRequestWrapperUrlPatterns());
        }

        reqWrapperFilter.setOrder(4);
        if (this.casClientConfigurer != null) {
            this.casClientConfigurer.configureHttpServletRequestWrapperFilter(reqWrapperFilter);
        }

        return reqWrapperFilter;
    }



    @Bean
    public FilterRegistrationBean casAssertionThreadLocalFilter() {
        FilterRegistrationBean assertionTLFilter = new FilterRegistrationBean();
        assertionTLFilter.setFilter(new AssertionThreadLocalFilter());
        if (this.configProps.getAssertionThreadLocalUrlPatterns().size() > 0) {
            assertionTLFilter.setUrlPatterns(this.configProps.getAssertionThreadLocalUrlPatterns());
        }

        assertionTLFilter.setOrder(5);
        if (this.casClientConfigurer != null) {
            this.casClientConfigurer.configureAssertionThreadLocalFilter(assertionTLFilter);
        }

        return assertionTLFilter;
    }

    @Autowired(
            required = false
    )
    void setConfigurers(Collection<CasClientConfigurer> configurers) {
        if (!CollectionUtils.isEmpty(configurers)) {
            if (configurers.size() > 1) {
                throw new IllegalStateException(configurers.size() + " implementations of CasClientConfigurer were found when only 1 was expected. Refactor the configuration such that CasClientConfigurer is implemented only once or not at all.");
            } else {
                this.casClientConfigurer = (CasClientConfigurer)configurers.iterator().next();
            }
        }
    }

    private Map<String, String> constructInitParams(String casUrlParamName, String casUrlParamVal, String clientHostUrlVal) {
        Map<String, String> initParams = new HashMap(2);
        initParams.put(casUrlParamName, casUrlParamVal);
        initParams.put("serverName", clientHostUrlVal);
        return initParams;
    }

    private void initFilter(FilterRegistrationBean filterRegistrationBean, Filter targetFilter, int filterOrder, Map<String, String> initParams, List<String> urlPatterns) {
        filterRegistrationBean.setFilter(targetFilter);
        filterRegistrationBean.setOrder(filterOrder);
        filterRegistrationBean.setInitParameters(initParams);
        if (urlPatterns.size() > 0) {
            filterRegistrationBean.setUrlPatterns(urlPatterns);
        }

    }
}
