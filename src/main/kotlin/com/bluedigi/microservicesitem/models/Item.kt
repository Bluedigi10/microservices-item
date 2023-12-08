package com.bluedigi.microservicesitem.models

class Item (val producto: Producto = Producto(), val cantidad: Int = 0){
    fun getTotal(): Double{
        return producto.precio * cantidad
    }
}
