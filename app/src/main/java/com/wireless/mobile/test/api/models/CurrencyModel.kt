package com.wireless.mobile.test.api.models

import com.google.gson.annotations.SerializedName

data class CurrencyModel(

    @SerializedName("name") val name : String? = null,
    @SerializedName("symbol") val symbol : String? = null

)