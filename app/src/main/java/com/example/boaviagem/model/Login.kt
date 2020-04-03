package com.example.boaviagem.model

class Login(private val login: String, private val senha : String){

    fun validaLogin(): Boolean{

        return (login == "teste01") and (senha == "12345")
    }
}