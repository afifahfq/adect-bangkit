package com.example.adect.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.adect.api.ApiConfig
import com.example.adect.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HospitalViewModel: ViewModel() {
    val loadingStatus = MutableLiveData<Boolean>()
    val mHospitals = MutableLiveData<ArrayList<Hospital>>()
    val Hospitals = ArrayList<Hospital>()

    val mProvinces = MutableLiveData<ArrayList<String>>()
    val Provinces = ArrayList<String>()

    val mCities = MutableLiveData<ArrayList<String>>()
    val Cities = ArrayList<String>()

    fun getHospitals(province: String, city: String) {
        Hospitals.clear()
        mHospitals.postValue(Hospitals)
        loadingStatus.postValue(true)

        val client = ApiConfig.getApiService("https://perludilindungi.herokuapp.com/api/").getHospitals(province, city)
        client.enqueue(object : Callback<HospitalResponse> {
            override fun onResponse(
                call: Call<HospitalResponse>,
                response: Response<HospitalResponse>
            ) {
                if (response.isSuccessful && response.body()!!.countTotal != 0) {
                    val responseBody = response.body()
                    val result = responseBody!!.data!!
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

    fun getProvinces() {
        Provinces.clear()
        mProvinces.postValue(Provinces)
        loadingStatus.postValue(true)

        val client = ApiConfig.getApiService("https://perludilindungi.herokuapp.com/api/").getProvinces()
        client.enqueue(object : Callback<ProvinceResponse> {
            override fun onResponse(
                call: Call<ProvinceResponse>,
                response: Response<ProvinceResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    val result = responseBody!!.results!!
                    for (province in result) {
                        Provinces.add(province!!.value!!)
                    }
                }
                mProvinces.postValue(Provinces)
                loadingStatus.postValue(false)
            }
            override fun onFailure(call: Call<ProvinceResponse>, t: Throwable) {
                Log.e("HospitalViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getCities(province: String) {
        Cities.clear()
        mCities.postValue(Cities)
        loadingStatus.postValue(true)

        val client = ApiConfig.getApiService("https://perludilindungi.herokuapp.com/api/").getCities(province)
        client.enqueue(object : Callback<CityResponse> {
            override fun onResponse(
                call: Call<CityResponse>,
                response: Response<CityResponse>
            ) {
                val responseBody = response.body()
                if (responseBody!!.results!!.size > 0) {
                    val result = responseBody.results!!
                    for (city in result) {
                        Cities.add(city!!.value!!)
                    }
                }
                mCities.postValue(Cities)
                loadingStatus.postValue(false)
            }
            override fun onFailure(call: Call<CityResponse>, t: Throwable) {
                Log.e("HospitalViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getListHospitals(): LiveData<ArrayList<Hospital>?> {
        return mHospitals
    }

    fun getListProvinces(): LiveData<ArrayList<String>?> {
        return mProvinces
    }

    fun getListCities(): LiveData<ArrayList<String>?> {
        return mCities
    }

    fun getStatus(): MutableLiveData<Boolean> {
        return loadingStatus
    }
}