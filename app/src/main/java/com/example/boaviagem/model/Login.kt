package com.example.boaviagem.model

import java.io.Serializable

data class Login(val idLogin: Int?, val nome: String, val email: String, val senha : String) : Serializable {

  constructor(): this(null, "", "", "")
}
