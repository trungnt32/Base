package nat.pink.base.setting;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
