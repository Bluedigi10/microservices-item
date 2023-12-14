package com.bluedigi.microservicesitem.models

class Item (var producto: Producto = Producto(), var cantidad: Int = 0){
    fun getTotal(): Double{
        return producto.precio * cantidad
    }
}
