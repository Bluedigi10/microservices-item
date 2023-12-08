package com.bluedigi.microservicesitem.clients

import com.bluedigi.microservicesitem.models.Producto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "microservices")
interface ProductoClientRest {
    @GetMapping("/list")
    fun list(): List<Producto>

    @GetMapping("/find/{id}")
    fun find(@PathVariable id: Long): Producto
}