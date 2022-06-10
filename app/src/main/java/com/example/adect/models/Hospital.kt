package com.example.adect.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hospital(
    val id: Int,
    val kode: String,
    val nama: String,
    val kota: String,
    val provinsi: String,
    val alamat: String,
    val latitude: String,
    val longitude: String,
    val telp: String,
    val jenis_faskes: String
): Parcelable
