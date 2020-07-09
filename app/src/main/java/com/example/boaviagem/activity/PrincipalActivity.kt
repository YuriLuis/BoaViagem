package com.example.boaviagem.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.boaviagem.R
import com.example.boaviagem.model.Login

class PrincipalActivity : AppCompatActivity() {

    private lateinit var imgNewTrip : ImageView
    private lateinit var imgNewGasto : ImageView
    private lateinit var imgListTrip : ImageView
    private lateinit var imgConfig : ImageView
    private lateinit var login : Login


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        pegaDadosIntentLogin()
        initComponents()
        clickEventNewGasto()
        clickEventNewTrip()
        clickEventListTrips()
    }

    private fun pegaDadosIntentLogin(){
        var bundle: Bundle? = intent.extras
        if (bundle != null) {
            login = bundle.getSerializable("login") as Login
        }
    }


    private fun initComponents(){
        imgNewTrip = findViewById(R.id.newTrip)
        imgNewGasto = findViewById(R.id.novoGastoImage)
        imgListTrip = findViewById(R.id.listTrip)
        imgConfig = findViewById(R.id.config)
    }

    private fun clickEventNewGasto(){
        imgNewGasto.setOnClickListener{
            startActivity(Intent(this, CadastraGastoActivity::class.java))
        }
    }

    private fun clickEventNewTrip(){
        imgNewTrip.setOnClickListener{
            startActivity(Intent(this, CadastraTripActivity::class.java)
                .putExtra("login", login))
        }
    }

    private fun clickEventListTrips(){
        imgListTrip.setOnClickListener{
            startActivity(Intent(this, ListaDeViagemActivity::class.java))
        }
    }

    private fun clickEventConfiguration(){

    }
}
