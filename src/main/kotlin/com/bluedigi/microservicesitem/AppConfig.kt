package com.bluedigi.microservicesitem

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class AppConfig {
    @Bean("clienteRest")
    @LoadBalanced
    fun registrarRestTemplate(): RestTemplate{
        return RestTemplate()
    }
}