package nat.pink.base.setting;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder;

import nat.pink.base.App;
import nat.pink.base.R;
import nat.pink.base.base.BaseFragment;
import nat.pink.base.databinding.FragmentPrivacyBinding;
import nat.pink.base.ui.home.HomeViewModel;
import nat.pink.base.utils.Const;

public class PrivacyFragment extends BaseFragment<FragmentPrivacyBinding, HomeViewModel> {
    private static final String TAG = "PrivacyFragment";

    @NonNull
    @Override
    public HomeViewModel getViewModel() {
        return new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public void initView() {
        super.initView();
        setTAG("PrivacyFragment");
        binding.llConstraint.setOnClickListener(v -> {});
        binding.llTop.ivBack.setOnClickListener(v -> {
            backstackFragment();
        });
        binding.llTop.ivTitle.setText(getResources().getText(R.string.privacy));
        binding.llTop.ivDone.setVisibility(View.GONE);

//        loadRewardAds();
    }

//    @Override
//    public void onAdsViewStateChanged(@NonNull ADS_TYPE type, @NonNull ADS_STATE state) {
//        if (type == ADS_TYPE.REWARD) {
//            switch (state) {
//                case LOADED:
//                    showRewardVideo();
//                    break;
//                default:
//                    Log.d(TAG, "onAdsViewStateChanged: " + state);
//            }
//        }
//        super.onAdsViewStateChanged(type, state);
//    }

    @Override
    public void initData() {
        super.initData();
//        initNativeAd();
    }

    private MaxNativeAdView createNativeAdView() {
        MaxNativeAdViewBinder binder = new MaxNativeAdViewBinder.Builder(R.layout.native_custom_ads)
                .setTitleTextViewId(R.id.txt_title)
                .setBodyTextViewId(R.id.txt_body)
                .setAdvertiserTextViewId(R.id.txt_advertiser)
                .setIconImageViewId(R.id.icon_image_view)
                .setMediaContentViewGroupId(R.id.media_view_container)
                .setOptionsContentViewGroupId(R.id.ad_options_view)
                .setCallToActionButtonId(R.id.cta_button)
                .build();
        return new MaxNativeAdView(binder, getContext());
    }

    // AppLovin SDK
    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd nativeAd;

    private void initNativeAd() {
        if (getHideAds())
            return;
        nativeAdLoader = new MaxNativeAdLoader(Const.KEY_ADS_LMG, requireContext());
        nativeAdLoader.setRevenueListener(ad -> Log.d(TAG, "onAdRevenuePaid: "));
        nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
            @Override
            public void onNativeAdLoaded(@Nullable MaxNativeAdView maxNativeAdView, MaxAd maxAd) {
                Log.d(TAG, "onNativeAdLoaded: ");
                if (nativeAd != null) {
                    nativeAdLoader.destroy(nativeAd);
                }

                nativeAd = maxAd;
                binding.nativeAdsPrivacy.removeAllViews();
                MaxNativeAdView adView = createNativeAdView();
                // Render the ad separately
                nativeAdLoader.render(adView, nativeAd);
                binding.nativeAdsPrivacy.addView(adView);
                binding.nativeAdsPrivacy.setBackgroundColor(Color.WHITE);
            }

            @Override
            public void onNativeAdLoadFailed(String s, MaxError maxError) {
                super.onNativeAdLoadFailed(s, maxError);
                Log.d(TAG, "onNativeAdLoadFailed: ");
            }

            @Override
            public void onNativeAdClicked(MaxAd maxAd) {
                super.onNativeAdClicked(maxAd);
                Log.d(TAG, "onNativeAdClicked: ");
                App.firebaseAnalytics.logEvent("ClickLMGNative",null);
            }
        });

        new Handler(Looper.getMainLooper()).postDelayed(() -> nativeAdLoader.loadAd(), 2000);
        super.initView();
    }

    @Override
    public void onDestroyView() {
        if (nativeAd != null) {
            nativeAdLoader.destroy(nativeAd);
        }
        if (nativeAdLoader != null)
            nativeAdLoader.destroy();
        super.onDestroyView();
    }

}
