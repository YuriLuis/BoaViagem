package com.example.boaviagem.model

import java.io.Serializable

data class Viagem(
    val idViagem: Int?, val destino: String,
    var despesa: ArrayList<Despesa>?, val tipoViagem: Int,
    val dataChegada: String, val dataPartida: String?,
    val orcamento: Double, val quantidadePessoas: Int,
    val idLogin: Login?
) : Serializable {

    constructor() : this(null, "", null, 0,
    "", "", 0.0, 0, null)

    fun calculaGastos(): Double {
        var total: Double = 0.0
        if (this.despesa != null) {
            this.despesa!!.forEach { gasto ->
                total += gasto.valor
            }
        }
        return total
    }

    fun calculaPorcetagemGasto(): Double {
        var totalDespesa = calculaGastos()
        return (totalDespesa * 100) / orcamento
    }

    override fun toString(): String {
        return idViagem.toString()
}
}