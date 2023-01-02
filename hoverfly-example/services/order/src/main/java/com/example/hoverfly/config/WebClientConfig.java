package com.example.hoverfly.config;

import java.net.InetSocketAddress;

import javax.net.ssl.SSLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

@Configuration(proxyBeanMethods = false)
public class WebClientConfig {

    @Primary
    @Bean
    public WebClient webClient(
            @Value("${hoverfly.enabled:false}") boolean hoverflyEnabled,
            @Value("${hoverfly.hostname:}") String hostname,
            @Value("${hoverfly.port:}") String port
    ) throws SSLException {

        SslContext sslContext = SslContextBuilder.forClient()
                                                 .trustManager(InsecureTrustManagerFactory.INSTANCE)
                                                 .build();

        HttpClient httpClient = HttpClient.create()
                                          .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));

        if (hoverflyEnabled) {
            httpClient = httpClient.proxy(proxy -> proxy.type(ProxyProvider.Proxy.HTTP).address(new InetSocketAddress(hostname, Integer.parseInt(port))));
        }
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient.compress(true));
        var webclient = WebClient.builder()
                                 .clientConnector(connector)
                                 .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                                 .build();
        return webclient;
    }

}
