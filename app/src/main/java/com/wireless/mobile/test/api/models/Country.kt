package com.wireless.mobile.test.api.models

import com.google.gson.annotations.SerializedName

data class Country(

    @SerializedName("name") val name: Name,
    @SerializedName("flags") val flag: Flag,
    @SerializedName("cioc") val code: String? = null,
    @SerializedName("population") val population: Long,
    @SerializedName("capital") val capital: List<String>? = null,
    @SerializedName("languages") val languages: Languages? = null,
    @SerializedName("currencies") val currencies: Currencies? = null,
    @SerializedName("borders") val borders: List<String>? = null
)