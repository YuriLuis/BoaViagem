package com.example.boaviagem.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.boaviagem.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginActivity : AppCompatActivity() {

    private lateinit var textInputLayoutLogin : TextInputLayout
    private lateinit var textInputEditTextLogin : TextInputEditText
    private lateinit var textInputLayoutSenha : TextInputLayout
    private lateinit var textInputEditTextSenha : TextInputEditText

    private  var autenticacao : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        instanciaLayoutComXml()
    }

    override fun onStart() {
        super.onStart()
        vericaSeUsuarioEstaLogado()
    }

    private fun vericaSeUsuarioEstaLogado() {
        //autenticacao.signOut();
        if (usuarioEstaLogado()) {
            abrirTelaPrincipal()
        }
    }

    private fun usuarioEstaLogado(): Boolean {
        return autenticacao.currentUser != null
    }


    private fun instanciaLayoutComXml(){

        textInputLayoutLogin = findViewById(R.id.textInputLayoutLogin)
        textInputEditTextLogin = findViewById(R.id.textInputEditTextLogin)
        textInputLayoutSenha = findViewById(R.id.textInputLayoutSenha)
        textInputEditTextSenha = findViewById(R.id.textInputEditTextSenha)
    }

    fun entrar(view: View){
        var email = textInputEditTextLogin.text.toString()
        var senha = textInputEditTextSenha.text.toString()

        if (campoNaoInvalido()){
            logarUsuario()

        }
    }

     fun logarUsuario(){

         var email = textInputEditTextLogin.text.toString()
         var senha = textInputEditTextSenha.text.toString()

         autenticacao.signInWithEmailAndPassword(email, senha)
             .addOnCompleteListener(this) { task ->
                 if (task.isSuccessful) {
                        abrirTelaPrincipal()
                 } else {
                    var excecao = ""
                     try {
                         throw task.getException()!!;
                     }catch (e : FirebaseAuthInvalidUserException){
                         excecao = "E-mail ou senha não correspondem a um usuário cadastrado!";
                     }catch (e : FirebaseAuthInvalidCredentialsException){
                         excecao = "Usuário não está cadastrado!";
                     }catch (e : Exception){
                         excecao = "Erro ao cadastrar usuario : " + e.message;
                         e.printStackTrace();
                     }
                     Toast.makeText(this, excecao, Toast.LENGTH_LONG).show()
                 }
             }
    }

    fun abrirTelaPrincipal(){
        startActivity(Intent(this,PrincipalActivity::class.java ).apply {})
    }

    fun abriTelaCadastro(view: View){
        startActivity(Intent(this,CreateLoginActivity::class.java ).apply {})
    }

    private fun campoNaoInvalido():Boolean{
        var login = textInputEditTextLogin.text.toString()
        var senha = textInputEditTextSenha.text.toString()
        if (campoEhVazio(login)){
            textInputEditTextLogin.error = "Campo Obrigatório"
            return false
        }
        if (campoEhVazio(senha)){
            textInputEditTextLogin.error = "Campo Obrigatório"
            return false
        }
        return true
    }

    private fun campoEhVazio(campo : String) = campo.isNullOrEmpty()
}
