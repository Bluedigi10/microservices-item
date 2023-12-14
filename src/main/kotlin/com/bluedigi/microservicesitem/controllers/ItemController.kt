package com.bluedigi.microservicesitem.controllers

import com.bluedigi.microservicesitem.models.Item
import com.bluedigi.microservicesitem.models.Producto
import com.bluedigi.microservicesitem.services.IItemInterfac
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ItemController {

    var logger: Logger = LoggerFactory.getLogger(ItemController::class.java)

    @Autowired
    @Qualifier("ItemRest")
    lateinit var service: IItemInterfac

    @GetMapping("/findAll")
    fun findAll(): List<Item>{
        return service.findAll()
    }

    @GetMapping("find/{id}/cantidad/{cantidad}")
    fun search(@PathVariable id: Long, @PathVariable cantidad: Int): Item{
        return service.findById(id, cantidad)
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
}