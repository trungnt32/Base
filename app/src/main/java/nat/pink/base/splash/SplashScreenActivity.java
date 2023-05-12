package nat.pink.base.splash;


import static nat.pink.base.utils.ScreenExtKt.setupSystemWindowInset;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Timer;
import java.util.TimerTask;

import nat.pink.base.MainActivity;
import nat.pink.base.MyCustomOnboarder;
import nat.pink.base.databinding.ActivitySplashScreenBinding;
import nat.pink.base.onboard.language.LanguageFragment;
import nat.pink.base.utils.PreferenceUtil;

public class SplashScreenActivity extends AppCompatActivity {
    private ActivitySplashScreenBinding binding;
    private Timer timer;
    private boolean appInitialized = false;
    private int retryAttempt = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupSystemWindowInset(this);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initAct();
    }

    protected void initAct() {
        Handler handler = new Handler();
        Runnable update = new Runnable() {
            int progress = 0;

            public void run() {
                progress += 1;
                binding.progress.setProgress(progress);
                if (progress == 99) {
                    actionDone();
//                    initInterstitialAd();
//                    interstitialAd.loadAd();
                }
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 5, 25);

    }

    private void actionDone() {
        boolean firstTime = PreferenceUtil.getBoolean(getApplicationContext(), PreferenceUtil.OPEN_APP_FIRST_TIME, true);
        if (firstTime) {
            String language = PreferenceUtil.getString(getApplicationContext(), PreferenceUtil.SETTING_ENGLISH, "");
            if ("".equals(language)) {
                getSupportFragmentManager().beginTransaction().add(binding.languageContainer.getId(), new LanguageFragment(true)).commitAllowingStateLoss();
            } else {
                Intent mainIntent = new Intent(SplashScreenActivity.this, MyCustomOnboarder.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                finish();
            }
        } else {
            Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainIntent);
        }
    }

    private void timeout() {
        if (!appInitialized) {
            initAct();
        }
    }

    @Override
    protected void onDestroy() {
        if (timer != null)
            timer.cancel();
        super.onDestroy();
    }

}

