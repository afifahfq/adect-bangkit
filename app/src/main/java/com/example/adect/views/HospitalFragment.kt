package com.example.adect.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_hospital, container, false)
        _binding = FragmentHospitalBinding.inflate(inflater, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvHospitals = view.findViewById(R.id.rv_hospitals)
        rvHospitals.setHasFixedSize(true)

        mLiveDataList = ViewModelProvider(this)[HospitalViewModel::class.java]
        subscribe()
        mLiveDataList.getHospitals()
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
    }

    private fun showRecyclerList(aList: ArrayList<Hospital>) {
        rvHospitals.layoutManager = LinearLayoutManager(activity)

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