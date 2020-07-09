package com.example.boaviagem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.boaviagem.R
import com.example.boaviagem.config.RetrofitInitializer
import com.example.boaviagem.model.Login
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.android.synthetic.main.activity_cria_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class CreateLoginActivity : AppCompatActivity() {

    private lateinit var email : TextInputEditText
    private lateinit var senha : TextInputEditText
    private lateinit var nome : TextInputEditText

    private var autenticacao : FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cria_login)

        instanciaLayoutComXml()
    }

    private fun instanciaLayoutComXml(){
        email = findViewById(R.id.inputTextEmail)
        senha = findViewById(R.id.inputTextSenha)
        nome = findViewById(R.id.textIputEditTextNome)

    }

    fun cadastrarUsuarioClickBotao(view: View){
        var email = email.text.toString()
        var senha = senha.text.toString()

        if (campoNaoInvalido()){
            criaConta()
            Toast.makeText(this, getString(R.string.criadoComSucesso), Toast.LENGTH_LONG).show()
        }
    }

    fun criaConta(){
        var email = email.text.toString()
        var senha = senha.text.toString()
        var nome = nome.text.toString()

        var login = Login(null, nome, email, senha )

        autenticacao.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    adicionaUsuarioAPI(login)
                    finish()
                } else {
                    var excecao = ""
                    try{
                        throw task.exception!!;
                    }catch (e : FirebaseAuthWeakPasswordException){
                        excecao = "Digite uma senha mais forte!";
                    }catch (e : FirebaseAuthInvalidCredentialsException){
                        excecao = "Por favor, digite um e-mail válido!";
                    }catch (e : FirebaseAuthUserCollisionException){
                        excecao = "Essa conta já está cadastrada!";
                    }catch (e : Exception){
                        excecao = "Erro ao cadastrar usuario : " + e.message;
                        e.printStackTrace();
                    }
                    Toast.makeText(this, excecao, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun campoNaoInvalido():Boolean{
        var login = email.text.toString()
        var senha = senha.text.toString()

        if (campoEhVazio(login)){
            inputTextEmail.error = "Campo Obrigatório"
            return false
        }
        if (campoEhVazio(senha)){
            inputTextSenha.error = "Campo Obrigatório"
            return false
        }
        return true
    }

    private fun campoEhVazio(campo : String) = campo.isNullOrEmpty()

    private fun adicionaUsuarioAPI(login: Login) {
        val call = RetrofitInitializer().boaViagemService().adicionaLogin(login)
        call.enqueue(object : Callback<Login> {
            override fun onFailure(call: Call<Login>, t: Throwable) {
                Log.d("Response POST LOGIN (TELA CRIAR LOGIN) {ERRO!} ", t.message)
            }

            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                Log.d("Response POST LOGIN (TELA CRIAR LOGIN) {OK}", response.toString())
            }
        })
    }
}
