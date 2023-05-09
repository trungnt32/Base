package nat.pink.base.onboard.onboard

import android.annotation.SuppressLint
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import nat.pink.base.MainActivity
import nat.pink.base.R
import nat.pink.base.base.BaseFragment
import nat.pink.base.base.EmptyViewModel
import nat.pink.base.data.AppPreferences
import nat.pink.base.databinding.FragmentOnboardBinding
import nat.pink.base.model.PageOnboard

class OnboardFragment : BaseFragment<FragmentOnboardBinding, EmptyViewModel>() {
    override val viewModel: EmptyViewModel by lazy {
        ViewModelProvider(this)[EmptyViewModel::class.java]
    }

    private var currentPage = 0
    private var listPage = mutableListOf<Fragment>()

    @SuppressLint("ResourceType")
    override fun initData() {
        super.initData()
        listPage.apply {
            add(
                PageOnBoardFragment.newInstance(
                    PageOnboard(
                        imageDrawable = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.bg_onboard_1
                        ),
                        title = getString(R.string.onboard_title_1),
                        content = getString(R.string.onboard_content_1)
                    )
                )
            )
            add(
                PageOnBoardFragment.newInstance(
                    PageOnboard(
                        imageDrawable = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.bg_onboard_2
                        ),
                        title = getString(R.string.onboard_title_2),
                        content = getString(R.string.onboard_content_2)
                    )
                )
            )
            add(
                PageOnBoardFragment.newInstance(
                    PageOnboard(
                        imageDrawable = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.bg_onboard_3
                        ),
                        title = getString(R.string.onboard_title_3),
                        content = getString(R.string.onboard_content_3)
                    )
                )
            )
        }
        setupPager()
    }

    override fun initView() {
        super.initView()

    }

    private fun setupPager() {
//        binding.viewPager.run {
//            adapter = OnboardPagerAdapter(listPage, childFragmentManager)
//            binding.dotsIndicator.attachTo(this)
////            binding.dotsIndicator.setDotIndicatorColor(resources.getColor(R.color.color_0085FF))
//            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//                override fun onPageScrolled(
//                    position: Int,
//                    positionOffset: Float,
//                    positionOffsetPixels: Int
//                ) {
//                    Timber.e("=====> onPageScrolled $position")
//                    if (currentPage < listPage.size - 1) {
//                        binding.btnNext.text = getString(R.string.next)
//                    } else {
//                        binding.btnNext.text = getString(R.string.ok)
//                    }
//                }
//
//                override fun onPageSelected(position: Int) {
//                    currentPage = position
//                }
//
//                override fun onPageScrollStateChanged(state: Int) {}
//            })
//        }
    }

    override fun initEvent() {
        super.initEvent()
//        binding.btnNext.setOnClickListener {
//            if (currentPage < listPage.size - 1) {
//                currentPage++
//                binding.viewPager.currentItem = currentPage
//            }else{
//                AppPreferences.setShowOnBoard(false)
//                gotoMain()
//            }
//        }

        binding.btnSkip.setOnClickListener {
            AppPreferences.setShowOnBoard(false)
            gotoMain()
        }
    }


    private fun gotoMain() {
        requireActivity().run {
            Intent(this, MainActivity::class.java).apply {
            }.also {
                finish()
                startActivity(it)
            }
        }
    }
}