package com.example.myonesignal1


import android.app.Application
import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley



class VolleySingleton : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(applicationContext)
    }
    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

//    val requestQueue: RequestQueue? = null
//        get() {
//            if (field == null) {
//                return Volley.newRequestQueue(applicationContext)
//            }
//            return field
//        }
//
//    fun <T> addToRequestQueue(request: Request<T>) {
//        request.tag = TAG
//        requestQueue?.add(request)
//    }

    companion object {
        private val TAG = VolleySingleton::class.java.simpleName
        @get:Synchronized var instance: VolleySingleton? = null
            private set
    }
}