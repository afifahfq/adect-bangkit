package com.example.adect.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.adect.api.ApiConfig
import com.example.adect.models.PredictResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PredictViewModel: ViewModel() {
    val loadingStatus = MutableLiveData<Boolean>()
    val mPredict = MutableLiveData<String>()
    var predict = String()

    fun getPredict(photo: MultipartBody.Part) {
        mPredict.postValue(predict)
        loadingStatus.postValue(true)

        val client = ApiConfig.getApiService("https://node-latihan011-u6pn4y2cqa-uc.a.run.app/").getPredict(photo)
        client.enqueue(object : Callback<PredictResponse> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<PredictResponse>,
                response: Response<PredictResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    predict = responseBody!!.resultStr!!
                    mPredict.postValue(predict)
                }
            }
            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                Log.e("PredictViewModel", "onFailure: ${t.message}")
                predict = "failure"
                mPredict.postValue(predict)
            }
        })
    }

    fun getListPredict(): LiveData<String?> {
        return mPredict
    }

    fun getStatus(): MutableLiveData<Boolean> {
        return loadingStatus
    }
}