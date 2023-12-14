package com.bluedigi.microservicesitem.services

import com.bluedigi.microservicesitem.models.Item
import com.bluedigi.microservicesitem.models.Producto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.stream.Collectors

@Service("ItemRest")
class ItemService: IItemInterfac {
    @Autowired
    lateinit var clienteRest: RestTemplate

    val path = "http://localhost:8001/"

    override fun findAll(): List<Item> {
        println("Con restTemplate")
        val arrayProducto = clienteRest.getForObject("${path}list", Array<Producto>::class.java)
        println(arrayProducto)
        val productos: List<Producto> = arrayProducto!!.toList()
        val items = productos.stream().map { p -> Item(p,1) }
        return items.collect(Collectors.toList())
    }

    override fun findById(id: Long, cantidad: Int): Item {
        println("Con restTemplate")
        val pathParam = HashMap<String, String>()
        pathParam["id"] = id.toString()
        val producto = clienteRest.getForObject("${path}find/{id}", Producto::class.java, pathParam)

        return producto?.let { Item(it,cantidad) }!!
    }
}