package com.relogging.server.global.config

import org.apache.http.HttpHeaders
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.xml.Jaxb2XmlDecoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Configuration
class WebClientConfig {
    @Bean
    fun webClient(): WebClient {
        val strategies =
            ExchangeStrategies.builder().codecs {
                it.defaultCodecs().maxInMemorySize(1024 * 1024)
                it.defaultCodecs().jaxb2Decoder(Jaxb2XmlDecoder())
            }.build()
        return WebClient.builder()
            .clientConnector(
                ReactorClientHttpConnector(
                    HttpClient.newConnection().responseTimeout(Duration.ofSeconds(30)),
                ),
            )
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE)
            .exchangeStrategies(strategies)
            .build()
    }
}
