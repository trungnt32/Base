package nat.pink.base.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nat.pink.base.R;
import nat.pink.base.databinding.ItemLanguageBinding;
import nat.pink.base.utils.PreferenceUtil;

public class LanguageAdpter extends RecyclerView.Adapter<LanguageAdpter.ListWordViewHolder> {
    private Context context;
    private ArrayList<Language> listLanguage;
    private Consumer<Language> consumer;

    private List<Language> listSearch = new ArrayList<>();
    private boolean isSearch;

    public LanguageAdpter(Context context, ArrayList<Language> listLanguage, Consumer<Language> consumer) {
        this.context = context;
        this.listLanguage = listLanguage;
        this.consumer = consumer;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void search(String search) {
        isSearch = !"".equals(search);
        listSearch = listLanguage.stream().filter(listLanguage -> listLanguage.getLanguage().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
        if ("".equals(search))
            listSearch = new ArrayList<>();
        setListSearch(listSearch);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListWordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLanguageBinding itemBinding =
                ItemLanguageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ListWordViewHolder(itemBinding);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ListWordViewHolder holder, int position) {
        ItemLanguageBinding binding = holder.binding;
        Language item;
        if (listSearch.size() > 0)
            item = listSearch.get(position);
        else
            item = listLanguage.get(position);
        if (item.language == null || binding == null)
            return;

        binding.tvEnglish.setText(item.language);
        Glide.with(context).load(item.flags).into(binding.ivImage);
        String english = PreferenceUtil.getString(context, PreferenceUtil.SETTING_ENGLISH, "");
        binding.rb.setChecked(item.values.equals(english));
        binding.rb.setButtonTintList(item.values.equals(english) ? ColorStateList.valueOf(context.getColor(R.color.FF9500)) : ColorStateList.valueOf(context.getColor(R.color.white)));
        binding.rootView.setBackgroundColor(context.getColor(R.color.color_4D4C7C));
        binding.rootView.setOnClickListener(v -> {
            consumer.accept(item);
        });
    }

    @Override
    public int getItemCount() {
        return isSearch ? (listSearch != null ? listSearch.size() : 0) : (listLanguage == null ? 0 : listLanguage.size());
    }

    public class ListWordViewHolder extends RecyclerView.ViewHolder {
        private final ItemLanguageBinding binding;

        public ListWordViewHolder(@NonNull ItemLanguageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class Language {
        private String language;
        private String values;
        private int flags;

        public Language(String language, String values, int flags) {
            this.language = language;
            this.values = values;
            this.flags = flags;
        }

        public String getLanguage() {
            return language;
        }

        public String getValues() {
            return values;
        }

        public void setLanguage(String language) {
            this.language = language;
        }
    }

    public List<Language> getListSearch() {
        return listSearch;
    }

    public void setListSearch(List<Language> listSearch) {
        this.listSearch = listSearch;
    }
}
