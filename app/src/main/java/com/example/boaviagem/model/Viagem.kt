package com.example.boaviagem.model

import java.io.Serializable

data class Viagem(
    val idViagem: String, val destino: String,
    var gastos: ArrayList<Gasto>?, val tipoViagem: String,
    val dataChegada: String, val dataPartida: String?,
    val orcamento: Double, val quantidadePessoas: Int,
    val login: Login
) : Serializable {


    fun calculaGastos(): Double {
        var total: Double = 0.0
        if (this.gastos != null) {
            this.gastos!!.forEach { gasto ->
                total += gasto.valor
            }
        }
        return total
    }

    fun calculaPorcetagemGasto(): Double {
        var totalDespesa = calculaGastos()
        return (totalDespesa * 100) / orcamento
    }

    fun adicionaGasto(gasto: Gasto) {
        gastos?.add(gasto)
    }

    override fun toString(): String {
        return destino
    }
}