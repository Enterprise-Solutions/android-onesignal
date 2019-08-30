package com.example.myonesignal1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val notification = intent.extras?.get("notificacion") as? Notification
        println(notification.toString())
        notification?.let {
            cargarNotificacion(it)
        }

        println("Actividad abierta: " + notification.toString())
    }
    private fun cargarNotificacion(notification: Notification) {
        notification_title.append(notification.title)
        if(notification.bigPicture != null){
            cargarImagenUrl(notification.bigPicture)
        }
        notification_description.append(notification.body)
    }

    private fun cargarImagenUrl(image: String?) {
        Picasso.with(this).load(image).into(notification_image)
    }
}
