package nat.pink.base

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.PersistableBundle
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import nat.pink.base.databinding.ActivityMainBinding
import nat.pink.base.setting.SettingFragment
import nat.pink.base.ui.home.HomeFragment
import nat.pink.base.ui.home.HomeViewModel
import nat.pink.base.utils.DatabaseController
import nat.pink.base.utils.MyContextWrapper
import nat.pink.base.utils.PreferenceUtil
import java.util.Locale


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val fragmentStates = ArrayList<String>()
    private var ft: FragmentTransaction? = null
    private val ctsBottomNavigation: ConstraintLayout? = null
    private val fragments: MutableList<Fragment> = ArrayList()
    private val fragmentHome: Fragment = HomeFragment()
    private var fragmentLever: Fragment? = null
    private var fragmentSetting: Fragment? = null

    private var isKeyboardVisible = false
    private var currentTab: Int = 0
    private val TAB_HOME = 1
    private val TAB_LEVEL = 2
    private val TAB_SETTING = 3

    private var retryAttempt = 0
    lateinit var fragment: Fragment
    private lateinit var viewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        setupSystemWindowInset()

        val firstTime =
            PreferenceUtil.getBoolean(applicationContext, PreferenceUtil.OPEN_APP_FIRST_TIME, true)
        if (firstTime) {
            DatabaseController.getInstance(this).initDefaultDatabase()
            var onboarding = false
            if (intent != null && intent.extras != null) {
                onboarding = intent.extras!!.getBoolean("onboarding", false)
            }
            if (!onboarding) {
                initOnboarding()
            }
        }


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val keepState = savedInstanceState?.getBoolean("keep_state",false)
//        if (keepState!= null && !keepState) {
//            return
//        }
        viewModel = ViewModelProvider(this@MainActivity).get(HomeViewModel::class.java)
        initView()
        initData()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putBoolean("keep_state", true)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    public fun isNetworkConnected(): Boolean {
        val cm: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    override fun onDestroy() {
//        binding.bannerAdView.destroy()
        super.onDestroy()
    }

    private fun initOnboarding() {
        val intent = Intent(
            this,
            MyCustomOnboarder::class.java
        )
        startActivity(intent)
        finish()
    }

    fun isKeyBoardVisible(): Boolean {
        return isKeyboardVisible
    }

    private fun initData() {
        showFragment(fragmentHome)
        val tm = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (tm.networkCountryIso.lowercase(Locale.getDefault()).contains("v1n")) {
            val intent = Intent(this, ShowWebActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initView() {
        fragments.add(fragmentHome)
        fragmentHome::class.simpleName?.let { addFragment(fragmentHome, it) }
        HomeFragment::class.simpleName?.let { fragmentStates.add(it) }
        binding.txtShowWeb.setOnClickListener {
            val intent = Intent(this, ShowWebActivity::class.java)
            startActivity(intent)
        }
    }


    fun addFragment(fmAdd: Fragment, tag: String) {
        fragmentManager.executePendingTransactions()
        if (!fmAdd.isAdded) {
            if (fmAdd is HomeFragment) {
                HomeFragment::class.simpleName?.let { fragmentStates.add(it) }
            } else if (fmAdd is SettingFragment) {
                SettingFragment::class.simpleName?.let { fragmentStates.add(it) }
            }
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.frContent, fmAdd)
            if (fmAdd is HomeFragment) {
//                transaction.addToBackStack("Add Fragment");
            }
            transaction.commit()
        }
    }

    private fun showFragment(fmShow: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.show(fmShow)
        if (fmShow is HomeFragment) {
//            transaction.addToBackStack("Show fragment");
        }
        fragmentManager.executePendingTransactions()
        for (i in fragments.indices) {
            // chỗ này đang k hiểu tại sao check isAdded false
//            if (fragments.get(i).isAdded()) {
            hideFragment(fragments[i])
            //            }
        }
        transaction.commit()
    }

    private fun hideFragment(fmHide: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.hide(fmHide)
        if (fmHide is HomeFragment) {
//            transaction.addToBackStack("Hide Fragment");
        }
        transaction.commit()
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
//        if (tag.contains(SettingFragment::class.java.simpleName)) {
//            activityResultFragment = fragment as SettingFragment
//        }
        ft = supportFragmentManager.beginTransaction()
        ft!!.replace(R.id.frContent, fragment)
        if (!fragmentStates.contains(tag)) fragmentStates.add(tag)
        ft!!.addToBackStack(tag)
        ft!!.commit()
    }

    fun goHome(tag: String) {
//        val fm: FragmentManager = supportFragmentManager
//        for (i in 1 until fm.backStackEntryCount) {
//            fm.popBackStack()
//        }

        while (fragmentStates.size > 1) {
            fragmentStates.removeAt(fragmentStates.size - 1)
            supportFragmentManager.popBackStack()
        }
        showFragment(fragmentHome)
    }

//    private fun addFragment(fmAdd: Fragment, tag: String) {
//        fragmentManager.executePendingTransactions()
//        if (!fmAdd.isAdded()) {
//            if (fmAdd is HomeFragment) {
//                fragmentStates.add(HomeFragment.TAG)
//            } else if (fmAdd is RankingFragment) {
//                fragmentStates.add(RankingFragment.TAG)
//            } else if (fmAdd is ProfileFragment) {
//                fragmentStates.add(ProfileFragment.TAG)
//            }
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.add(R.id.frContent, fmAdd)
//            if (fmAdd is HomeFragment) {
////                transaction.addToBackStack("Add Fragment");
//            }
//            transaction.commit()
//        }
//        hideOrShowBottomView(
//            tag.contains(HomeFragment::class.java.simpleName) || tag.contains(
//                ProfileFragment::class.java.getSimpleName()
//            ) || tag.contains(RankingFragment::class.java.getSimpleName())
//        )
//    }

    private fun hideOrShowBottomView(show: Boolean) {
//        if (ctsBottomNavigation != null) {
//        if (show) {
//            binding.bannerAdView.visibility = View.VISIBLE
//            if (binding.ctsBottomNavigation.visibility != View.VISIBLE)
//                binding.ctsBottomNavigation.visibility = View.VISIBLE
//        } else {
//            binding.bannerAdView.visibility = View.GONE
//            binding.ctsBottomNavigation.visibility = View.GONE
//            binding.bannerAdView.visibility = View.GONE
//        }
//        }
    }

    fun addChildFragment(fragment: Fragment, tag: String) {
        ft = supportFragmentManager.beginTransaction()
        ft!!.add(R.id.frContent, fragment)
        if (!fragmentStates.contains(tag)) fragmentStates.add(tag)
        ft!!.addToBackStack(tag)
        ft!!.commit()
    }

    override fun onBackPressed() {
        if (fragmentStates.size > 1) {
            fragmentStates.removeAt(fragmentStates.size - 1)
            supportFragmentManager.popBackStack()
            return
        } else {
            finish()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (item in supportFragmentManager.fragments) {
            item.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun attachBaseContext(newBase: Context?) {
        val english = PreferenceUtil.getString(newBase, PreferenceUtil.SETTING_ENGLISH, "")
        if (english != null)
            super.attachBaseContext(MyContextWrapper.wrap(newBase, english))
        else
            super.attachBaseContext(newBase)
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}