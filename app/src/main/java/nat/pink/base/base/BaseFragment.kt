package nat.pink.base.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.applovin.mediation.*
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.mediation.ads.MaxRewardedAd
import nat.pink.base.MainActivity
import nat.pink.base.base.BaseViewModel
import nat.pink.base.customView.dialog.ProgressDialog
import nat.pink.base.ui.home.HomeFragment
import nat.pink.base.utils.InAppPurchase
import java.lang.reflect.ParameterizedType
import java.util.concurrent.TimeUnit
import kotlin.math.pow

abstract class BaseFragment<VB : ViewBinding ,VM : BaseViewModel>() : Fragment() {
    abstract val viewModel : VM
    lateinit var binding: VB

    private val myDialog by lazy {
        ProgressDialog(requireContext())
    }

    // AppLovin Ads integrate
    private var interstitialAd: MaxInterstitialAd? = null
    private var intRetryAttempt = 0.0
    private lateinit var rewardedAd: MaxRewardedAd
    private var rewardRetryAttempt = 0.0
    var hideAds = false

    var TAG = "";

    var interstitialAdUnitId = "YOUR_AD_UNIT_ID"

    enum class ADS_TYPE {
        BANNER, NATIVE, INTERSTITIAL, REWARD
    }

    enum class ADS_STATE {
        LOADED, DISPLAYED, HIDDEN, CLICKED, LOAD_FAILED, DISPLAY_FAILED, REWARD_VIDEO_STARTED, REWARD_VIDEO_COMPLETED, REWARDED, REVENUE_PAID
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<*>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        binding = method.invoke(null, layoutInflater, container, false) as VB
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        hideAds = InAppPurchase.getInstance().isPurchaseAdsRemove()
        initView()
        initData()
        initObserver()
        initEvent()
    }

    override fun onDestroyView() {
        interstitialAd?.destroy()

        super.onDestroyView()
    }

    /**
    *  Receiver data and setup default value
    * */
    open fun initData(){}

    /**
    *  Set up UI of tool bar , recycle view , button etc....
    * */
    open fun initView(){
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val consumed = onBackPressed()
                    if (!consumed) {
                        isEnabled = false
                        activity?.onBackPressed()
                    }
                }
            }
        )
    }

    /**
     *  Set up observer data from view model
     * */
    open fun initObserver(){
        viewModel.isLoading.observe(viewLifecycleOwner){ isLoading ->
            if (isLoading){
                myDialog.show()
            }else{
                myDialog.hide()
            }
        }
    }

    protected open fun replaceToFragmentHome(fragment: Fragment?, tag: String?) {
        if (activity is MainActivity) {
            val nativeLoginActivity = activity as MainActivity?
            HomeFragment::class.simpleName?.let { nativeLoginActivity!!.goHome(it) }
        }
    }

    fun showLoading(){
        if (!myDialog.progressDialog.isShowing) {
            myDialog.show()
        }
    }

    fun hideLoading(){
        myDialog.hide()
    }

    /**
    *  Set up event click
    * */
    open fun initEvent(){}

    protected open fun backstackFragment() {
        if (activity is MainActivity) {
            val mainActivity: MainActivity? = activity as MainActivity?
            mainActivity?.onBackPressed()
        }
    }

    protected open fun onBackPressed() = false


    fun loadRewardAds() {
        rewardedAd = MaxRewardedAd.getInstance("YOUR_AD_UNIT_ID", requireActivity())
        rewardedAd.setListener(object: MaxRewardedAdListener {
            override fun onAdLoaded(ad: MaxAd?) {
                rewardRetryAttempt = 0.0
                onAdsViewStateChanged(ADS_TYPE.REWARD, ADS_STATE.LOADED)
            }

            override fun onAdDisplayed(ad: MaxAd?) {
                onAdsViewStateChanged(ADS_TYPE.REWARD, ADS_STATE.DISPLAYED)
            }

            override fun onAdHidden(ad: MaxAd?) {
                onAdsViewStateChanged(ADS_TYPE.REWARD, ADS_STATE.HIDDEN)
                //TODO: May need reload new Ads for next display
//                rewardedAd.loadAd()
            }

            override fun onAdClicked(ad: MaxAd?) {
                onAdsViewStateChanged(ADS_TYPE.REWARD, ADS_STATE.CLICKED)
            }

            override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
                onAdsViewStateChanged(ADS_TYPE.REWARD, ADS_STATE.LOAD_FAILED)
                rewardRetryAttempt++
                val delayMillis = TimeUnit.SECONDS.toMillis(
                    2.0.pow(
                        6.0.coerceAtMost(rewardRetryAttempt)
                    ).toLong())

                Handler(Looper.getMainLooper()).postDelayed({ rewardedAd.loadAd() }, delayMillis)
            }

            override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
                onAdsViewStateChanged(ADS_TYPE.REWARD, ADS_STATE.DISPLAY_FAILED)
                rewardedAd.loadAd()
            }

            override fun onRewardedVideoStarted(ad: MaxAd?) {
                onAdsViewStateChanged(ADS_TYPE.REWARD, ADS_STATE.REWARD_VIDEO_STARTED)
            }

            override fun onRewardedVideoCompleted(ad: MaxAd?) {
                onAdsViewStateChanged(ADS_TYPE.REWARD, ADS_STATE.REWARD_VIDEO_COMPLETED)
            }

            override fun onUserRewarded(ad: MaxAd?, reward: MaxReward?) {
                onAdsViewStateChanged(ADS_TYPE.REWARD, ADS_STATE.REWARDED)
            }

        })
        rewardedAd.setRevenueListener {
            onAdsViewStateChanged(ADS_TYPE.REWARD, ADS_STATE.REVENUE_PAID)
        }

        rewardedAd.loadAd()
    }

    fun showRewardVideo() {
        if (rewardedAd.isReady) {
            rewardedAd.showAd()
        } else {
            rewardedAd.loadAd()
        }
    }

    open fun autoShowInterstitialAd() {}

    open fun onAdsViewStateChanged(type: ADS_TYPE, state: ADS_STATE) {}

    companion object {
        private const val TAG = "BaseFragment"
    }
}