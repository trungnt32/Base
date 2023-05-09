package nat.pink.base.onboard.language

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import nat.pink.base.R
import nat.pink.base.databinding.ItemLanguageBinding
import nat.pink.base.model.Language

class LanguageAdapter(
    var listLanguage: MutableList<Language>,
    var onClickItem: (Int) -> Unit,
) : RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemLanguageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = listLanguage.size
    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val itemBinding =
            ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val item = listLanguage[position]
        binding.tvEnglish.text = item.language
        Glide.with(binding.ivImage.context).load(item.flags).into(binding.ivImage)
        binding.rb.isChecked = item.isSelected
        binding.rb.buttonTintList = if (item.isSelected) ColorStateList.valueOf(
            context!!.getColor(R.color.FF9500)
        ) else ColorStateList.valueOf(
            context!!.getColor(R.color.white)
        )
        binding.rootView.setBackgroundColor(context!!.getColor(R.color.color_4D4C7C))
//            if (item.isSelected) context!!.getColor(R.color.color_4D4C7C) else context!!.getColor(
//                R.color.color_4D4C7C
//            ))
        binding.rootView.setOnClickListener {
            onClickItem.invoke(position)
        }
    }
}
