package nat.pink.base.onboard.moreapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.function.Consumer;

import nat.pink.base.databinding.ItemMoreAppBinding;
import nat.pink.base.model.ObjectList;

public class MoreAppAdapter extends RecyclerView.Adapter<MoreAppAdapter.ViewHolder> {

    Context context;
    List<ObjectList.Result> list;
    Consumer consumer;

    public MoreAppAdapter(Context context, List<ObjectList.Result> list, Consumer consumer) {
        this.context = context;
        this.list = list;
        this.consumer = consumer;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMoreAppBinding binding = ItemMoreAppBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemMoreAppBinding binding = holder.binding;
        ObjectList.Result item = list.get(position);
        if (item == null)
            return;
        binding.titleTextView.setText(item.getName());
        binding.bodyTextView.setText(item.getCategoryName());
        binding.capacityTextView.setText(item.getSize());
        binding.download.setOnClickListener(v -> {
            consumer.accept(item.getAppId());
        });
        Glide.with(context).load(item.getIcon()).into(binding.iconImageView);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemMoreAppBinding binding;

        public ViewHolder(@NonNull ItemMoreAppBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
