//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.unicon.cas.client.configuration;

import net.unicon.cas.client.configuration.EnableCasClient.ValidationType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huangliuyu
 * @Description cas客户端配置属性
 * @create 2019-06-13
 **/
@ConfigurationProperties(
        prefix = "cas",
        ignoreUnknownFields = false
)
public class CasClientConfigurationProperties {
    @NotNull
    private String serverUrlPrefix;
    @NotNull
    private String serverLoginUrl;
    @NotNull
    private String clientHostUrl;
    private List<String> authenticationUrlPatterns = new ArrayList();
    private List<String> validationUrlPatterns = new ArrayList();
    private List<String> requestWrapperUrlPatterns = new ArrayList();
    private List<String> assertionThreadLocalUrlPatterns = new ArrayList();
    private Boolean gateway;
    private Boolean useSession;
    private Boolean redirectAfterValidation;
    private Boolean acceptAnyProxy;
    private List<String> allowedProxyChains = new ArrayList();
    private String proxyCallbackUrl;
    private String proxyReceptorUrl;
    private ValidationType validationType;
    //不拦截url
    private String ignorePattern;

    public void setIgnorePattern(String ignorePattern){
        this.ignorePattern=ignorePattern;
    }

    public String getIgnorePattern(){
        return this.ignorePattern;
    }

    public CasClientConfigurationProperties() {
        this.validationType = ValidationType.CAS3;
    }

    public String getServerUrlPrefix() {
        return this.serverUrlPrefix;
    }

    public void setServerUrlPrefix(String serverUrlPrefix) {
        this.serverUrlPrefix = serverUrlPrefix;
    }

    public String getServerLoginUrl() {
        return this.serverLoginUrl;
    }

    public void setServerLoginUrl(String serverLoginUrl) {
        this.serverLoginUrl = serverLoginUrl;
    }

    public String getClientHostUrl() {
        return this.clientHostUrl;
    }

    public void setClientHostUrl(String clientHostUrl) {
        this.clientHostUrl = clientHostUrl;
    }

    public Boolean getAcceptAnyProxy() {
        return this.acceptAnyProxy;
    }

    public void setAcceptAnyProxy(Boolean acceptAnyProxy) {
        this.acceptAnyProxy = acceptAnyProxy;
    }

    public List<String> getAllowedProxyChains() {
        return this.allowedProxyChains;
    }

    public void setAllowedProxyChains(List<String> allowedProxyChains) {
        this.allowedProxyChains = allowedProxyChains;
    }

    public String getProxyCallbackUrl() {
        return this.proxyCallbackUrl;
    }

    public void setProxyCallbackUrl(String proxyCallbackUrl) {
        this.proxyCallbackUrl = proxyCallbackUrl;
    }

    public String getProxyReceptorUrl() {
        return this.proxyReceptorUrl;
    }

    public void setProxyReceptorUrl(String proxyReceptorUrl) {
        this.proxyReceptorUrl = proxyReceptorUrl;
    }

    public Boolean getGateway() {
        return this.gateway;
    }

    public void setGateway(Boolean gateway) {
        this.gateway = gateway;
    }

    public Boolean getUseSession() {
        return this.useSession;
    }

    public void setUseSession(Boolean useSession) {
        this.useSession = useSession;
    }

    public Boolean getRedirectAfterValidation() {
        return this.redirectAfterValidation;
    }

    public void setRedirectAfterValidation(Boolean redirectAfterValidation) {
        this.redirectAfterValidation = redirectAfterValidation;
    }

    public List<String> getAssertionThreadLocalUrlPatterns() {
        return this.assertionThreadLocalUrlPatterns;
    }

    public void setAssertionThreadLocalUrlPatterns(List<String> assertionThreadLocalUrlPatterns) {
        this.assertionThreadLocalUrlPatterns = assertionThreadLocalUrlPatterns;
    }

    public List<String> getRequestWrapperUrlPatterns() {
        return this.requestWrapperUrlPatterns;
    }

    public void setRequestWrapperUrlPatterns(List<String> requestWrapperUrlPatterns) {
        this.requestWrapperUrlPatterns = requestWrapperUrlPatterns;
    }

    public List<String> getValidationUrlPatterns() {
        return this.validationUrlPatterns;
    }

    public void setValidationUrlPatterns(List<String> validationUrlPatterns) {
        this.validationUrlPatterns = validationUrlPatterns;
    }

    public List<String> getAuthenticationUrlPatterns() {
        return this.authenticationUrlPatterns;
    }

    public void setAuthenticationUrlPatterns(List<String> authenticationUrlPatterns) {
        this.authenticationUrlPatterns = authenticationUrlPatterns;
    }

    public ValidationType getValidationType() {
        return this.validationType;
    }

    public void setValidationType(ValidationType validationType) {
        this.validationType = validationType;
    }
}
