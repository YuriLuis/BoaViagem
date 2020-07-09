package com.example.boaviagem.util

class Formata {


    companion object {
        fun formataPorcetagem(valor: Double): String {
            return String.format("%.2f", valor)
        }
    }
}