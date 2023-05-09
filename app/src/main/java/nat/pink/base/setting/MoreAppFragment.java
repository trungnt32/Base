package nat.pink.base.setting;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder;

import nat.pink.base.App;
import nat.pink.base.R;
import nat.pink.base.base.BaseFragment;
import nat.pink.base.databinding.FragmentMoreAppBinding;
import nat.pink.base.dialog.DialogLoading;
import nat.pink.base.model.ObjectList;
import nat.pink.base.onboard.moreapp.MoreAppAdapter;
import nat.pink.base.retrofit.RequestAPI;
import nat.pink.base.retrofit.RetrofitClient;
import nat.pink.base.ui.home.HomeViewModel;
import nat.pink.base.utils.Const;
import retrofit2.Retrofit;

public class MoreAppFragment extends BaseFragment<FragmentMoreAppBinding, HomeViewModel> {
    public static final String TAG = "HomeFragment";
    private MoreAppAdapter moreAppAdapter;
    protected RequestAPI requestAPI;

    private DialogLoading dialogLoading;

    @NonNull
    @Override
    public HomeViewModel getViewModel() {
        return new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public void initObserver() {
        super.initObserver();
    }

    @Override
    public void initView() {
        super.initView();
        setTAG("MoreAppFragment");
        binding.llConstraint.setOnClickListener(v -> {
        });
        binding.llTop.ivBack.setOnClickListener(v -> {
            backstackFragment();
        });
        dialogLoading = new DialogLoading(getContext(), R.style.MaterialDialogSheet, o -> {
            dialogLoading.dismiss();
        });
        dialogLoading.show();
        dialogLoading.setText(getString(R.string.loading));

//        App.firebaseAnalytics.logEvent("View_More_App", null);
    }

    @Override
    public void initData() {
        super.initData();
        Retrofit retrofit = RetrofitClient.getInstance(Const.URL_REQUEST_MORE_APP);
        requestAPI = retrofit.create(RequestAPI.class);
        getViewModel().getListApp(requestAPI, o -> {
            dialogLoading.dismiss();
            if (o instanceof ObjectList) {
                ObjectList objectList = (ObjectList) o;
                moreAppAdapter = new MoreAppAdapter(getContext(), objectList.getResults(), o1 -> {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + o1)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + o1)));
                    }
                });
                binding.rcvEnglish.setAdapter(moreAppAdapter);
            }
        });
        binding.llTop.ivTitle.setText(getResources().getText(R.string.more_app));
        binding.llTop.ivDone.setVisibility(View.GONE);
        binding.rcvEnglish.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvEnglish.setAdapter(moreAppAdapter);
//        reloadData();
//        initNativeAd();
    }

//    private void reloadData() {
//        imageScans = DatabaseController.getInstance(requireContext()).getImageAll();
//        showListView(imageScans != null && imageScans.size() > 0);
//        moreAppAdapter.setImageScans(imageScans);
//    }
//
//    private void showListView(boolean isShow) {
//        binding.rcvScan.setVisibility(isShow ? View.VISIBLE : View.GONE);
//        binding.llEmptyView.setVisibility(!isShow ? View.VISIBLE : View.GONE);
//    }

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
                binding.nativeAdsMoreApp.removeAllViews();
                MaxNativeAdView adView = createNativeAdView();
                // Render the ad separately
                nativeAdLoader.render(adView, nativeAd);
                binding.nativeAdsMoreApp.addView(adView);
                binding.nativeAdsMoreApp.setBackgroundColor(Color.WHITE);
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
                App.firebaseAnalytics.logEvent("ClickLMGNative", null);
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
