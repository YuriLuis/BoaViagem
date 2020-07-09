package com.example.boaviagem.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.boaviagem.adapter.AdapterViagens
import com.example.boaviagem.R
import com.example.boaviagem.config.RetrofitInitializer
import com.example.boaviagem.model.Viagem
import com.example.boaviagem.util.RecyclerItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaDeViagemActivity : AppCompatActivity() {

    private lateinit var recyclerViewViagens: RecyclerView
    private lateinit var adapter: AdapterViagens
    private var listaViagens: ArrayList<Viagem> =
        mutableListOf<Viagem>() as ArrayList<Viagem>


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_de_viagem)

        initComponentes()
        getViagensAPI()
        eventoClickRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        atualizaRecyclerViewGasto()
    }

    private fun initComponentes() {
        recyclerViewViagens = findViewById(R.id.recyclerViewViagens)
    }

    private fun configuraAdapter(list: List<Viagem>) {
        adapter =
            AdapterViagens(list as MutableList<Viagem>)
        configuraRecyclerViewVariaveis(adapter)
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
                        configuraAdapter(list)
                    }
                }
            }

        })
    }

    private fun atualizaRecyclerViewGasto(){
        val call = RetrofitInitializer().boaViagemService().findAllViagens()
        call.enqueue(object : Callback<List<Viagem>>{
            override fun onFailure(call: Call<List<Viagem>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<Viagem>>, response: Response<List<Viagem>>) {
                if (response.isSuccessful){
                    var v = response.body()
                    listaViagens = v!! as ArrayList<Viagem>
                    configuraAdapter(listaViagens)
                    adapter.notifyDataSetChanged()
                }
            }
        })
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

                        Toast.makeText(this@ListaDeViagemActivity, "CLicou no recycler", Toast.LENGTH_LONG).show()
                    }

                    override fun onItemClick(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        Toast.makeText(this@ListaDeViagemActivity, "CLicou Longo no recycler", Toast.LENGTH_LONG).show()
                    }

                })
        )
    }

}