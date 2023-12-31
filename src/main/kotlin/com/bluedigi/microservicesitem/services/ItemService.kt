package com.bluedigi.microservicesitem.services

import com.bluedigi.microservicesitem.models.Item
import com.bluedigi.microservicesitem.models.Producto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashMap

@Service("ItemRest")
class ItemService: IItemInterfac {
    @Autowired
    lateinit var clienteRest: RestTemplate

    val path = "http://microservices/"

    override fun findAll(): List<Item> {
        println("Con restTemplate")
        val arrayProducto = clienteRest.getForObject("${path}list", Array<Producto>::class.java)
        println("$arrayProducto hola")
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

    override fun save(producto: Producto): Producto {
        val requestEntity = HttpEntity(producto)
        val response = clienteRest.exchange("${path}save", HttpMethod.POST, requestEntity, Producto::class.java)

        return response.body!!
    }

    override fun update(producto: Producto, id: Long): Producto {
        val requestEntity = HttpEntity(producto)
        val pathParam = HashMap<String, String>()
        pathParam["id"] = id.toString()
        val response = clienteRest.exchange("${path}edit/{id}", HttpMethod.PUT, requestEntity, Producto::class.java, pathParam)

        return response.body!!
    }

    override fun delete(id: Long): String {
        val pathParam = HashMap<String, String>()
        pathParam["id"] = id.toString()
        val response = clienteRest.delete("${path}edit/{id}", String::class.java, pathParam)

        return response.toString()
    }
}