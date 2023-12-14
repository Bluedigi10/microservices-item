package com.bluedigi.microservicesitem.models

import java.util.*

data class Producto(
    var id: Long = 0,
    var nombre: String = "",
    var precio: Double = 0.0,
    val createAt: Date = Date()
)
