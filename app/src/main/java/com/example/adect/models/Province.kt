package com.example.adect.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Province (
    val key: String,
    val value: String
): Parcelable