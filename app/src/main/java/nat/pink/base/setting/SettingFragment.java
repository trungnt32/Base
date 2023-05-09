package nat.pink.base.setting;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.applovin.sdk.AppLovinSdk;

import nat.pink.base.App;
import nat.pink.base.BuildConfig;
import nat.pink.base.MainActivity;
import nat.pink.base.R;
import nat.pink.base.base.BaseFragment;
import nat.pink.base.databinding.FragmentSettingBinding;
import nat.pink.base.dialog.DialogFeedback;
import nat.pink.base.dialog.DialogLoading;
import nat.pink.base.dialog.DialogRateTheApp;
import nat.pink.base.retrofit.RequestAPI;
import nat.pink.base.retrofit.RetrofitClient;
import nat.pink.base.ui.home.HomeViewModel;
import nat.pink.base.utils.Const;
import retrofit2.Retrofit;

public class SettingFragment extends BaseFragment<FragmentSettingBinding, HomeViewModel> {

    protected RequestAPI requestAPI;
    private DialogLoading dialogLoading;

    @NonNull
    @Override
    public HomeViewModel getViewModel() {
        return new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public void initView() {
        super.initView();
        binding.clContacUs.setOnClickListener(v -> ((MainActivity) getActivity()).addChildFragment(new ContactUsFragment(), ContactUsFragment.class.getSimpleName()));
        binding.clLanguage.setOnClickListener(v -> ((MainActivity) getActivity()).addChildFragment(new LanguageFragmentSetting(), LanguageFragmentSetting.class.getSimpleName()));
        binding.clMoreApp.setOnClickListener(v -> ((MainActivity) getActivity()).addChildFragment(new MoreAppFragment(), MoreAppFragment.class.getSimpleName()));
        binding.clRateApp.setOnClickListener(v -> new DialogRateTheApp(requireActivity(), R.style.MaterialDialogSheet,null, s -> {
            final String appPackageName = getActivity().getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }).show());
        binding.clFeedback.setOnClickListener(v -> new DialogFeedback(requireActivity(), R.style.TransparentDialog, null, s -> {
            dialogLoading.show();
            dialogLoading.setText(getString(R.string.loading));

            getViewModel().feedback(requestAPI, s, getActivity().getPackageName(), o1 -> {
                dialogLoading.dismiss();
            });
            App.firebaseAnalytics.logEvent("Leave_Review",null);
        }).show());
        binding.clPrivacy.setOnClickListener(v -> ((MainActivity) getActivity()).addChildFragment(new PrivacyFragment(), PrivacyFragment.class.getSimpleName()));
        binding.clGuide.setOnClickListener(v -> ((MainActivity) getActivity()).addChildFragment(new GuideFragment(), GuideFragment.class.getSimpleName()));
        binding.appLovinDebug.setVisibility(BuildConfig.DEBUG ? View.VISIBLE : View.GONE);
        binding.appLovinDebug.setOnClickListener(v -> AppLovinSdk.getInstance(getContext()).showMediationDebugger());
    }

    @Override
    public void initData() {
        super.initData();
        dialogLoading = new DialogLoading(getContext(), R.style.MaterialDialogSheet, o -> {
            dialogLoading.dismiss();
        });
        Retrofit retrofit = RetrofitClient.getInstance(Const.URL_REQUEST);
        requestAPI = retrofit.create(RequestAPI.class);
        try {
            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            String version = pInfo.versionName;
            binding.txtVersion.setText(getString(R.string.version) + " " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}
