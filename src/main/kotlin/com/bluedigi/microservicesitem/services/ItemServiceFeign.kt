package com.bluedigi.microservicesitem.services

import com.bluedigi.microservicesitem.clients.ProductoClientRest
import com.bluedigi.microservicesitem.models.Item
import com.bluedigi.microservicesitem.models.Producto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("ItemFeign")
class ItemServiceFeign: IItemInterfac {
    @Autowired
    lateinit var clienteRest: ProductoClientRest

    override fun findAll(): List<Item> {
        return clienteRest.list().stream().map { p -> Item(p,1) }
            .toList()
    }

    override fun findById(id: Long, cantidad: Int): Item {
        println("Con feign")
        return Item(clienteRest.find(id), cantidad)
    }

    override fun save(producto: Producto): Producto {
        TODO("Not yet implemented")
    }

    override fun update(producto: Producto, id: Long): Producto {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long): String {
        TODO("Not yet implemented")
    }
}