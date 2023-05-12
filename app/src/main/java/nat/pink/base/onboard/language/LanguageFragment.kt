package nat.pink.base.onboard.language

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.*
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import nat.pink.base.App
import nat.pink.base.MainActivity
import nat.pink.base.R
import nat.pink.base.adapter.LanguageAdpter
import nat.pink.base.databinding.FragmentLanguageBinding
import nat.pink.base.utils.Const
import nat.pink.base.utils.PreferenceUtil
import nat.pink.base.utils.StringUtils
import java.util.*
import kotlin.system.exitProcess

class LanguageFragment() : Fragment(R.layout.fragment_language), Parcelable {
    private val TAG = "LanguageFragment"
    private var _binding: FragmentLanguageBinding? = null

    private val binding get() = _binding!!
    private var lstLanguage = ArrayList<LanguageAdpter.Language>()
    private var adapter: LanguageAdpter? = null
    private var position: Int = -1
    private var lang: LanguageAdpter.Language? = null
    private var firsTimeInApp = false

    constructor(parcel: Parcel) : this() {
        position = parcel.readInt()
    }

    constructor(arg1: Boolean) : this() {
        firsTimeInApp = arg1;
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
//        initNativeAd()

        val language =
            PreferenceUtil.getString(requireContext(), PreferenceUtil.SETTING_ENGLISH, "")
        if (language.isNotEmpty()) {
            for (i in 0 until lstLanguage.size) {
                if (language == lstLanguage[i].values) {
                    position = i
                    lang = lstLanguage[position]
                    break
                }
            }
        }

        lang?.let {
            lstLanguage[position] = it
        }

//        if (!firsTimeInApp) {
        binding.save.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("language", lang?.language)
//            App.firebaseAnalytics.logEvent("ChangeLanguage", bundle)
            PreferenceUtil.saveString(
                requireContext(),
                PreferenceUtil.SETTING_ENGLISH,
                lang?.values
            )
            Handler(Looper.getMainLooper()).postDelayed({
                /* Create an Intent that will start the Menu-Activity. */
                if (isAdded) {
                    requireActivity().startActivity(
                        Intent(
                            requireActivity(),
                            MainActivity::class.java
                        )
                    )
                    requireActivity().finish()
                }
            }, 500)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun loadEnglish() {
        val language =
            PreferenceUtil.getString(requireContext(), PreferenceUtil.SETTING_ENGLISH, "")

        val res: Resources = requireContext().resources
        val dm: DisplayMetrics = res.displayMetrics
        val conf: Configuration = res.configuration
        conf.setLocale(Locale(language)) // API 17+ only.
        res.updateConfiguration(conf, dm)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initData() {
        //set ui first time in app
        lstLanguage.add(
            LanguageAdpter.Language(
                getString(R.string.txt_language_en),
                "en",
                R.drawable.flag_en
            )
        )
        lstLanguage.add(
            LanguageAdpter.Language(
                getString(R.string.txt_language_chi),
                "za",
                R.drawable.flag_cn
            )
        )
        lstLanguage.add(
            LanguageAdpter.Language(
                getString(R.string.txt_language_ja),
                "ja",
                R.drawable.flag_jp
            )
        )
        lstLanguage.add(
            LanguageAdpter.Language(
                getString(R.string.txt_language_vi),
                "vi",
                R.drawable.flag_vi
            )
        )
        lstLanguage.add(
            LanguageAdpter.Language(
                getString(R.string.txt_language_spanish),
                "es",
                R.drawable.flag_sp
            )
        )
        lstLanguage.add(
            LanguageAdpter.Language(
                getString(R.string.txt_language_portuguese),
                "pt",
                R.drawable.flag_ft
            )
        )
        lstLanguage.add(
            LanguageAdpter.Language(
                getString(R.string.txt_language_russiane),
                "ru",
                R.drawable.flag_rs
            )
        )
        lstLanguage.add(LanguageAdpter.Language(getString(R.string.txt_language_korean), "ko", R.drawable.flag_kr))
        lstLanguage.add(LanguageAdpter.Language(getString(R.string.txt_language_french), "fr", R.drawable.flag_fr))
        lstLanguage.add(LanguageAdpter.Language(getString(R.string.txt_language_german), "de", R.drawable.flag_gr))
        lstLanguage.add(LanguageAdpter.Language(getString(R.string.txt_language_in), "in", R.drawable.flag_in))

        if (firsTimeInApp) {
//            binding.toolbar.visibility = View.GONE
            PreferenceUtil.saveString(
                requireContext(),
                PreferenceUtil.SETTING_ENGLISH,
                lstLanguage[0].values
            )
//            binding.save.setOnClickListener {
//                Handler(Looper.getMainLooper()).postDelayed({
//                    startActivity(Intent(activity, MainActivity::class.java))
//                    activity?.finish()
//                }, 500)
//            }
        }


        adapter = LanguageAdpter(requireContext(), lstLanguage) {
            lang = it
            PreferenceUtil.saveString(
                requireContext(),
                PreferenceUtil.SETTING_ENGLISH,
                lang?.values
            )
            adapter!!.notifyDataSetChanged()
        }
        binding.rcvEnglish.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvEnglish.adapter = adapter
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onQueryTextChange(qString: String): Boolean {
                adapter!!.search(qString)
                binding.ivSearch.visibility = View.GONE
                binding.llErrorSearch.visibility = View.GONE
                if (adapter!!.listSearch.size == 0 && !StringUtils.isEmpty(qString)) {
                    binding.llErrorSearch.visibility = View.VISIBLE
                    binding.extError.text =
                        requireContext().getString(R.string.search_error, qString)
                } else {
                    if (StringUtils.isEmpty(qString)) {
                        binding.ivSearch.visibility = View.VISIBLE
                    }
                    binding.llErrorSearch.visibility = View.GONE
                }
                return true
            }

            override fun onQueryTextSubmit(qString: String): Boolean {
                return true
            }
        })
        binding.back.setOnClickListener {
            exitProcess(0);
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(position)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LanguageFragment> {
        override fun createFromParcel(parcel: Parcel): LanguageFragment {
            return LanguageFragment(parcel)
        }

        override fun newArray(size: Int): Array<LanguageFragment?> {
            return arrayOfNulls(size)
        }
    }
}