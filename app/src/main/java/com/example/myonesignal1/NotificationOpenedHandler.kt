package com.example.myonesignal1

import android.content.Intent
import android.util.Log
import com.onesignal.OSNotificationAction
import com.onesignal.OSNotificationOpenResult
import com.onesignal.OneSignal


class NotificationOpenedHandler : OneSignal.NotificationOpenedHandler {


    // This fires when a notification is opened by tapping on it.
    override fun notificationOpened(result: OSNotificationOpenResult) {
        val actionType = result.action.type
        val data = result.notification.payload.additionalData

        var notification = Notification(
            result.notification.payload.title,
            result.notification.payload.body,
            result.notification.payload.bigPicture
        )
        println("JSON notificacion: " + notification.toString())

        val customKey = data?.optString("customkey", null)
        if (customKey != null)
            Log.i("OneSignalExample", "customkey set with value: $customKey")

        if (actionType == OSNotificationAction.ActionType.ActionTaken)
            Log.i("OneSignalExample", "Button pressed with id: $result.action.actionID")

        // The following can be used to open an Activity of your choice.
        //   Replace MainActivity with your own Activity class.

        val intent = Intent(VolleySingleton.instance, NotificationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("notificacion",notification)

        VolleySingleton.instance?.startActivity(intent);


        // Add the following to your AndroidManifest.xml to prevent the launching of your main Activity
        //  if you are calling startActivity above.
        /*
        <application ...>
          <meta-data android:name="com.onesignal.NotificationOpened.DEFAULT" android:value="DISABLE" />
        </application>
           */
    }
}