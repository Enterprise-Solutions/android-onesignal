package com.example.myonesignal1

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

import org.json.JSONException
import org.json.JSONObject




class ViewArtistsActivity : AppCompatActivity() {

    private var listView: ListView? = null
    private var canalesList: MutableList<Canales>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_canales_activity)

        listView = findViewById(R.id.listViewCanales) as ListView
        canalesList = mutableListOf<Canales>()
        loadCanales()
    }

    private fun loadCanales() {
        val stringRequest = StringRequest(Request.Method.GET,
            EndPoints.URL_GET_CANALES,
            Response.Listener<String> { s ->
                try {



                    val obj = JSONObject()
                    val ja = JSONArray(s)

                    obj.put("canales", ja)
                    obj.put("error", false)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("canales")

                        for (i in 0..array.length() - 2) {
                            val objectCanales = array.getJSONObject(i)
                            val canalesget = Canales(
                                objectCanales.getString("nombre"),
                                objectCanales.getInt("id")
                            )
                            canalesList!!.add(canalesget)
                            val adapter = CanalesList(this@ViewArtistsActivity, canalesList!!)
                            listView!!.adapter = adapter


                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No se pudo encontrar los datos", Toast.LENGTH_LONG).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() })

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)
    }




}