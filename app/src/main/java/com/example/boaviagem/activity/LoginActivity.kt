package com.example.boaviagem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.boaviagem.R
import com.example.boaviagem.model.Login
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var textInputLayoutLogin : TextInputLayout
    private lateinit var textInputEditTextLogin : TextInputEditText
    private lateinit var textInputLayoutSenha : TextInputLayout
    private lateinit var textInputEditTextSenha : TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        instanciaLayoutComXml()
    }

    private fun instanciaLayoutComXml(){

        textInputLayoutLogin = findViewById(R.id.textInputLayoutLogin)
        textInputEditTextLogin = findViewById(R.id.textInputEditTextLogin)
        textInputLayoutSenha = findViewById(R.id.textInputLayoutSenha)
        textInputEditTextSenha = findViewById(R.id.textInputEditTextSenha)
    }

     fun entrar(view : View){

        val login = Login(textInputEditTextLogin.text.toString(),
                        textInputEditTextSenha.text.toString())

        if (login.validaLogin() and validaCamposActivityLogin()){
            Toast.makeText(this, "Sucesso ao entrar!", Toast.LENGTH_LONG).show()
        }else {
            Toast.makeText(this, "Erro ao entrar!", Toast.LENGTH_LONG).show()
        }
    }

    private fun validaCamposActivityLogin():Boolean{
        if (textInputEditTextLogin.text.isNullOrEmpty()){
            textInputEditTextLogin.error = "Campo Obrigatório"
            return false
        }
        if (textInputEditTextSenha.text.isNullOrEmpty()){
            textInputEditTextLogin.error = "Campo Obrigatório"
            return false
        }
        return true
    }
}
