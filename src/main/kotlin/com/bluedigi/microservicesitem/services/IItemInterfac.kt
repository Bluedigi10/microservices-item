package com.bluedigi.microservicesitem.services

import com.bluedigi.microservicesitem.models.Item

interface IItemInterfac {
    fun findAll(): List<Item>
    fun findById(id: Long, cantidad: Int): Item
}