package nat.pink.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import nat.pink.base.MainActivity
import nat.pink.base.customView.dialog.ProgressDialog
import nat.pink.base.ui.home.HomeFragment
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VB : ViewBinding ,VM : BaseViewModel>() : Fragment() {
    abstract val viewModel : VM
    lateinit var binding: VB

    private val myDialog by lazy {
        ProgressDialog(requireContext())
    }

    // AppLovin Ads integrate
    private var intRetryAttempt = 0.0
    private var rewardRetryAttempt = 0.0
    var hideAds = false

    var TAG = "";

    var interstitialAdUnitId = "YOUR_AD_UNIT_ID"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<*>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        binding = method.invoke(null, layoutInflater, container, false) as VB
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initObserver()
        initEvent()
    }

    override fun onDestroyView() {

        super.onDestroyView()
    }

    /**
    *  Receiver data and setup default value
    * */
    open fun initData(){}

    /**
    *  Set up UI of tool bar , recycle view , button etc....
    * */
    open fun initView(){
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val consumed = onBackPressed()
                    if (!consumed) {
                        isEnabled = false
                        activity?.onBackPressed()
                    }
                }
            }
        )
    }

    /**
     *  Set up observer data from view model
     * */
    open fun initObserver(){
        viewModel.isLoading.observe(viewLifecycleOwner){ isLoading ->
            if (isLoading){
                myDialog.show()
            }else{
                myDialog.hide()
            }
        }
    }

    protected open fun replaceToFragmentHome(fragment: Fragment?, tag: String?) {
        if (activity is MainActivity) {
            val nativeLoginActivity = activity as MainActivity?
            HomeFragment::class.simpleName?.let { nativeLoginActivity!!.goHome(it) }
        }
    }

    fun showLoading(){
        if (!myDialog.progressDialog.isShowing) {
            myDialog.show()
        }
    }

    fun hideLoading(){
        myDialog.hide()
    }

    /**
    *  Set up event click
    * */
    open fun initEvent(){}

    protected open fun backstackFragment() {
        if (activity is MainActivity) {
            val mainActivity: MainActivity? = activity as MainActivity?
            mainActivity?.onBackPressed()
        }
    }

    protected open fun onBackPressed() = false


    fun showRewardVideo() {
    }

    companion object {
        private const val TAG = "BaseFragment"
    }
}