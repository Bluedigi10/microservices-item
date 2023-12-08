package com.bluedigi.microservicesitem.services

import com.bluedigi.microservicesitem.clients.ProductoClientRest
import com.bluedigi.microservicesitem.models.Item
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
@Primary
class ItemServiceFeign: IItemInterfac {
    @Autowired
    lateinit var clienteRest: ProductoClientRest

    override fun findAll(): List<Item> {
        println("Con feign")
        return clienteRest.list().stream().map { p -> Item(p,1) }
            .collect(Collectors.toList())
    }

    override fun findById(id: Long, cantidad: Int): Item {
        println("Con feign")
        return Item(clienteRest.find(id), cantidad)
    }
}