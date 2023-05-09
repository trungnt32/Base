package nat.pink.base.setting.inapp;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.function.Consumer;

import nat.pink.base.adapter.ItemInAppAdapter;
import nat.pink.base.base.BaseFragment;
import nat.pink.base.databinding.FragmentInAppPurchaseBinding;

public class InAppFragment extends BaseFragment<FragmentInAppPurchaseBinding, InAppModel> {

    private ItemInAppAdapter itemInAppAdapter;
    private Consumer consumer;

    public InAppFragment(Consumer consumer) {
        this.consumer = consumer;
    }

    @NonNull
    @Override
    public InAppModel getViewModel() {
        return new ViewModelProvider(this).get(InAppModel.class);
    }

    @Override
    public void initView() {
        super.initView();
        itemInAppAdapter = new ItemInAppAdapter(requireContext());
        binding.rcvInApp.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rcvInApp.setAdapter(itemInAppAdapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initData() {
        super.initData();
        setTAG("InAppFragment");
        getViewModel().getProducts();
        getViewModel().getUpdateProducts().observe(getViewLifecycleOwner(), inAppProductModels -> {
            itemInAppAdapter.setItems(inAppProductModels);
        });
        getViewModel().getStatusBuy().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                backstackFragment();
                setHideAds(true);
                consumer.accept(new Object());
            }
        });
    }

    @Override
    public void initEvent() {
        super.initEvent();
        binding.ivBack.setOnClickListener(v -> backstackFragment());
        binding.extContinue.setOnClickListener(v -> {
            if (itemInAppAdapter.getObjectSelect() != null && itemInAppAdapter.getObjectSelect().getId() != null)
                getViewModel().buyProduct(itemInAppAdapter.getObjectSelect().getId(), requireActivity());
        });
    }
}
