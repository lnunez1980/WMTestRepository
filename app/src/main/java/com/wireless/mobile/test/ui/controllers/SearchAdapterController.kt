package com.wireless.mobile.test.ui.controllers

import com.airbnb.epoxy.AsyncEpoxyController
import com.bumptech.glide.RequestManager
import com.wireless.mobile.test.api.models.Country
import com.wireless.mobile.test.ui.listeners.CountryCardListener
import com.wireless.mobile.test.ui.view.CountryCardViewModel_


class SearchAdapterController(
    private val listener: CountryCardListener? = null,
    private val imageLoader: RequestManager
) : AsyncEpoxyController() {

    private var countries: List<Country> = listOf()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        if (countries.isNotEmpty()) {
            countries.forEach { country ->
                CountryCardViewModel_()
                    .id(country.hashCode())
                    .data(country)
                    .imageLoader(imageLoader)
                    .listener(listener)
                    .addTo(this)
            }
        }
    }

    fun dispatch(countries: List<Country>) {
        this.countries += countries
    }

    fun clear() {
        this.countries = emptyList()
    }
}