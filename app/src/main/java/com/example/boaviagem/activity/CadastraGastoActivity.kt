package com.example.boaviagem.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.boaviagem.R
import com.example.boaviagem.adapter.ViagemAdapterAutoComplete
import com.example.boaviagem.config.RetrofitInitializer
import com.example.boaviagem.model.Despesa
import com.example.boaviagem.model.Viagem
import com.example.boaviagem.util.DateUtil
import com.example.boaviagem.util.Mascaras
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_lista_gastos.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CadastraGastoActivity : AppCompatActivity() {

    private lateinit var autoCompleteTipoGasto: AutoCompleteTextView
    private lateinit var autoCompleteViagem: AutoCompleteTextView
    private lateinit var botaoCancelarGasto: FloatingActionButton

    private lateinit var campoValor: TextInputEditText
    private lateinit var campoData: TextInputEditText
    private lateinit var campoDescricao: TextInputEditText
    private lateinit var campoLocal: TextInputEditText
    private lateinit var botaoAdicionaGasto: FloatingActionButton
    private lateinit var botaoExcluirGasto: FloatingActionButton

    private var listaViagens: ArrayList<Viagem> =
        mutableListOf<Viagem>() as ArrayList<Viagem>
    private var despesa: Despesa = Despesa()
    private var viagem: Viagem = Viagem()
    private var isNewVariavel = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_novo_gasto)

        recuperaDadosIntent()
        initComponentes()
        verificaSeVariavelEhEditadaOuNova()
        getViagensAPI()
        populaDropDownComTipoGastos()
        eventoClickBotaoCancelarGasto()
        eventoClickBotaoAdicionarGasto()
        eventoClickBotaoExcluirGasto()
    }

    fun recuperaDadosIntent() {
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            this.despesa = bundle.getSerializable("despesa") as Despesa
            this.viagem = bundle.getSerializable("viagem") as Viagem
        }
    }

    private fun verificaSeVariavelEhEditadaOuNova() {
        campoDescricao.setText(despesa.descricao)

        if (!despesa.descricao.isEmpty()) {
            campoData.setText(despesa.data)
            campoValor.setText(despesa.valor.toString())
            campoDescricao.setText(despesa.descricao)
            campoLocal.setText(despesa.local)
            autoCompleteTipoGasto.setText(despesa.tipo)
            autoCompleteViagem.setText(viagem.idViagem.toString())
            populaDropDownComViagens(listaViagens)
            //atualizaRecyclerViewVariaveis()
            botaoAdicionaGasto.visibility = View.VISIBLE
            botaoCancelarGasto.visibility = View.VISIBLE
            /**EDITAR*/
            botaoAdicionaGasto.setImageResource(R.drawable.ic_baseline_check_24)
        } else {
            botaoExcluirGasto.visibility = View.INVISIBLE
            botaoCancelarGasto.visibility = View.VISIBLE
            isNewVariavel = true
        }

    }

    private fun populaDropDownComTipoGastos() {
        val tipoGasto = arrayOf("Alimentação", "Transporte", "Hospedagem")
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.dropdown_menu_popup_item, tipoGasto)
        autoCompleteTipoGasto.setAdapter(adapter)
    }

    private fun getViagensAPI() {
        val call = RetrofitInitializer().boaViagemService().findAllViagens()
        call.enqueue(object : Callback<List<Viagem>> {
            override fun onFailure(call: Call<List<Viagem>>, t: Throwable) {
                Log.d("Response GET VIAGENS (TELA ADD GASTO) {ERRO!} ", t.message)
            }

            override fun onResponse(call: Call<List<Viagem>>, response: Response<List<Viagem>>) {
                Log.d("Response GET VIAGENS (TELA ADD GASTO) {OK}", response.toString())
                when {
                    response.isSuccessful -> {
                        val list = response.body()
                        listaViagens = list as ArrayList<Viagem>
                        populaDropDownComViagens(list)
                    }
                }
            }
        })
    }

    private fun postAdicionaGastoViagem() {
        val valor = campoValor.text.toString()
        val data = campoData.text.toString()
        val descricao = campoDescricao.text.toString()
        val local = campoLocal.text.toString()
        val idViagem = autoCompleteViagem.text.toString().toInt()
        val tipoGasto = autoCompleteTipoGasto.text.toString()

        val despesa = Despesa(
            null, tipoGasto,
            valor.toDouble(), data, descricao, local
        )

        val call = RetrofitInitializer()
            .boaViagemService()
            .adicionaDespesaViagem(
                idViagem, despesa
            )
        call.enqueue(object : Callback<Viagem> {
            override fun onFailure(call: Call<Viagem>, t: Throwable) {
                Log.d("Response POST ADD GASTO P/ VIAGEM (TELA ADD GASTO) {ERRO!} ", t.message)
            }

            override fun onResponse(call: Call<Viagem>, response: Response<Viagem>) {
                Log.d(
                    "Response POST ADD GASTO P/ VIAGEM (TELA ADD GASTO) {OK}",
                    response.toString()
                )
                when {
                    response.isSuccessful -> {

                    }
                }
            }

        })
    }

    private fun atualizaGastoViagem() {
        val valor = campoValor.text.toString()
        val data = campoData.text.toString()
        val descricao = campoDescricao.text.toString()
        val local = campoLocal.text.toString()
        val idViagem = autoCompleteViagem.text.toString().toInt()
        val tipoGasto = autoCompleteTipoGasto.text.toString()

        val despesa = Despesa(
            this.despesa.idDespesa, tipoGasto,
            valor.toDouble(), data, descricao, local
        )

        val call = RetrofitInitializer()
            .boaViagemService()
            .adicionaDespesaViagem(
                idViagem, despesa
            )
        call.enqueue(object : Callback<Viagem> {
            override fun onFailure(call: Call<Viagem>, t: Throwable) {
                Log.d("Response POST ADD GASTO P/ VIAGEM (TELA ADD GASTO) {ERRO!} ", t.message)
            }

            override fun onResponse(call: Call<Viagem>, response: Response<Viagem>) {
                Log.d(
                    "Response POST ADD GASTO P/ VIAGEM (TELA ADD GASTO) {OK}",
                    response.toString()
                )
                when {
                    response.isSuccessful -> {

                    }
                }
            }
        })
    }

    private fun deleteGastoAPI() {
        val idViagem = autoCompleteViagem.text.toString().toInt()
        val call = RetrofitInitializer()
            .boaViagemService()
            .removeDespesaViagem(
                idViagem, despesa
            )
        call.enqueue(object : Callback<Despesa> {
            override fun onFailure(call: Call<Despesa>, t: Throwable) {}

            override fun onResponse(call: Call<Despesa>, response: Response<Despesa>) {
                when {
                    response.isSuccessful -> {
                    }
                }
            }
        })
    }

    private fun populaDropDownComViagens(list: ArrayList<Viagem>) {
        val adapter: ViagemAdapterAutoComplete =
            ViagemAdapterAutoComplete(
                this,
                R.layout.dropdown_menu_popup_item,
                R.id.textDropdown,
                list as MutableList<Viagem>
            )
        autoCompleteViagem.setAdapter(adapter)
    }

    private fun initComponentes() {
        botaoCancelarGasto = findViewById(R.id.floatingActionButtonCancelarGasto)
        autoCompleteTipoGasto = findViewById(R.id.autoCompleteTextViewTipoGasto)
        autoCompleteViagem = findViewById(R.id.autoCompleteTextViewViagem)
        campoValor = findViewById(R.id.inputTextValorGasto)
        campoData = findViewById(R.id.textInputData)
        campoDescricao = findViewById(R.id.textInputDescricaoGasto)
        campoLocal = findViewById(R.id.textInputLocal)
        botaoAdicionaGasto = findViewById(R.id.floatActionButtonAdicionaGasto)
        botaoExcluirGasto = findViewById(R.id.floatingActionButtonExcluir)


        campoData.setText(DateUtil.dataAtual())
        Mascaras.adicionaMascaraData(campoData)
    }

    private fun eventoClickBotaoCancelarGasto() {
        botaoCancelarGasto.setOnClickListener {
            finish()
        }
    }

    private fun eventoClickBotaoAdicionarGasto() {
        botaoAdicionaGasto.setOnClickListener {
            if (isNewVariavel) {
                if (validaCampos()) {
                    postAdicionaGastoViagem()
                    finish()
                }
            } else {
                if (validaCampos()) {
                    atualizaGastoViagem()
                    finish()
                }
            }
        }
    }

    private fun eventoClickBotaoExcluirGasto() {
        botaoExcluirGasto.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Excluir Despesa?")
                .setMessage("Deseja realmente excluir a despesa?")
                .setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                    //deleteGastoAPI()
                    dialog.dismiss()
                    deleteGastoAPI()
                    finish()
                }
                .setNegativeButton("Não") { dialog, which ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .show()

        }
    }

    private fun validaCampos(): Boolean {
        val valor = campoValor.text.toString()
        val data = campoData.text.toString()
        val descricao = campoDescricao.text.toString()
        val local = campoLocal.text.toString()
        when {
            campoEhVazio(valor) -> {
                campoValor.error = getString(R.string.campoObrigatorio)
                return false
            }
            tipoGastoInvalido() -> {
                Toast.makeText(this, getString(R.string.tipoGastoErro), Toast.LENGTH_LONG).show();
                return false
            }
            campoEhVazio(data) -> {
                campoData.error = getString(R.string.campoObrigatorio)
                return false
            }
            campoEhVazio(descricao) -> {
                campoDescricao.error = getString(R.string.campoObrigatorio)
                return false
            }
            campoEhVazio(local) -> {
                campoLocal.error = getString(R.string.campoObrigatorio)
                return false
            }

            viagemNaoFoiSelecionada() -> {
                Toast.makeText(this, getString(R.string.informeViagemErro), Toast.LENGTH_LONG)
                    .show();
                return false
            }

            else -> return true
        }
    }

    private fun campoEhVazio(campo: String): Boolean {
        return campo.isNullOrEmpty()
    }

    private fun tipoGastoInvalido(): Boolean {
        return autoCompleteTipoGasto.text.toString() == ""
    }

    private fun viagemNaoFoiSelecionada(): Boolean {
        return autoCompleteViagem.text.toString() == ""
    }

}

