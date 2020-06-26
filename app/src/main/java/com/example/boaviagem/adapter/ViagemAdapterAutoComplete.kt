package com.example.boaviagem.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.boaviagem.R
import com.example.boaviagem.model.Viagem

data class ViagemAdapterAutoComplete(val context : Activity , val resource: Int, val textView: Int, val objects: MutableList<Viagem>) :
    ArrayAdapter<Viagem>(context, resource, objects) {

    private var inflater : LayoutInflater = context.layoutInflater
    private lateinit var view : View

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        if (convertView == null) {
            view = inflater.inflate(R.layout.dropdown_menu_popup_item, parent, false)
        }
        val textView: TextView = view.findViewById(R.id.textDropdown)

        val viagem: Viagem? = getItem(position)
        if (viagem != null) {
            textView.text = viagem.destino
        } else {
            textView.text = ""
            return view
        }
        return view
    }

    override fun getCount(): Int {
        return objects.size
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        if (convertView == null) {
            view = inflater.inflate(R.layout.dropdown_menu_popup_item, parent, false)
        }
        val viagem: Viagem? = getItem(position)
        val textView: TextView = view.findViewById(R.id.textDropdown)
        if (viagem != null) {
            textView.text = viagem.destino
        } else {
            textView.text = "Oops. There was a problem"
        }
        return view
    }
}