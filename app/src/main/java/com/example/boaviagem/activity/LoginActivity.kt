package com.example.boaviagem.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.boaviagem.R
import com.example.boaviagem.config.RetrofitInitializer
import com.example.boaviagem.model.Login
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var textInputLayoutLogin: TextInputLayout
    private lateinit var textInputEditTextLogin: TextInputEditText
    private lateinit var textInputLayoutSenha: TextInputLayout
    private lateinit var textInputEditTextSenha: TextInputEditText
    private var autenticacao: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        instanciaLayoutComXml()
    }

    private fun instanciaLayoutComXml() {

        textInputLayoutLogin = findViewById(R.id.textInputLayoutLogin)
        textInputEditTextLogin = findViewById(R.id.textInputEditTextLogin)
        textInputLayoutSenha = findViewById(R.id.textInputLayoutSenha)
        textInputEditTextSenha = findViewById(R.id.textInputEditTextSenha)
    }

    fun entrar(view: View) {
        when {
            campoNaoInvalido() -> {
                logarUsuario()
            }
        }
    }

    fun logarUsuario() {

        var email = textInputEditTextLogin.text.toString()
        var senha = textInputEditTextSenha.text.toString()

        autenticacao.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                when {
                    task.isSuccessful -> {
                        autenticaUsuario()
                    }
                    else -> {
                        var excecao = ""
                        try {
                            throw task.exception!!;
                        } catch (e: FirebaseAuthInvalidUserException) {
                            excecao = "E-mail ou senha não correspondem a um usuário cadastrado!";
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            excecao = "Usuário não está cadastrado!";
                        } catch (e: Exception) {
                            excecao = "Erro ao cadastrar usuario : " + e.message;
                            e.printStackTrace();
                        }

                        AlertDialog.Builder(this)
                            .setTitle("Autenticação")
                            .setMessage(excecao)
                            .setPositiveButton("OK") { dialog, which ->
                                dialog.dismiss()
                            }
                            .setCancelable(false)
                            .show()
                    }
                }
            }
    }

    private fun autenticaUsuario() {
        val email = textInputEditTextLogin.text.toString()
        val senha = textInputEditTextSenha.text.toString()

        var login = Login(null, "", email, senha)

        val call = RetrofitInitializer().boaViagemService().autenticaLogin(login)
        call.enqueue(object : Callback<Login> {
            override fun onFailure(call: Call<Login>, t: Throwable) {
                Log.d("Response Fail OK", t.message)
                if (t.message.equals("timeout")){
                        autenticaUsuario()
                }
            }


            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                Log.d("Response USuario OK", response.toString())
                when {
                    response.isSuccessful -> {
                        login = response.body()!!
                        salvaLoginNoIntent(login)
                    }
                }
            }
        })
    }

    private fun salvaLoginNoIntent(login: Login) {
        when {
            (login.idLogin != null) and (login != null) and (login.idLogin!! > 0) -> {
                abrirTelaPrincipal(login)
            }
        }
    }

    fun abrirTelaPrincipal(login: Login) {
        startActivity(Intent(this, PrincipalActivity::class.java).apply {
            putExtra("login", login)
        })
    }


    fun abriTelaCadastro(view: View) {
        startActivity(Intent(this, CreateLoginActivity::class.java).apply {})
    }

    private fun campoNaoInvalido(): Boolean {
        val login = textInputEditTextLogin.text.toString()
        val senha = textInputEditTextSenha.text.toString()
        return when {
            campoEhVazio(login) -> {
                textInputEditTextLogin.error = "Campo Obrigatório"
                false
            }
            campoEhVazio(senha) -> {
                textInputEditTextLogin.error = "Campo Obrigatório"
                false
            }
            else -> true
        }
    }

    private fun campoEhVazio(campo: String) = campo.isEmpty()
}
