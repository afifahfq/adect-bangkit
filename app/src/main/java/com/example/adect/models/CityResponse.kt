package com.example.adect.models

import com.google.gson.annotations.SerializedName

data class CityResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("curr_val")
	val currVal: String? = null,

	@field:SerializedName("results")
	val results: List<CitiesItem?>? = null
)

data class CitiesItem(

	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("key")
	val key: String? = null
)
