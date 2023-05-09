package nat.pink.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.LinearLayoutManager;


import java.util.List;

import nat.pink.base.adapter.ItemTranslateAdapter;
import nat.pink.base.databinding.DialogChangeCountryBinding;
import nat.pink.base.model.ObjectCountry;

public class DialogChangeLanguage extends Dialog {

    private Consumer<ObjectCountry.Result> consumer;
    private DialogChangeCountryBinding binding;
    private ItemTranslateAdapter adapter;
    private List<ObjectCountry.Result> listObject;

    public DialogChangeLanguage(@NonNull Context context, int themeResId, List<ObjectCountry.Result> listObject, Consumer<ObjectCountry.Result> consumer) {
        super(context, themeResId);
        this.consumer = consumer;
        this.listObject = listObject;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        binding = DialogChangeCountryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.txtCancel.setOnClickListener(v -> dismiss());
        binding.txtOk.setOnClickListener(v -> {
            consumer.accept(adapter.getSelected());
            dismiss();
        });

        adapter = new ItemTranslateAdapter(getContext(), listObject);
        binding.rcvCountry.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvCountry.setAdapter(adapter);
    }
}
