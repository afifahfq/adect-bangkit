package com.example.adect.models

import com.google.gson.annotations.SerializedName

data class PredictResponse(
	@field:SerializedName("result_str")
	val resultStr: String? = null
)
