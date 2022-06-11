package com.example.adect.models

import com.google.gson.annotations.SerializedName

data class HospitalResponse(

	@field:SerializedName("count_total")
	val countTotal: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DetailItem(

	@field:SerializedName("batal_vaksin")
	val batalVaksin: Int? = null,

	@field:SerializedName("pending_vaksin_1")
	val pendingVaksin1: Int? = null,

	@field:SerializedName("pending_vaksin_2")
	val pendingVaksin2: Int? = null,

	@field:SerializedName("batch")
	val batch: String? = null,

	@field:SerializedName("divaksin_1")
	val divaksin1: Int? = null,

	@field:SerializedName("divaksin")
	val divaksin: Int? = null,

	@field:SerializedName("divaksin_2")
	val divaksin2: Int? = null,

	@field:SerializedName("kode")
	val kode: String? = null,

	@field:SerializedName("pending_vaksin")
	val pendingVaksin: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("batal_vaksin_2")
	val batalVaksin2: Int? = null,

	@field:SerializedName("batal_vaksin_1")
	val batalVaksin1: Int? = null
)

data class DataItem(

	@field:SerializedName("provinsi")
	val provinsi: String? = null,

	@field:SerializedName("kota")
	val kota: String? = null,

	@field:SerializedName("telp")
	val telp: String? = null,

	@field:SerializedName("source_data")
	val sourceData: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("kode")
	val kode: String? = null,

	@field:SerializedName("kelas_rs")
	val kelasRs: String? = null,

	@field:SerializedName("jenis_faskes")
	val jenisFaskes: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("detail")
	val detail: List<Any?>? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
