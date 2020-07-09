package com.example.boaviagem.api

import com.example.boaviagem.model.Despesa
import com.example.boaviagem.model.Login
import com.example.boaviagem.model.Viagem
import retrofit2.Call
import retrofit2.http.*

interface IDataService {

    @GET("viagens")
    fun findAllViagens(): Call<List<Viagem>>

    @POST("viagens/salvaViagem")
    fun salvaViagem(@Body viagem: Viagem): Call<Viagem>

    @POST("viagens/deletaDespesa/{idViagem}")
    fun removeDespesaViagem(
        @Path("idViagem") idViagem: Int,
        @Body despesa: Despesa
    ): Call<Despesa>

    @POST("viagens/adicionaDespesa/{idViagem}")
    fun adicionaDespesaViagem(
        @Path("idViagem") idViagem: Int,
        @Body despesa: Despesa
    ): Call<Viagem>

    @GET("viagens/{idViagem}")
    fun findByIdViagem(@Path("idViagem") idViagem: Int?): Call<Viagem>

    @DELETE("viagens/{idViagem}")
    fun deleteViagem(@Path("idViagem") idViagem: Int): Call<Void>

    @GET("viagens/viagemPorUsuario/{{idLogin}}")
    fun buscaViagemPorUsuario(@Path("idLogin") idLogin: Int?): Call<List<Viagem>>

    @GET("logins")
    fun findAllLogins(): Call<List<Login>>

    @POST("logins/salvaLogin")
    fun adicionaLogin(@Body login: Login): Call<Login>

    @GET("logins/{idLogin}")
    fun findByIdLogin(@Path("idLogin") idLogin: Int?): Call<Login>

    @DELETE("logins/deletaLogin/{idLogin}")
    fun deletaLogin(@Path("/{idLogin}") idLogin: Int): Call<Void>

    @POST("logins/autenticaLogin")
    fun autenticaLogin(@Body login: Login): Call<Login>
}