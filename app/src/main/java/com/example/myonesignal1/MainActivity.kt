package com.example.myonesignal1

import android.content.Intent
import com.onesignal.OneSignal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.Toast
import com.onesignal.OSSubscriptionObserver
import com.onesignal.OSPermissionSubscriptionState
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import org.json.JSONException
import com.android.volley.toolbox.StringRequest
import java.net.HttpURLConnection.HTTP_OK
import android.os.AsyncTask
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.*
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import javax.net.ssl.HttpsURLConnection




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .init()


        val status = OneSignal.getPermissionSubscriptionState()
        status.permissionStatus.enabled

        status.subscriptionStatus.subscribed
        status.subscriptionStatus.userSubscriptionSetting
        val user_id = status.subscriptionStatus.userId
        val token = status.subscriptionStatus.pushToken


        val jsonObject = JSONObject()
        jsonObject.put("usuario", "Prueba")
        jsonObject.put("nombre", "nombre")
        jsonObject.put("apellido", "apellido")
        jsonObject.put("one_signal", true)
        jsonObject.put("token_onesignal", token)












        postVolley(EndPoints.URL_ADD_USUARIOS,jsonObject)





        val canalesview = findViewById(R.id.buttonViewCanales) as Button
        canalesview.setOnClickListener {
            val intent = Intent(applicationContext, ViewArtistsActivity::class.java)
            startActivity(intent)
        }


        val moviesuser = findViewById(R.id.peliculas) as Button
        // set on-click listener
        moviesuser.setOnClickListener {

            OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init()

            OneSignal.addSubscriptionObserver(OSSubscriptionObserver { true });
            OneSignal.sendTag("peliculas", "1");

            // your code to perform when the user clicks on the button
            Toast.makeText(
                this@MainActivity,
                "Subscripcion realizada a Peliculas" + user_id + "Token:" + token,
                Toast.LENGTH_SHORT
            ).show()

        }

        val comicuser = findViewById(R.id.comic) as Button
        // set on-click listener
        comicuser.setOnClickListener {

            OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init()

            OneSignal.addSubscriptionObserver(OSSubscriptionObserver { true });
            OneSignal.sendTag("comic", "1");

            // your code to perform when the user clicks on the button
            Toast.makeText(this@MainActivity, "Subscripcion realizada a Comics", Toast.LENGTH_SHORT).show()

        }

        val comidasuser = findViewById(R.id.comidas) as Button
        // set on-click listener
        comidasuser.setOnClickListener {

            OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init()

            OneSignal.addSubscriptionObserver(OSSubscriptionObserver { true });
            OneSignal.sendTag("comidas", "1");

            // your code to perform when the user clicks on the button
            Toast.makeText(this@MainActivity, "Subscripcion realizada a Comidas", Toast.LENGTH_SHORT).show()

        }


    }


//    fun postJSONObject(myurl: String, parameters: JSONObject): String? {
//        var conn: HttpURLConnection? = null
//        try {
//            var response: StringBuffer? = null
//            val url =  URL(EndPoints.URL_ADD_USUARIOS)
//            conn = url.openConnection() as HttpURLConnection
//            conn.readTimeout = 10000
//            conn.connectTimeout = 15000
//            conn.setRequestProperty("Content-Type", "application/json")
//            conn.doOutput = true
//            conn.requestMethod = "POST"
//            val out = BufferedOutputStream(conn.outputStream)
//            val writer = BufferedWriter(OutputStreamWriter(out, "UTF-8"))
//            writer.write(parameters.toString())
//            writer.close()
//            out.close()
//            val responseCode = conn.responseCode
//            println("Datos:"+parameters)
//            println("responseCode$responseCode")
//            when (responseCode) {
//                200 -> {
//                    val `in` = BufferedReader(InputStreamReader(conn.inputStream))
//                    var inputLine: String
//                    response = StringBuffer()
//
//
//                    Toast.makeText(this@MainActivity, "Subscripcion Enviada" + parameters, Toast.LENGTH_SHORT).show()
//                    return response.toString();
//
//
//                }
//            }
//        } catch (ex: IOException) {
//            ex.printStackTrace()
//            Toast.makeText(this@MainActivity, "Error al enviar la subscripcion", Toast.LENGTH_SHORT).show()
//        } finally {
//            if (conn != null) {
//                try {
//                    conn.disconnect()
//                } catch (ex: Exception) {
//                    ex.printStackTrace()
//                }
//
//            }
//        }
//        return null
//    }


    private fun post(data: JSONObject) {

        try {

            val url = URL("http://192.168.1.124/apiNotification/AltaUsuario.php")


            val postDataParams = data


            Log.e("params", postDataParams.toString())

            val conn = url.openConnection() as HttpURLConnection
            conn.readTimeout = 15000
            conn.connectTimeout = 15000
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.doInput = true
            conn.doOutput = true
            Toast.makeText(
                applicationContext, "Enviado" + postDataParams.toString(),
                Toast.LENGTH_LONG
            ).show()

            Log.i("JSON", postDataParams.toString());
            val out = BufferedOutputStream(conn.outputStream)
            val os = BufferedWriter(OutputStreamWriter(out, "UTF-8"))


            os.write(postDataParams.toString());

            os.flush();
            os.close();

            Log.i("STATUS", "" + (conn.getResponseCode()));
            Log.i("MSG", conn.getResponseMessage());


        } catch (e: Exception) {

            Log.e("Fallo", "")
            Toast.makeText(
                applicationContext, e.message,
                Toast.LENGTH_LONG
            ).show()
            e.printStackTrace();
        }


    }






    fun postVolley(url: String, params: JSONObject) {




        val request = JsonObjectRequest(Request.Method.POST,url,params,
            Response.Listener<JSONObject> { response ->
                // Process the json
                try {
                    Log.e("Response: ",""+response)
                    Toast.makeText(
                        applicationContext, "Enviado" + response,
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



