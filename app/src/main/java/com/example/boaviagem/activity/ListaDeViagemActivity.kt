package com.example.boaviagem.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.boaviagem.adapter.AdapterViagens
import com.example.boaviagem.R
import com.example.boaviagem.model.Gasto
import com.example.boaviagem.model.Viagem
import com.example.boaviagem.tests.CarregaArrays
import com.example.boaviagem.util.RecyclerItemClickListener

class ListaDeViagemActivity : AppCompatActivity() {

    private lateinit var recyclerViewViagens : RecyclerView
    private lateinit var adapter: AdapterViagens
    var listaViagens: ArrayList<Viagem> = CarregaArrays.carregaViagem() as ArrayList<Viagem>


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_de_viagem)

        initComponentes()
        configuraAdapter(listaViagens)
        val gasto = Gasto("3", 50.00, "27/06/2020", "Uber", "Uber", "Transporte")
        listaViagens[0].adicionaGasto(gasto)
        adapter.notifyDataSetChanged()
        eventoClickRecyclerView()
    }

    private fun initComponentes(){
        recyclerViewViagens = findViewById(R.id.recyclerViewViagens)
    }

    private fun configuraAdapter(list: List<Viagem>) {
        adapter =
            AdapterViagens(list as MutableList<Viagem>)
        configuraRecyclerViewVariaveis(adapter)
    }

    private fun configuraRecyclerViewVariaveis(adapter: AdapterViagens) {
        val layout = LinearLayoutManager(this)
        this.adapter = adapter
        recyclerViewViagens.adapter = adapter
        recyclerViewViagens.layoutManager = layout
        recyclerViewViagens.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayout.VERTICAL
            )
        )
        recyclerViewViagens.setHasFixedSize(true)
    }

    private fun eventoClickRecyclerView() {
        recyclerViewViagens.addOnItemTouchListener(
            RecyclerItemClickListener(this,
                recyclerViewViagens,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onLongItemClick(view: View?, position: Int) {

                    }

                    override fun onItemClick(view: View?, position: Int) {
                        val viagem = listaViagens[position]
                        startActivity(
                            Intent(this@ListaDeViagemActivity, ListaGastosActivity::class.java)
                                .putExtra("viagem", viagem)
                        )
                    }

                    override fun onItemClick(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {

                    }

                })
        )
    }

}