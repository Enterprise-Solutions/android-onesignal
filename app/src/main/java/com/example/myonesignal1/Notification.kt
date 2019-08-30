package com.example.myonesignal1

import java.io.Serializable

data class Notification(var title: String, var body: String, var bigPicture: String?): Serializable {
}