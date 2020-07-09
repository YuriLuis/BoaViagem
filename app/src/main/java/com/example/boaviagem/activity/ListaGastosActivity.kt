package com.example.boaviagem.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.boaviagem.R
import com.example.boaviagem.adapter.AdapterGastos
import com.example.boaviagem.config.RetrofitInitializer
import com.example.boaviagem.model.Despesa
import com.example.boaviagem.model.Viagem
import com.example.boaviagem.util.RecyclerItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaGastosActivity : AppCompatActivity() {

    private lateinit var viagem : Viagem
    private lateinit var recyclerViewGastos : RecyclerView
    private lateinit var adapter: AdapterGastos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_gastos)

        initComponents()
        recuperaDadosIntentVariavel()
        configuraAdapter(viagem.despesa)
        eventoClickRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        atualizaRecyclerViewGasto()
    }

    private fun atualizaRecyclerViewGasto(){
        val call = RetrofitInitializer().boaViagemService().findByIdViagem(viagem.idViagem)
        call.enqueue(object : Callback<Viagem>{
            override fun onFailure(call: Call<Viagem>, t: Throwable) {

            }

            override fun onResponse(call: Call<Viagem>, response: Response<Viagem>) {
                if (response.isSuccessful){
                    var v = response.body()
                    viagem = v!!
                    configuraAdapter(viagem!!.despesa)
                    adapter.notifyDataSetChanged()
                }
            }
        })
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

    private fun configuraAdapter(list: ArrayList<Despesa>?) {
        adapter =  AdapterGastos(list as MutableList<Despesa>)
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

    private fun eventoClickRecyclerView() {
        recyclerViewGastos.addOnItemTouchListener(
            RecyclerItemClickListener(this,
                recyclerViewGastos,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onLongItemClick(view: View?, position: Int) {

                    }

                    override fun onItemClick(view: View?, position: Int) {
                       val despesa = viagem.despesa!![position]
                        startActivity(
                            Intent(this@ListaGastosActivity, CadastraGastoActivity::class.java)
                                .putExtra("despesa", despesa)
                                .putExtra("viagem", viagem)
                        )
                        Toast.makeText(this@ListaGastosActivity, "CLicou no recycler", Toast.LENGTH_LONG).show()
                    }

                    override fun onItemClick(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        Toast.makeText(this@ListaGastosActivity, "CLicou Longo no recycler", Toast.LENGTH_LONG).show()
                    }

                })
        )
    }

}