package com.example.boaviagem.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.example.boaviagem.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CadastraGastoActivity : AppCompatActivity() {

    private lateinit var autoCompleteTipoGasto : AutoCompleteTextView
    private lateinit var botaoCancelarGasto: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastra_novo_gasto)

        autoCompleteTipoGasto = findViewById(R.id.autoCompleteTextViewTipoGasto)
        populaDropDownComTodosOsEstadosDoBrasil()
        initComponentes()
        eventoClickBotaoCancelarGasto()
    }

    fun populaDropDownComTodosOsEstadosDoBrasil() {
        val item_siglasEstados = arrayOf("Alimentação", "Transporte", "Hospedagem")
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.dropdown_menu_popup_item, item_siglasEstados)
        autoCompleteTipoGasto.setAdapter(adapter)
    }

    private fun initComponentes(){
        botaoCancelarGasto = findViewById(R.id.floatingActionButtonCancelarGasto)
    }

    private fun eventoClickBotaoCancelarGasto(){
        botaoCancelarGasto.setOnClickListener {
            finish()
        }
    }

}