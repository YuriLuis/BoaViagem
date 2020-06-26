package com.example.boaviagem.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.boaviagem.R
import com.example.boaviagem.adapter.AdapterGastos
import com.example.boaviagem.adapter.AdapterViagens
import com.example.boaviagem.model.Gasto
import com.example.boaviagem.model.Viagem

class ListaGastosActivity : AppCompatActivity() {

    private lateinit var viagem : Viagem
    private lateinit var recyclerViewGastos : RecyclerView
    private lateinit var adapter: AdapterGastos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_gastos)

        initComponents()
        recuperaDadosIntentVariavel()
        configuraAdapter(viagem.gastos)

    }

    private fun initComponents(){
        recyclerViewGastos = findViewById(R.id.recyclerViewGastos)
    }

    private fun recuperaDadosIntentVariavel() {
        var bundle: Bundle? = intent.extras
        if (bundle != null) {
            viagem = bundle.getSerializable("viagem") as Viagem
        }
    }

    private fun configuraAdapter(list: ArrayList<Gasto>?) {
        adapter =  AdapterGastos(list as MutableList<Gasto>)
        configuraRecyclerViewGastos(adapter)
    }

    private fun configuraRecyclerViewGastos(adapter: AdapterGastos) {
        val layout = LinearLayoutManager(this)
        this.adapter = adapter
        recyclerViewGastos.adapter = adapter
        recyclerViewGastos.layoutManager = layout
        recyclerViewGastos.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayout.VERTICAL
            )
        )
        recyclerViewGastos.setHasFixedSize(true)
    }

}