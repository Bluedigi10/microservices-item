package com.bluedigi.microservicesitem.models

import java.util.*

data class Producto(
    val id: Long = 0,
    val nombre: String = "",
    val precio: Double = 0.0,
    val createAt: Date = Date()
)
