package toocowe.`fun`.instaclone

import android.app.Application
import android.util.Log
import com.parse.*

class SetupParse: Application() {
    override fun onCreate() {
        super.onCreate()

        Log.i("IMPORTANT","THIS IS GETTING RUN")

        // Enable Local Datastore
        Parse.enableLocalDatastore(this)

        // from server.js on aws server
        Parse.initialize(
            Parse.Configuration.Builder(applicationContext)
                .applicationId(getString(R.string.parse_app_id))  // app key
                .clientKey(getString(R.string.parse_client_key))  // master key
                .server(getString(R.string.parse_server))         // server with / on the end
                .build()
        )

//        ParseUser.enableAutomaticUser()

//        var obj = ParseObject("test")
//        with(obj) {
//            put("name","editTextUser.text.toString()")
//            saveInBackground(SaveCallback {e ->
//                if (e == null) {
//                    Log.i("success", "obj added")
//                } else {
//                    e.printStackTrace()
//                    Log.e("signup", "failed to add obj")
//                }
//
//            })
//        }


        val defaultACL = ParseACL()
        defaultACL.publicReadAccess = true
        defaultACL.publicWriteAccess = true
        ParseACL.setDefaultACL(defaultACL, true)
    }
}
