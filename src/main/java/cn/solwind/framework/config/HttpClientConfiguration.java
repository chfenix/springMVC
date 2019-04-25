package cn.solwind.framework.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
* @author Zhouluning
* @version 创建时间：2019-04-12
*
* httpclient配置类
*/

@Configuration
@PropertySource("classpath:httpclient.properties")
public class HttpClientConfiguration {

    @Value("${http.maxTotal}")
    private Integer maxTotal;

    @Value("${http.defaultMaxPerRoute}")
    private Integer defaultMaxPerRoute;

    @Value("${http.connectTimeout}")
    private Integer connectTimeout;

    @Value("${http.connectionRequestTimeout}")
    private Integer connectionRequestTimeout;

    @Value("${http.socketTimeout}")
    private Integer socketTimeout;

    @Value("${http.validateAfterInactivity}")
    private Integer validateAfterInactivity;


    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(){
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxTotal);
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        connectionManager.setValidateAfterInactivity(validateAfterInactivity);
        return connectionManager;
    }

    @Bean
    public HttpClientBuilder httpClientBuilder(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager){
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);

        return httpClientBuilder;
    }

    @Bean
    public CloseableHttpClient closeableHttpClient(HttpClientBuilder httpClientBuilder){
        return httpClientBuilder.build();
    }


    @Bean
    public RequestConfig.Builder builder(){
        RequestConfig.Builder builder = RequestConfig.custom();
        return builder.setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout);
    }

    @Bean
    public RequestConfig requestConfig(RequestConfig.Builder builder){
        return builder.build();
    }
}