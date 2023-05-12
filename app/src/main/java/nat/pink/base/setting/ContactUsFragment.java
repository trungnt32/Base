package nat.pink.base.setting;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import nat.pink.base.R;
import nat.pink.base.base.BaseFragment;
import nat.pink.base.databinding.FragmentAboutAppBinding;
import nat.pink.base.ui.home.HomeViewModel;

public class ContactUsFragment extends BaseFragment<FragmentAboutAppBinding, HomeViewModel> {

    private String TAG = "FragmentAboutAppBinding";

    @NonNull
    @Override
    public HomeViewModel getViewModel() {
        return new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public void initView() {
        super.initView();
        binding.llConstraint.setOnClickListener(v -> {});
        binding.llTop.ivBack.setOnClickListener(v -> {
            backstackFragment();
        });
        binding.llTop.ivTitle.setText(getResources().getText(R.string.contac_us));
        binding.llTop.ivDone.setVisibility(View.GONE);
        binding.name.setText(getResources().getText(R.string.contac_us));
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
