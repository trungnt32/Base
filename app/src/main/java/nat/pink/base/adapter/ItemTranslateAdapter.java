package nat.pink.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import nat.pink.base.R;
import nat.pink.base.databinding.ItemChooseCountryBinding;
import nat.pink.base.model.ObjectCountry;

public class ItemTranslateAdapter extends RecyclerView.Adapter<ItemTranslateAdapter.ViewHolder> {

    private List<ObjectCountry.Result> strings;
    private ObjectCountry.Result selected;
    private Context context;

    public ItemTranslateAdapter(Context context, List<ObjectCountry.Result> strings) {
        this.strings = strings;
        this.context = context;
    }

    public ObjectCountry.Result getSelected() {
        return selected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChooseCountryBinding binding = ItemChooseCountryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemChooseCountryBinding binding = holder.binding;
        ObjectCountry.Result item = strings.get(position);
        if (item == null)
            return;
        binding.txtContent.setText(item.getName());
        if (item.getCode() != null)
            Glide.with(context).load(selected != null && item.getCode().equals(selected.getCode()) ? R.drawable.ic_checkbox : R.drawable.ic_checkbox_none).into(binding.ivContent);
        binding.llContent.setOnClickListener(v -> {
            selected = item;
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return strings != null ? strings.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemChooseCountryBinding binding;

        public ViewHolder(@NonNull ItemChooseCountryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
