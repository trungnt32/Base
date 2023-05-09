package nat.pink.base.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder;
import java.util.ArrayList;

import nat.pink.base.App;
import nat.pink.base.R;
import nat.pink.base.base.BaseFragment;
import nat.pink.base.databinding.CustomViewpagerGuideBinding;
import nat.pink.base.databinding.FragmentGuideBinding;
import nat.pink.base.ui.home.HomeViewModel;
import nat.pink.base.utils.Const;

public class GuideFragment extends BaseFragment<FragmentGuideBinding, HomeViewModel> {
    GuideAdapter guideAdapter;
    ViewPager viewPager;
    ArrayList<Guide> guides = new ArrayList<>();

    // AppLovin SDK
    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd nativeAd;
    private String TAG = "GuideFragment";

    @NonNull
    @Override
    public HomeViewModel getViewModel() {
        return new ViewModelProvider(this).get(HomeViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentGuideBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initData();
        initEvent();
    }

    @Override
    public void initEvent() {
        super.initEvent();
        setTAG("GuideFragment");
        binding.llTop.ivTitle.setText(getResources().getText(R.string.guide));
        binding.llTop.ivDone.setVisibility(View.GONE);
        binding.llConstraint.setOnClickListener(v -> {});
        binding.llTop.ivBack.setOnClickListener(v -> backstackFragment());
        binding.nextPagerNumber.setText(1 + "/" + guides.size());
        binding.ivBacknew.setOnClickListener(v -> {
            int countBack = Math.max(viewPager.getCurrentItem() - 1, 0);
            reloadData(countBack);
            viewPager.setCurrentItem(countBack);
        });
        binding.ivNext.setOnClickListener(v -> {
            int countNext = Math.min(viewPager.getCurrentItem() + 1, guides.size() - 1);
            reloadData(countNext);
            viewPager.setCurrentItem(countNext);
        });
    }

    @Override
    public void initView() {
        super.initView();
    }

    private void reloadData(int number){
        binding.nextPagerNumber.setText(number + 1 + "/" + guides.size());
    }

    @Override
    public void initData() {
        super.initData();
        guides.add(new Guide(R.drawable.bg_guide_1, getString(R.string.guide_info_1)));
        guides.add(new Guide(R.drawable.bg_guide_2, getString(R.string.guide_info_2)));
        guides.add(new Guide(R.drawable.bg_guide_3, getString(R.string.guide_info_3)));
        guides.add(new Guide(R.drawable.bg_guide_4, getString(R.string.guide_info_4)));
        guides.add(new Guide(R.drawable.bg_guide_5, getString(R.string.guide_info_5)));

        guideAdapter = new GuideAdapter(getChildFragmentManager(), guides);
        viewPager = binding.pager;
        viewPager.setAdapter(guideAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                reloadData(position);
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        App.firebaseAnalytics.logEvent("View_Guide",null);
//        initNativeAd();
    }

    public static class GuideAdapter extends FragmentStatePagerAdapter {

        private final ArrayList<Guide> guides;

        public GuideAdapter(@NonNull FragmentManager fm, ArrayList<Guide> guides) {
            super(fm);
            this.guides = guides;
        }

        @NonNull
        @Override
        public Fragment getItem(int i) {
            GuideInfoFragment fragment = new GuideInfoFragment(guides.get(i));
            Bundle args = new Bundle();
            args.putInt(GuideInfoFragment.ARG_OBJECT, i + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return guides != null ? guides.size() : 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }

    public static class Guide {
        private final int image;
        private final String text;

        public Guide(int image, String text) {
            this.image = image;
            this.text = text;
        }

        public int getImage() {
            return image;
        }

        public String getText() {
            return text;
        }
    }

    public static class GuideInfoFragment extends Fragment {
        public static final String ARG_OBJECT = "object";
        private CustomViewpagerGuideBinding binding;

        private final Guide guide;

        public GuideInfoFragment(Guide guide) {
            this.guide = guide;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            binding = CustomViewpagerGuideBinding.inflate(inflater, container, false);
            return binding.getRoot();
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            binding.title.setText(guide.getText());
            binding.image.setImageResource(guide.getImage());
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
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
                binding.nativeAdsGuide.removeAllViews();
                MaxNativeAdView adView = createNativeAdView();
                // Render the ad separately
                nativeAdLoader.render(adView, nativeAd);
                binding.nativeAdsGuide.addView(adView);
                binding.nativeAdsGuide.setBackgroundColor(Color.WHITE);
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
