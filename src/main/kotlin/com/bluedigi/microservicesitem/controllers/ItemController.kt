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
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RefreshScope
@RestController
class ItemController {

    var logger: Logger = LoggerFactory.getLogger(ItemController::class.java)

    @Autowired
    lateinit var cbFactory: Resilience4JCircuitBreakerFactory

    @Autowired
    lateinit var env: Environment

    @Autowired
    @Qualifier("ItemRest")
    lateinit var service: IItemInterfac

    @Value("\${config.texto}")
    val texto = ""

    @Value("\${server.port}")
    val port = ""

    @GetMapping("/findAll")
    fun findAll(): List<Item>{
        return service.findAll()
    }

    @CircuitBreaker(name = "items", fallbackMethod = "searchAlternative")
    @TimeLimiter(name = "items", fallbackMethod = "searchAlternative")
    @GetMapping("find/{id}/cantidad/{cantidad}")
    fun search(@PathVariable id: Long, @PathVariable cantidad: Int): CompletableFuture<Item>{
        return CompletableFuture.supplyAsync { service.findById(id, cantidad) }
    }

    fun searchAlternative(id: Long, cantidad: Int, e: Throwable): CompletableFuture<Item>{
        logger.warn("${e.message}")

        return CompletableFuture.supplyAsync { Item(Producto(id,"Camara", 500.0), cantidad) }
    }

    @GetMapping("/get-config")
    fun getConfig(): ResponseEntity<Any>{
        val json = HashMap<String, String>()
        json["texto"] = texto
        json["port"] = port

        if (env.activeProfiles.isNotEmpty() && env.activeProfiles[0] == "dev"){
            json["name"] = env["config.autor.name"].toString()
            json["email"] = env["config.autor.email"].toString()
        }

        return ResponseEntity(json, HttpStatus.OK)
    }

    /*@GetMapping("find2/{id}/cantidad/{cantidad}")
    fun search2(@PathVariable id: Long, @PathVariable cantidad: Int): Item{
        return cbFactory.create("items")
            .run({service.findById(id, cantidad)}, {e -> searchAlternative(id, cantidad, e)})
    }*/

    /*@CircuitBreaker(name = "items", fallbackMethod = "searchAlternative")
    @TimeLimiter(name = "items")
    @GetMapping("find3/{id}/cantidad/{cantidad}")
    fun search3(@PathVariable id: Long, @PathVariable cantidad: Int): Item{
        return service.findById(id, cantidad)
    }*/
}