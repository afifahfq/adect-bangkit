package com.example.adect.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.adect.api.ApiConfig
import com.example.adect.models.Hospital
import com.example.adect.models.HospitalResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HospitalViewModel: ViewModel() {
    val loadingStatus = MutableLiveData<Boolean>()
    val mHospitals = MutableLiveData<ArrayList<Hospital>>()
    val Hospitals = ArrayList<Hospital>()

    fun getHospitals() {
        Hospitals.clear()
        mHospitals.postValue(Hospitals)
        loadingStatus.postValue(true)

        val client = ApiConfig.getApiService("https://virtserver.swaggerhub.com/labpro/PerluDilindungi/1.0.0/api/").getHospitals("DKI JAKARTA", "KOTA ADM. JAKARTA PUSAT")
        client.enqueue(object : Callback<HospitalResponse> {
            override fun onResponse(
                call: Call<HospitalResponse>,
                response: Response<HospitalResponse>
            ) {
                val responseBody = response.body()
                if (responseBody!!.success == true && responseBody.countTotal!! > 0) {
                    val result = responseBody.results!!
                    for (hospital in result) {
                        val curr = Hospital(
                            hospital!!.id!!.toInt(),
                            hospital.kode.toString(),
                            hospital.nama.toString(),
                            hospital.kota.toString(),
                            hospital.provinsi.toString(),
                            hospital.alamat.toString(),
                            hospital.latitude.toString(),
                            hospital.longitude.toString(),
                            hospital.telp.toString(),
                            hospital.jenisFaskes.toString()
                        )
                        Hospitals.add(curr)
                    }
                }
                mHospitals.postValue(Hospitals)
                loadingStatus.postValue(false)
            }
            override fun onFailure(call: Call<HospitalResponse>, t: Throwable) {
                Log.e("HospitalViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getListHospitals(): LiveData<ArrayList<Hospital>?> {
        return mHospitals
    }

    fun getStatus(): MutableLiveData<Boolean> {
        return loadingStatus
    }
}