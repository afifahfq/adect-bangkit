package com.example.adect.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adect.R
import com.example.adect.adapter.ListHospitalAdapter
import com.example.adect.databinding.FragmentHospitalBinding
import com.example.adect.models.Hospital
import com.example.adect.viewmodels.HospitalViewModel


class HospitalFragment : Fragment() {
    private lateinit var mLiveDataList: HospitalViewModel
    private lateinit var rvHospitals: RecyclerView
    private var _binding: FragmentHospitalBinding? = null
    private val binding get() = _binding!!

    private var provinceSelect: String = "ACEH"
    private var citySelect: String = "KAB. ACEH TIMUR"
    var listProvince: ArrayList<String> = ArrayList()
    var listCity: ArrayList<String> = ArrayList()

    private lateinit var spinnerProvince: Spinner
    private lateinit var spinnerCity: Spinner
    private lateinit var btnSearchFaskes: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        val view: View = inflater.inflate(R.layout.fragment_hospital, container, false)
        _binding = FragmentHospitalBinding.inflate(inflater, container, false)

        val view: View = binding.root

        btnSearchFaskes = view.findViewById(R.id.btn_search_faskes)
        btnSearchFaskes.setOnClickListener {
            mLiveDataList.getHospitals(provinceSelect, citySelect)
        }

        spinnerProvince = view.findViewById(R.id.spinner_province)
        spinnerCity = view.findViewById(R.id.spinner_city)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvHospitals = view.findViewById(R.id.rv_hospitals)
        rvHospitals.setHasFixedSize(true)

        mLiveDataList = ViewModelProvider(this)[HospitalViewModel::class.java]
        subscribe()
        mLiveDataList.getHospitals(provinceSelect, citySelect)
        mLiveDataList.getProvinces()
    }

    private fun subscribe() {
        val statusObserver = Observer<Boolean> { aStatus ->
            showLoading(aStatus)
        }
        mLiveDataList.getStatus().observe(viewLifecycleOwner, statusObserver)

        val hospitalsObserver = Observer<ArrayList<Hospital>?> { aList ->
            showRecyclerList(aList)
        }
        mLiveDataList.getListHospitals().observe(viewLifecycleOwner, hospitalsObserver)

        val provincesObserver = Observer<ArrayList<String>?> { aList ->
            if (!aList.isEmpty()) {
                showProvinceSpinner(aList)
            }
        }
        mLiveDataList.getListProvinces().observe(viewLifecycleOwner, provincesObserver)

        val citiesObserver = Observer<ArrayList<String>?> { aList ->
            showCitySpinner(aList)
        }
        mLiveDataList.getListCities().observe(viewLifecycleOwner, citiesObserver)
    }

    private fun showRecyclerList(aList: ArrayList<Hospital>) {
        rvHospitals.layoutManager = LinearLayoutManager(activity)

        Log.i("CEKPOIN2", aList.toString())

        val listHospitalAdapter = ListHospitalAdapter(aList)
        rvHospitals.adapter = listHospitalAdapter

//        listHospitalAdapter.setOnItemClickCallback(object : ListHospitalAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: Hospital) {
////                val detailHospitalIntent = Intent(context, DetailHospitalActivity::class.java)
////                detailHospitalIntent.putExtra(DetailHospitalActivity.EXTRA_USER, data)
//
//                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
//                startActivity(browserIntent)
//            }
//        })
    }

    private fun showProvinceSpinner(aList: ArrayList<String>) {
        listProvince = aList

        val spinProvinceAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, listProvince) }
        spinnerProvince.adapter = spinProvinceAdapter

        spinnerProvince.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                Toast.makeText(context, listProvince[position], Toast.LENGTH_SHORT).show()
                provinceSelect = listProvince[position]
                mLiveDataList.getCities(provinceSelect)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun showCitySpinner(aList: ArrayList<String>) {
        listCity = aList

        val spinCityAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, listCity) }
        spinnerCity.adapter = spinCityAdapter

        spinnerCity.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                Toast.makeText(context, listProvince[position], Toast.LENGTH_SHORT).show()
                citySelect = listCity[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            val progressBar: ProgressBar = requireView().findViewById(R.id.progressBar)
            progressBar.visibility = View.VISIBLE
        } else {
            val progressBar: ProgressBar = requireView().findViewById(R.id.progressBar)
            progressBar.visibility = View.GONE
        }
    }

    companion object {
    }
}