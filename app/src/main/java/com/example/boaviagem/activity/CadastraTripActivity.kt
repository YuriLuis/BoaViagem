package com.example.boaviagem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.boaviagem.R
import com.example.boaviagem.config.RetrofitInitializer
import com.example.boaviagem.model.Login
import com.example.boaviagem.model.Viagem
import com.example.boaviagem.util.DateUtil
import com.example.boaviagem.util.Mascaras
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastraTripActivity : AppCompatActivity() {

    //Front-end
    private lateinit var campoDestino: TextInputEditText
    private lateinit var radioGroupTipoViagem: RadioGroup
    private lateinit var radioButtonLazer: RadioButton
    private lateinit var radioButtonNegocio: RadioButton
    private lateinit var campoDataChegada: EditText
    private lateinit var campoDataPartida: EditText
    private lateinit var campoOrcamento: TextInputEditText
    private lateinit var campoQuantidadePessoa: EditText
    private lateinit var botaoAdicionarViagem: FloatingActionButton
    private lateinit var botaoCancelarViagem: FloatingActionButton

    //Back-end
    private var tipoViagem: Int = 0
    private lateinit var login: Login
    private var listaUsuario: ArrayList<Login> =
        mutableListOf<String>() as ArrayList<Login>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_trip)

        pegaDadosIntentLogin()
        initComponents()
        eventoClickbotaoAdicionarDespesa()
    }

    private fun pegaDadosIntentLogin() {
        var bundle: Bundle? = intent.extras
        when {
            bundle != null -> {
                login = bundle.getSerializable("login") as Login
            }
        }
    }

    private fun initComponents() {
        campoDestino = findViewById(R.id.editTextDestino)
        radioGroupTipoViagem = findViewById(R.id.radioGroupTipoViagem)
        radioButtonLazer = findViewById(R.id.radioButtonLazer)
        radioButtonNegocio = findViewById(R.id.radioButtonNegocio)
        campoDataChegada = findViewById(R.id.editTextDataChegada)
        campoDataPartida = findViewById(R.id.editTextDataPartida)
        campoOrcamento = findViewById(R.id.textInputEditTextOrcamento)
        campoQuantidadePessoa = findViewById(R.id.editTextQuantidadePessoas)
        botaoAdicionarViagem = findViewById(R.id.floatActionButtonAdicionaTrip)
        botaoCancelarViagem = findViewById(R.id.floatingActionButtonCancelarViagem)

        campoDataChegada.setText(DateUtil.dataAtual())
        Mascaras.adicionaMascaraData(campoDataChegada)
        Mascaras.adicionaMascaraData(campoDataPartida)
    }

    private fun validaCampos(): Boolean {
        when {
            campoEhVazio(campoDestino.text.toString()) -> {
                campoDestino.error = getString(R.string.campoObrigatorio)
                return false
            }
            tipoClienteEhInvalido() -> {
                Toast.makeText(this, getString(R.string.tipoViagemErro), Toast.LENGTH_LONG).show();
                return false
            }
            campoEhVazio(campoDataChegada.text.toString()) -> {
                campoDataChegada.error = getString(R.string.campoObrigatorio)
                return false
            }
            campoEhVazio(campoOrcamento.text.toString()) -> {
                campoOrcamento.error = getString(R.string.campoObrigatorio)
                return false
            }
            campoEhVazio(campoQuantidadePessoa.text.toString()) -> {
                campoQuantidadePessoa.error = getString(R.string.campoObrigatorio)
                return false
            }
            else -> return true
        }
    }

    private fun campoEhVazio(campo: String): Boolean {
        return campo.isNullOrEmpty()
    }

    private fun viagemEhLazer(): Boolean {
        return radioButtonLazer.isChecked
    }

    private fun viagemEhNegocios(): Boolean {
        return radioButtonNegocio.isChecked
    }

    private fun verificaTipoViagem() {
        when {
            viagemEhLazer() -> {
                this.tipoViagem = 1 // Viagem é LAzer
            }
            viagemEhNegocios() -> {
                this.tipoViagem = 2 // Viagem é Negócio!
            }
            else -> {
                this.tipoViagem = 0 // Não selecionado
            }
        }
    }

    private fun postViagemAPI() {
        val destino = campoDestino.text.toString()
        val dataChegada = campoDataChegada.text.toString()
        val dataPartida = campoDataPartida.text.toString()
        val orcamento = campoOrcamento.text.toString().toDouble()
        val quantidadePessoa = campoOrcamento.text.toString().toInt()

        val viagem = Viagem(
            null, destino, null, tipoViagem, dataChegada,
            dataPartida, orcamento, quantidadePessoa, login
        )

        val call = RetrofitInitializer().boaViagemService().salvaViagem(viagem)
        call.enqueue(object : Callback<Viagem> {
            override fun onFailure(call: Call<Viagem>, t: Throwable) {
                Log.d("Response post viagem Fail", t.message)
            }

            override fun onResponse(call: Call<Viagem>, response: Response<Viagem>) {
                Log.d("Response post viagem OK", response.toString())
                if (response.isSuccessful){
                    finish()
                }
            }

        })
    }

    private fun tipoClienteEhInvalido(): Boolean {
        verificaTipoViagem()
        return this.tipoViagem == 0
    }

    private fun eventoClickbotaoAdicionarDespesa() {
        botaoAdicionarViagem.setOnClickListener {
            when {
                validaCampos() -> {
                    postViagemAPI()
                }
            }
        }
    }
}