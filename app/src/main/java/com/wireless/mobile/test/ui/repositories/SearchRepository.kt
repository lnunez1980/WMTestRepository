package com.wireless.mobile.test.ui.repositories

import com.wireless.mobile.test.api.models.Country
import io.reactivex.Observable

interface SearchRepository {

    fun fetchCountries(): Observable<List<Country>>

    fun getCountryInfo(code: String): Observable<Country>

    fun getCountriesByCodes(codes: String): Observable<List<Country>>

    fun getCountriesByName(name: String): Observable<List<Country>>
}