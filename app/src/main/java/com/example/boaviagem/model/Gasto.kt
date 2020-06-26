package com.example.boaviagem.model

import java.io.Serializable

data class Gasto(
     val idGasto: String,  val valor: Double,
     val data: String ,  val descicao : String,
     val local : String,  val tipoGasto : String
) : Serializable {
}