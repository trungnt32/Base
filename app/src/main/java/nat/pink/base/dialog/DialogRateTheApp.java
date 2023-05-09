package nat.pink.base.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;

import nat.pink.base.R;
import nat.pink.base.databinding.DialogRateTheAppBinding;

public class DialogRateTheApp extends Dialog {
    private Consumer consumer;
    private Activity context;
    private DialogRateTheAppBinding binding;
    Drawable draw;

    public DialogRateTheApp(@NonNull Activity context, int themeResId,Drawable draw, Consumer consumer) {
        super(context, themeResId);
        this.consumer = consumer;
        this.context = context;
        this.draw = draw;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        binding = DialogRateTheAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.llRoot.setBackground(draw);
        binding.llRoot.setOnClickListener(v -> dismiss());

        binding.extCancel.setOnClickListener(v -> dismiss());
        binding.extTitle.setText(getContext().getString(R.string.do_you_like_the_app));
        binding.extTextView.setText(getContext().getString(R.string.the_word_you_searched));
        binding.ivBg.setImageResource(R.drawable.bg_rate_app);
        binding.rtContent.setRating(5);
        binding.rtContent.setOnRatingChangeListener((ratingBar, rating, fromUser) -> {
            consumer.accept(new Object());
            dismiss();
        });
        
    }
}
