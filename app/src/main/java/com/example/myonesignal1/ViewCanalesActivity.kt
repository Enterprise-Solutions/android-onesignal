package com.example.myonesignal1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

import org.json.JSONException
import org.json.JSONObject
import android.widget.AdapterView
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.onesignal.OneSignal


class ViewArtistsActivity : AppCompatActivity() {

    private var listView: ListView? = null
    private var canalesList: MutableList<Canales>? = null






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_canales_activity)

        listView = findViewById(R.id.listViewCanales) as ListView

        val listView1 = findViewById(R.id.listViewCanales) as ListView


        listView1.setClickable(true)
        listView1.setOnItemClickListener(object : AdapterView.OnItemClickListener {

           override fun onItemClick(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {

                val o = listView1.getItemAtPosition(position)




               val json2 = JSONObject()
               json2.put("ususario_id", EndPoints.USUARIO_ID)
               json2.put("canales_id", EndPoints.CANALESDATA[position].id)



               OneSignal.sendTag(EndPoints.CANALESDATA[position].nombre, "1");


               postVolley(EndPoints.URL_ADD_USUARIOS_CANAL,json2)

            }
        })


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

                        for (i in 0..array.length() - 1) {
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


    fun postVolley(url: String, params: JSONObject) {




        val request = JsonObjectRequest(Request.Method.POST,url,params,
            Response.Listener { response ->
                // Process the json
                try {
                    Log.e("Response: ",""+response)
                    Toast.makeText( applicationContext, "Enviado" + response,
                        Toast.LENGTH_LONG
                    ).show()







                }catch (e:Exception){
                    Toast.makeText(
                        applicationContext, "Error" + e,
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e("Exception: ",""+e+response)
                }

            }, Response.ErrorListener{
                // Error in request
                Log.e("Response: ",""+params)
                Log.e("Exception: ",""+it)
            })





        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,

            0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
            1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        // Add the volley post request to the request queue
        VolleySingleton.instance?.addToRequestQueue(request)
    }





}