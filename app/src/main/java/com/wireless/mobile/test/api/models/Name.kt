package com.wireless.mobile.test.api.models

import com.google.gson.annotations.SerializedName

data class Name (

    @SerializedName("common") val common : String,
    @SerializedName("official") val official : String
)