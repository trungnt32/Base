package nat.pink.base.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import nat.pink.base.App
import nat.pink.base.MainActivity
import nat.pink.base.MyCustomOnboarder
import nat.pink.base.databinding.ActivitySplashBinding
import nat.pink.base.utils.PreferenceUtil
import nat.pink.base.utils.setupSystemWindowInset

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private var isLoadingAds = true
    private var appInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSystemWindowInset()

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!App.getInstance().isInitializing()) {
            var delay = 700L
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                delay = 0
            }
            handleNavigate(delay)
        }
    }

    override fun onResume() {
        super.onResume()

        if (App.getInstance().isInitializing()) {
            showLoadingAds(true)
            App.getInstance().initializeEvent.observe(this) { event ->
                if (event) {
                    appInitialized = true
                    handleNavigate(0)
                }
            }

            Handler(Looper.getMainLooper()).postDelayed({
                timeout()
            }, 5000)
        }
    }

    fun showLoadingAds(visible: Boolean) {
        binding.splashLoadingAdsLayout.bringToFront()
        binding.splashLoadingAdsLayout.isVisible = visible
    }

    private fun timeout() {
        if (!appInitialized) {
            handleNavigate(0)
        }
    }

    private fun handleNavigate(delay: Long) {
        val firstTime =
            PreferenceUtil.getBoolean(applicationContext, PreferenceUtil.OPEN_APP_FIRST_TIME, true)
        if (firstTime) {
            Handler(Looper.getMainLooper()).postDelayed({
                openOnboard()
            }, delay)
        } else {
            openHome()
        }
    }

    private fun openOnboard() {
        val intent = Intent(this, MyCustomOnboarder::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun openHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}