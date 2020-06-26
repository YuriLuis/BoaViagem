package com.example.boaviagem.tests

import com.example.boaviagem.model.Gasto
import com.example.boaviagem.model.Login
import com.example.boaviagem.model.Viagem

class CarregaArrays {


    companion object {
        fun carregaLogin(): Login {
            return Login("1", "yuriLuis", "123456789");
        }

        fun carregaDespesas(): ArrayList<Gasto> {
            val listaGastos: ArrayList<Gasto> = mutableListOf<Gasto>() as ArrayList<Gasto>
            val gasto1 = Gasto(
                "1", 150.00, "25/06/2020", "Big Mac",
                "McDonalds", "Alimentação"
            )
            val gasto2 = Gasto(
                "2", 100.00, "25/06/2020", "Uber",
                "Uber", "Transporte"
            )
            listaGastos.add(gasto1)
            listaGastos.add(gasto2)

            return listaGastos
        }

        fun carregaViagem(): List<Viagem> {
            var listaViagens: ArrayList<Viagem> = mutableListOf<Viagem>() as ArrayList<Viagem>
            val viagem1 = Viagem(
                "1", "São Paulo", carregaDespesas(),
                "Negócios", "25/06/2020", null,
                500.00, 1, carregaLogin()
            )
            listaViagens.add(viagem1)
            return listaViagens
        }

        fun adicionaDespesa(viagem: Viagem, gasto: Gasto) {
            var listaGastos: ArrayList<Gasto> = carregaDespesas() as ArrayList<Gasto>
            var listaViagem: ArrayList<Viagem> = carregaViagem() as ArrayList<Viagem>
            listaGastos.add(gasto)
            viagem.gastos = carregaDespesas()
            viagem.calculaGastos()
            viagem.calculaPorcetagemGasto()


        }

        fun formataPorcetagem(valor: Double): String {
            return String.format("%.2f", valor)
        }
    }
}