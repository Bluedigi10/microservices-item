package com.bluedigi.microservicesitem.controllers

import com.bluedigi.microservicesitem.models.Item
import com.bluedigi.microservicesitem.services.IItemInterfac
import com.bluedigi.microservicesitem.services.ItemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ItemController {
    @Autowired
    lateinit var service: IItemInterfac

    @GetMapping("/findAll")
    fun findAll(): List<Item>{
        return service.findAll()
    }

    @GetMapping("find/{id}/cantidad/{cantidad}")
    fun search(@PathVariable id: Long, @PathVariable cantidad: Int): Item{
        return service.findById(id, cantidad)
    }
}