package nat.pink.base.setting

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.mediation.nativeAds.MaxNativeAdView
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder
import nat.pink.base.App
import nat.pink.base.MainActivity
import nat.pink.base.R
import nat.pink.base.base.BaseFragment
import nat.pink.base.base.EmptyViewModel
import nat.pink.base.data.AppPreferences
import nat.pink.base.databinding.FragmentLanguageSettingBinding
import nat.pink.base.model.Language
import nat.pink.base.onboard.language.LanguageAdapter
import nat.pink.base.utils.Const
import nat.pink.base.utils.PreferenceUtil

class LanguageFragmentSetting : BaseFragment<FragmentLanguageSettingBinding, EmptyViewModel>() {
    override val viewModel: EmptyViewModel by lazy {
        ViewModelProvider(this)[EmptyViewModel::class.java]
    }

    private var listLanguage = mutableListOf<Language>()

    private var adapterLanguage: LanguageAdapter? = null

    private lateinit var currentLanguageSelected: Language

    // AppLovin SDK
    private var nativeAdLoader: MaxNativeAdLoader? = null
    private var nativeAd: MaxAd? = null

    override fun initData() {
        super.initData()
        TAG = "LanguageFragmentSetting"
//        initNativeAd()
        AppPreferences.getCurrentLanguage()?.let {
            currentLanguageSelected = it
        } ?: kotlin.run {
            currentLanguageSelected =
                Language(getString(R.string.txt_language_en), true, "en", R.drawable.flag_en)
        }

        val english = PreferenceUtil.getString(context, PreferenceUtil.SETTING_ENGLISH, "")
        listLanguage.apply {
            add(
                Language(
                    getString(R.string.txt_language_en),
                    english.equals("en"),
                    "en",
                    R.drawable.flag_en
                )
            )
            add(
                Language(
                    getString(R.string.txt_language_chi),
                    english.equals("za"),
                    "za",
                    R.drawable.flag_cn
                )
            )
            add(
                Language(
                    getString(R.string.txt_language_ja),
                    english.equals("ja"),
                    "ja",
                    R.drawable.flag_jp
                )
            )
            add(
                Language(
                    getString(R.string.txt_language_vi),
                    english.equals("vi"),
                    "vi",
                    R.drawable.flag_vi
                )
            )
            add(
                Language(
                    getString(R.string.txt_language_spanish),
                    english.equals("es"),
                    "es",
                    R.drawable.flag_sp
                )
            )
            add(
                Language(
                    getString(R.string.txt_language_portuguese),
                    english.equals("pt"),
                    "pt",
                    R.drawable.flag_ft
                )
            )
            add(
                Language(
                    getString(R.string.txt_language_russiane),
                    false,
                    "ru",
                    R.drawable.flag_rs
                )
            )
            add(
                Language(
                    getString(R.string.txt_language_korean),
                    english.equals("ko"),
                    "ko",
                    R.drawable.flag_kr
                )
            )
            add(
                Language(
                    getString(R.string.txt_language_french),
                    english.equals("fr"),
                    "fr",
                    R.drawable.flag_fr
                )
            )
            add(
                Language(
                    getString(R.string.txt_language_german),
                    english.equals("de"),
                    "de",
                    R.drawable.flag_gr
                )
            )
            add(
                Language(
                    getString(R.string.txt_language_in),
                    english.equals("in"),
                    "in",
                    R.drawable.flag_in
                )
            )
        }
//        onLanguageSelected(1)
        AppPreferences.saveCurrentLanguage(currentLanguageSelected)
    }

    override fun initView() {
        super.initView()
        setupRecycleView()
    }

    override fun initEvent() {
        super.initEvent()
        binding.llConstraint.setOnClickListener(View.OnClickListener { })
        binding.llTop.ivBack.setOnClickListener {
            backstackFragment()
        }
        binding.llTop.ivDone.setOnClickListener {
            PreferenceUtil.saveString(
                requireContext(),
                PreferenceUtil.SETTING_ENGLISH,
                currentLanguageSelected.value
            )
            AppPreferences.saveCurrentLanguage(currentLanguageSelected)
            Handler(Looper.getMainLooper()).postDelayed({
                if (isAdded) {
                    requireActivity().startActivity(
                        Intent(
                            requireActivity(),
                            MainActivity::class.java
                        )
                    )
                    requireActivity().finish()
                }
            }, 500)
        }
    }

    private fun setupRecycleView() {
        adapterLanguage = LanguageAdapter(listLanguage, ::onLanguageSelected)
        binding.rcvEnglish.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterLanguage
        }
    }

    private fun onLanguageSelected(position: Int) {
        listLanguage.forEachIndexed { index, language ->
            if (language.isSelected) {
                language.isSelected = false
                adapterLanguage?.notifyItemChanged(index)
            }
        }

        listLanguage[position].isSelected = true
        currentLanguageSelected = listLanguage[position]
        adapterLanguage?.notifyItemChanged(position)
    }

    private fun createNativeAdView(): MaxNativeAdView {
        val binder = MaxNativeAdViewBinder.Builder(R.layout.native_custom_ads)
            .setTitleTextViewId(R.id.txt_title)
            .setBodyTextViewId(R.id.txt_body)
            .setAdvertiserTextViewId(R.id.txt_advertiser)
            .setIconImageViewId(R.id.icon_image_view)
            .setMediaContentViewGroupId(R.id.media_view_container)
            .setOptionsContentViewGroupId(R.id.ad_options_view)
            .setCallToActionButtonId(R.id.cta_button)
            .build()
        return MaxNativeAdView(binder, context)
    }

    private fun initNativeAd() {
        if (hideAds) return
        nativeAdLoader = MaxNativeAdLoader(Const.KEY_ADS_LMG, requireContext())
        nativeAdLoader!!.setRevenueListener { ad: MaxAd? ->
            Log.d(
                TAG,
                "onAdRevenuePaid: "
            )
        }
        nativeAdLoader!!.setNativeAdListener(object : MaxNativeAdListener() {
            override fun onNativeAdLoaded(maxNativeAdView: MaxNativeAdView?, maxAd: MaxAd) {
                Log.d(TAG, "onNativeAdLoaded: ")
                if (nativeAd != null) {
                    nativeAdLoader!!.destroy(nativeAd)
                }
                nativeAd = maxAd
                binding.nativeAdsLanguage.removeAllViews()
                val adView = createNativeAdView()
                // Render the ad separately
                nativeAdLoader!!.render(adView, nativeAd)
                binding.nativeAdsLanguage.addView(adView)
            }

            override fun onNativeAdLoadFailed(s: String, maxError: MaxError) {
                super.onNativeAdLoadFailed(s, maxError)
                Log.d(TAG, "onNativeAdLoadFailed: ")
            }

            @SuppressLint("InvalidAnalyticsName")
            override fun onNativeAdClicked(maxAd: MaxAd) {
                super.onNativeAdClicked(maxAd)
                Log.d(TAG, "onNativeAdClicked: ")
                App.firebaseAnalytics.logEvent("6d8677354e1b2ed4", null)
            }
        })
        Handler(Looper.getMainLooper()).postDelayed({ nativeAdLoader!!.loadAd() }, 2000)
        super.initView()
    }

    override fun onDestroyView() {
        if (nativeAd != null) {
            nativeAdLoader!!.destroy(nativeAd)
        }
        if (nativeAdLoader != null)
            nativeAdLoader!!.destroy()
        super.onDestroyView()
    }

}