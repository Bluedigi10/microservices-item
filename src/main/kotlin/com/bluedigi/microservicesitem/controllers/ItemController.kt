package com.bluedigi.microservicesitem.controllers

import com.bluedigi.microservicesitem.models.Item
import com.bluedigi.microservicesitem.models.Producto
import com.bluedigi.microservicesitem.services.IItemInterfac
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.timelimiter.annotation.TimeLimiter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
class ItemController {

    var logger: Logger = LoggerFactory.getLogger(ItemController::class.java)

    @Autowired
    lateinit var cbFactory: Resilience4JCircuitBreakerFactory

    @Autowired
    @Qualifier("ItemFeign")
    lateinit var service: IItemInterfac

    @GetMapping("/findAll")
    fun findAll(): List<Item>{
        return service.findAll()
    }

    @GetMapping("find/{id}/cantidad/{cantidad}")
    fun search(@PathVariable id: Long, @PathVariable cantidad: Int): Item{
        return cbFactory.create("items")
                    .run({service.findById(id, cantidad)}, {e -> searchAlternative(id, cantidad, e)})
    }

    @CircuitBreaker(name = "items", fallbackMethod = "searchAlternative")
    @GetMapping("find2/{id}/cantidad/{cantidad}")
    fun search2(@PathVariable id: Long, @PathVariable cantidad: Int): Item{
        return service.findById(id, cantidad)
    }

    @TimeLimiter(name = "items", fallbackMethod = "searchAlternative2")
    @GetMapping("find3/{id}/cantidad/{cantidad}")
    fun search3(@PathVariable id: Long, @PathVariable cantidad: Int): CompletableFuture<Item>{
        return CompletableFuture.supplyAsync { service.findById(id, cantidad) }
    }

    fun searchAlternative(id: Long, cantidad: Int, e: Throwable): Item{
        logger.warn(e.message)
        val item = Item()
        val producto = Producto()
        producto.id = id
        producto.nombre = "Camara Sony"
        producto.precio = 500.0

        item.producto = producto
        item.cantidad = cantidad

        return item
    }

    fun searchAlternative2(id: Long, cantidad: Int, e: Throwable): CompletableFuture<Item>{
        logger.warn(e.message)
        val item = Item()
        val producto = Producto()
        producto.id = id
        producto.nombre = "Camara Sony"
        producto.precio = 500.0

        item.producto = producto
        item.cantidad = cantidad

        return CompletableFuture.supplyAsync { item }
    }
}