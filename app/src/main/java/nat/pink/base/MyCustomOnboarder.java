package nat.pink.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aemerse.onboard.OnboardAdvanced;
import com.aemerse.onboard.OnboardFragment;
import com.aemerse.onboard.ScreenUtils;

import nat.pink.base.onboard.language.LanguageFragment;
import nat.pink.base.utils.MyContextWrapper;
import nat.pink.base.utils.PreferenceUtil;

public class MyCustomOnboarder extends OnboardAdvanced {
    private int retryAttempt = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.transparentStatusAndNavigation(this);
        addSlide(OnboardFragment.newInstance(getString(R.string.onboard_title_1), getString(R.string.onboard_content_1),
                R.drawable.bg_onboard_1,
                R.drawable.bg_screen_activity,
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.white), 0, 0, R.drawable.bg_screen_activity));
        addSlide(OnboardFragment.newInstance(getString(R.string.onboard_title_2), getString(R.string.onboard_content_2),
                R.drawable.bg_onboard_2,
                R.drawable.bg_screen_activity,
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.white), 0, 0, R.drawable.bg_screen_activity));
        addSlide(OnboardFragment.newInstance(getString(R.string.onboard_title_3), getString(R.string.onboard_content_3),
                R.drawable.bg_onboard_3,
                R.drawable.bg_screen_activity,
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.white), 0, 0, R.drawable.bg_screen_activity));
        String language = PreferenceUtil.getString(getBaseContext(), PreferenceUtil.SETTING_ENGLISH, "");
        if ("".equals(language))
            addFragment(new LanguageFragment(true));
//        getSupportFragmentManager().beginTransaction().add(R.id.background, new LanguageFragment());

//        initInterstitialAd();
    }

    @Override
    protected void onDestroy() {

//        setLoadingAdsView(false);
        super.onDestroy();
    }

    public void removeFragLang() {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        String language = PreferenceUtil.getString(newBase, PreferenceUtil.SETTING_ENGLISH, "");
        super.attachBaseContext(MyContextWrapper.wrap(newBase, language));
    }

    @Override
    protected void onSkipPressed(@Nullable Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        showHome();

//        setLoadingAdsView(true);
//        if (interstitialAd != null) {
//            interstitialAd.loadAd();
//        } else {
//            initInterstitialAd();
//            interstitialAd.loadAd();
//        }
    }

    @Override
    protected void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        showHome();
//        setLoadingAdsView(true);
//        if (interstitialAd != null) {
//            interstitialAd.loadAd();
//        } else {
//            initInterstitialAd();
//            interstitialAd.loadAd();
//        }
    }

    private void showHome() {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle args = new Bundle();
        args.putBoolean("onboarding", true);
        intent.putExtras(args);
        PreferenceUtil.saveBoolean(getApplicationContext(), PreferenceUtil.OPEN_APP_FIRST_TIME, false);
        PreferenceUtil.saveBoolean(getApplicationContext(), PreferenceUtil.DISPLAY_ADS_AFTER_ONBOARD, true);
        startActivity(intent);
        finish();
    }

}

