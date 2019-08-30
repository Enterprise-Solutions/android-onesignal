package com.example.myonesignal1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.onesignal.OSSubscriptionObserver
import com.onesignal.OneSignal
import org.json.JSONObject


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

        jsonObject.put("token", token)
        jsonObject.put("origen", 'O')





        postVolley(EndPoints.URL_ADD_USUARIOS, jsonObject)


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


    fun postVolley(url: String, params: JSONObject) {


        val request = JsonObjectRequest(Request.Method.POST, url, params,
            Response.Listener { response ->
                // Process the json
                try {
                    Log.e("Response: ", "" + response)
                    Toast.makeText(
                        applicationContext, "Enviado" + response,
                        Toast.LENGTH_LONG
                    ).show()

                    EndPoints.USUARIO_ID = response.getInt("id")


                } catch (e: Exception) {
                    Toast.makeText(
                        applicationContext, "Error" + e,
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e("Exception: ", "" + e + response)
                }

            }, Response.ErrorListener {
                // Error in request
                Log.e("Response: ", "" + params)
                Log.e("Exception: ", "" + it)
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



