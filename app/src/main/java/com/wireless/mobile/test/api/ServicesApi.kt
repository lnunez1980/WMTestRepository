package com.wireless.mobile.test.api

import com.wireless.mobile.test.api.models.Country
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServicesApi {

    @GET("all")
    fun getCountries(): Observable<List<Country>>

    @GET("alpha/{code}")
    fun getCountryInfo(
        @Path("code") code: String
    ): Observable<List<Country>>

    @GET("alpha")
    fun getCountriesByCodes(
        @Query("codes") codes: String
    ): Observable<List<Country>>

    @GET("name/{name}")
    fun getCountriesByName(
        @Path("name") name: String
    ): Observable<List<Country>>
}
