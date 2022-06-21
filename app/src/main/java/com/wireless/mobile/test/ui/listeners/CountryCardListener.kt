package com.wireless.mobile.test.ui.listeners

import com.wireless.mobile.test.api.models.Country

interface CountryCardListener {
    fun onCountryClicked(country: Country)
}