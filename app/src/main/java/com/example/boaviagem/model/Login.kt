package com.example.boaviagem.model

import java.io.Serializable

data class Login(private val idLogin: String, private val login: String, private val senha: String) : Serializable {

}
