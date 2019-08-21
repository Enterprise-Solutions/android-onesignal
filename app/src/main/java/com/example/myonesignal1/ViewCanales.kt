package com.example.myonesignal1


import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView



class CanalesList(private val context: Activity, internal var canales: List<Canales>) : ArrayAdapter<Canales>(context, R.layout.list_layout, canales) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.list_layout, null, true)

        val textViewName = listViewItem.findViewById(R.id.textViewCanalesname) as TextView



        val canales = canales[position]
        textViewName.text = canales.nombre

        return listViewItem
    }
}