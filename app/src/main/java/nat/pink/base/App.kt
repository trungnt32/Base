package nat.pink.base

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import nat.pink.base.log.DebugTree
import nat.pink.base.log.ReleaseTree
import timber.log.Timber

class App : Application() {
    private val TAG = "DictionaryApp"

    private var isInitialized = false

    val initializeEvent: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initTimber()

        val bundle = Bundle()
        bundle.putString("app_open", "app_open")
//        firebaseAnalytics = Firebase.analytics
//        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle)
//        initFirebaseMessage()
    }

    fun isInitializing() = !isInitialized

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    companion object {
        @Volatile
        private var instance: App? = null

        @JvmStatic
        fun getInstance(): App = instance ?: synchronized(this) {
            instance ?: App().also {
                instance = it
            }
        }
        lateinit var firebaseAnalytics: FirebaseAnalytics
    }

    /**
     * TEST FCM
     * 1. copy: Token -> add firebase console
     */
    private fun initFirebaseMessage() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.e(TAG, "==> token:$token")
        })
    }
}