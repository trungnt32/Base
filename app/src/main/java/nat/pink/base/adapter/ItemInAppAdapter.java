package nat.pink.base.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nat.pink.base.R;
import nat.pink.base.databinding.ItemInAppBinding;
import nat.pink.base.model.InAppProductModel;
import nat.pink.base.utils.InAppPurchase;

public class ItemInAppAdapter extends RecyclerView.Adapter<ItemInAppAdapter.ViewHolder> {

    private List<InAppProductModel> items = new ArrayList<>();
    private Context context;
    private InAppProductModel objectSelect;

    public ItemInAppAdapter(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setItems(List<InAppProductModel> items) {
        this.items = items;
        items.forEach(inAppProductModel -> {
            if (inAppProductModel.getId().contains(InAppPurchase.IN_APP_PROD_1y))
                objectSelect = inAppProductModel;
        });
        notifyDataSetChanged();
    }

    public InAppProductModel getObjectSelect() {
        return objectSelect;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInAppBinding binding = ItemInAppBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("StringFormatInvalid")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemInAppBinding binding = holder.binding;
        InAppProductModel inAppProductModel = items.get(position);
        if (inAppProductModel == null)
            return;
        binding.txtTitle.setText(getTitle(inAppProductModel.getId()));
        binding.txtContent.setText(getContent(inAppProductModel.getId()));
        binding.txtPrice.setText(inAppProductModel.getPrice());
        binding.txtCurrencyPrice.setText(getCurrent(inAppProductModel.getId()));
        binding.llSaleOff.setVisibility(inAppProductModel.getSalePrice() != 0 ? View.VISIBLE : View.GONE);
        binding.txtSaleOff.setText(String.format(context.getString(R.string.save_in_app), inAppProductModel.getSalePrice() + "%"));
        boolean isSelect = objectSelect != null && inAppProductModel.getId().contains(objectSelect.getId());
        binding.llContent.setBackgroundResource(isSelect ? R.drawable.bg_in_app_select : R.drawable.bg_item_in_app);

        binding.txtTitle.setTextColor(context.getColor(isSelect ? R.color.white : R.color.color_404040));
        binding.txtContent.setTextColor(context.getColor(isSelect ? R.color.white : R.color.color_666666));
        binding.txtPrice.setTextColor(context.getColor(isSelect ? R.color.white : R.color.color_E85D7D));
        binding.txtCurrencyPrice.setTextColor(context.getColor(isSelect ? R.color.white : R.color.color_666666));

        binding.llContent.setOnClickListener(v -> {
            objectSelect = inAppProductModel;
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemInAppBinding binding;

        public ViewHolder(@NonNull ItemInAppBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private String getTitle(String key) {
        if (key.contains(InAppPurchase.IN_APP_PROD_1m)) {
            return context.getString(R.string.in_app_title_1_m);
        }
        if (key.contains(InAppPurchase.IN_APP_PROD_3m)) {
            return String.format(context.getString(R.string.in_app_title_n_m), "3");
        }
        if (key.contains(InAppPurchase.IN_APP_PROD_6m)) {
            return String.format(context.getString(R.string.in_app_title_n_m), "6");
        }
        if (key.contains(InAppPurchase.IN_APP_PROD_1y)) {
            return context.getString(R.string.in_app_title_1_y);
        }
        return context.getString(R.string.in_app_title_f_r);
    }

    private String getCurrent(String key) {
        if (key.contains(InAppPurchase.IN_APP_PROD_1m) || key.contains(InAppPurchase.IN_APP_PROD_3m) || key.contains(InAppPurchase.IN_APP_PROD_6m))
            return context.getString(R.string.in_app_month);
        if (key.contains(InAppPurchase.IN_APP_PROD_1y))
            return context.getString(R.string.in_app_year);
        return context.getString(R.string.in_app_forever);
    }

    private String getContent(String key) {
        if (key.contains(InAppPurchase.IN_APP_PROD_1y)) {
            return context.getString(R.string.in_app_title_1_y);
        }
        if (key.contains(InAppPurchase.IN_APP_PROD_1m)) {
            return context.getString(R.string.in_app_title_1_m);
        }
        if (key.contains(InAppPurchase.IN_APP_PROD_3m)) {
            return String.format(context.getString(R.string.in_app_title_n_m), "3");
        }
        if (key.contains(InAppPurchase.IN_APP_PROD_6m)) {
            return String.format(context.getString(R.string.in_app_title_n_m), "6");
        }
        return context.getString(R.string.in_app_title_f_r);
    }
}
