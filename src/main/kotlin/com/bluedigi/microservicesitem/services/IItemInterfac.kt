package com.bluedigi.microservicesitem.services

import com.bluedigi.microservicesitem.models.Item
import com.bluedigi.microservicesitem.models.Producto

interface IItemInterfac {
    fun findAll(): List<Item>
    fun findById(id: Long, cantidad: Int): Item

    fun save(producto: Producto): Producto

    fun update(producto: Producto, id: Long): Producto

    fun delete(id: Long): String
}