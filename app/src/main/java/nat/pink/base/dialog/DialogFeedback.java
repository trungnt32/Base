package nat.pink.base.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;


import nat.pink.base.R;
import nat.pink.base.databinding.DialogFeedbackBinding;
import nat.pink.base.utils.Utils;

public class DialogFeedback extends Dialog {

    private Consumer<String> consumer;
    private DialogFeedbackBinding binding;
    private Activity context;
    Drawable draw;

    public DialogFeedback(@NonNull Activity context, int themeResId, Drawable draw, Consumer<String> consumer) {
        super(context, themeResId);
        this.consumer = consumer;
        this.context = context;
        this.draw = draw;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(draw);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        binding = DialogFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.txtCancel.setOnClickListener(v -> {
            Utils.hiddenKeyboard(context, binding.edtRename);
            dismiss();
        });
        binding.txtSent.setOnClickListener(v -> {
//            if (binding.txtError.getVisibility() == View.VISIBLE)
//                return;
            consumer.accept(binding.edtRename.getText().toString());
            dismiss();
        });
        binding.ivFeedback.setImageResource(R.drawable.ic_bg_give_us_feedback);
        binding.txtFeedback.setText(getContext().getString(R.string.please_give_us_feedback));
        binding.edtRename.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                binding.txtError.setVisibility(s.toString().equals("") ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
