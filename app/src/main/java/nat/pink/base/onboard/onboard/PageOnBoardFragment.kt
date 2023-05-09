package nat.pink.base.onboard.onboard

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import nat.pink.base.base.BaseFragment
import nat.pink.base.base.EmptyViewModel
import nat.pink.base.databinding.FragmentPageOnboardBinding
import nat.pink.base.model.PageOnboard

class PageOnBoardFragment: BaseFragment<FragmentPageOnboardBinding, EmptyViewModel>() {
    override val viewModel: EmptyViewModel by lazy {
        ViewModelProvider(this)[EmptyViewModel::class.java]
    }

    private val pageOnboard by lazy { arguments?.getSerializable(PAGE_ONBOARD) as PageOnboard?}

    override fun initView() {
        super.initView()
        binding.run {
            pageOnboard?.let {
                ivOnboard.setImageDrawable(it.imageDrawable)
                tvTitle.text = it.title
                tvContent.text =  it.content
            }
        }
    }

    companion object {
        private const val PAGE_ONBOARD = "PAGE_ONBOARD"

        fun newInstance(
            pageOnboard: PageOnboard
        ): PageOnBoardFragment  = PageOnBoardFragment().apply {
            arguments = Bundle().apply {
                putSerializable(PAGE_ONBOARD,pageOnboard)
            }
        }
    }
}