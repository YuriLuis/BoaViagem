package com.example.boaviagem.model

import java.io.Serializable

data class Despesa(
    val idDespesa: Int?,
    val tipo: String, val valor: Double, val data: String, val descricao: String,
    val local: String
) : Serializable {

    constructor(): this(null, "", 0.0, "", "",
    "")
}