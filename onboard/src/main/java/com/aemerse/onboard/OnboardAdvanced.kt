package com.aemerse.onboard

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout

abstract class OnboardAdvanced : com.aemerse.onboard.OnboardBase() {

    override val layoutId = R.layout.onboard_intro_layout2

    @IdRes
    var backgroundResource: Int? = null
        set(value) {
            field = value
            if (field != null) {
                field?.let { backgroundFrame.setBackgroundResource(it) }
            }
        }

    var backgroundDrawable: Drawable? = null
        set(value) {
            field = value
            if (field != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    backgroundFrame.background = field
                }
            }
        }

    private lateinit var backgroundFrame: ConstraintLayout
    private lateinit var bottomBar: View
    private lateinit var skipImageButton: TextView
    private lateinit var loadingAds: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backgroundFrame = findViewById(R.id.background)
        bottomBar = findViewById(R.id.bottom)
        skipImageButton = findViewById(R.id.skip)
        loadingAds = findViewById(R.id.onboard_loading_ads_layout)
        if (isRtl) {
            skipImageButton.scaleX = -1F
        }
    }

    /**
     * Override viewpager bar color
     * @param color your color resource
     */
    fun setBarColor(@ColorInt color: Int) {
        bottomBar.setBackgroundColor(color)
    }

    /**
     * Override Skip button drawable
     * @param imageSkipButton your drawable resource
     */
    fun setImageSkipButton(imageSkipButton: Drawable) {
//        skipImageButton.setImageDrawable(imageSkipButton)
    }

    /**
     * Override next button arrow color
     *
     * @param color your color
     */
    fun setNextArrowColor(@ColorInt color: Int) {
        val nextButton = findViewById<ImageButton>(R.id.next)
        nextButton.setColorFilter(color)
    }

    /**
     * Override skip button color
     *
     * @param colorSkipButton your color resource
     */
    fun setSkipArrowColor(@ColorInt colorSkipButton: Int) {
        val skip = findViewById<ImageButton>(R.id.skip)
        skip.setColorFilter(colorSkipButton)
    }

    fun setLoadingAdsView(visible: Boolean) {
        loadingAds.bringToFront()
        loadingAds.visibility = if (visible) View.VISIBLE else View.GONE
    }
}
