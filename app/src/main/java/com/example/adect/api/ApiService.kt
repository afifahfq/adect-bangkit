package com.example.adect.api

import com.example.adect.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("everything")
    fun getArticles(
        @Query("q") q: String? = "+anemia",
        @Query("sortBy") sortBy: String? = "relevance",
        @Query("language") language: String? = "en",
        @Query("apiKey") apiKey: String? = "74b833108c194e9bb3edbb36b22bd8b5"
    ): Call<ArticlesResponse>

    @GET("get-faskes-vaksinasi")
    fun getHospitals(
        @Query("province") province: String? = "ACEH",
        @Query("city") city: String? = "KAB. ACEH TIMUR"
    ): Call<HospitalResponse>

    @GET("get-province")
    fun getProvinces()
    : Call<ProvinceResponse>

    @GET("get-city")
    fun getCities(
        @Query("start_id") province: String
    ): Call<CityResponse>

    @Multipart
    @POST("predict")
    fun getPredict(
        @Part photo: MultipartBody.Part
    ): Call<PredictResponse>
}