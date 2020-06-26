package com.example.boaviagem.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.example.boaviagem.R
import com.example.boaviagem.adapter.ViagemAdapterAutoComplete
import com.example.boaviagem.model.Viagem
import com.example.boaviagem.tests.CarregaArrays
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CadastraGastoActivity : AppCompatActivity() {

    private lateinit var autoCompleteTipoGasto : AutoCompleteTextView
    private lateinit var autoCompleteViagem : AutoCompleteTextView
    private lateinit var botaoCancelarGasto: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_novo_gasto)

        initComponentes()
        populaDropDownComTipoGastos()
        populaDropDownComViagens()
        eventoClickBotaoCancelarGasto()
    }

    private fun populaDropDownComTipoGastos() {
        val tipoGasto = arrayOf("Alimentação", "Transporte", "Hospedagem")
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.dropdown_menu_popup_item, tipoGasto)
        autoCompleteTipoGasto.setAdapter(adapter)
    }

    private fun populaDropDownComViagens() {
        val viagens = CarregaArrays.carregaViagem()
        val adapter: ViagemAdapterAutoComplete=
            ViagemAdapterAutoComplete(this, R.layout.dropdown_menu_popup_item, R.id.textDropdown,
                viagens as MutableList<Viagem>
            )
        autoCompleteViagem.setAdapter(adapter)
    }

    private fun initComponentes(){
        botaoCancelarGasto = findViewById(R.id.floatingActionButtonCancelarGasto)
        autoCompleteTipoGasto = findViewById(R.id.autoCompleteTextViewTipoGasto)
        autoCompleteViagem = findViewById(R.id.autoCompleteTextViewViagem)
    }

    private fun eventoClickBotaoCancelarGasto(){
        botaoCancelarGasto.setOnClickListener {
            finish()
        }
    }
}