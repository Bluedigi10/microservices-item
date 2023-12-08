package com.bluedigi.microservicesitem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class MicroservicesItemApplication

fun main(args: Array<String>) {
    runApplication<MicroservicesItemApplication>(*args)
}
