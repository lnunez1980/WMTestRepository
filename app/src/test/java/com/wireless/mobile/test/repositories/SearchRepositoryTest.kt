package com.wireless.mobile.test.repositories

import com.wireless.mobile.test.api.ServicesApi
import com.wireless.mobile.test.api.models.Country
import com.wireless.mobile.test.api.models.Flag
import com.wireless.mobile.test.api.models.Name
import com.wireless.mobile.test.ui.repositories.SearchRepository
import com.wireless.mobile.test.ui.repositories.SearchRepositoryImpl
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchRepositoryTest {

    @Mock
    lateinit var searchApi: ServicesApi

    private lateinit var searchRepository: SearchRepository

    @Before
    fun setUp() {
        searchRepository = SearchRepositoryImpl(searchApi)
    }

    private val country = Country(
        flag = Flag("url imagen"),
        name = Name("Germany", "Germany"),
        population = 123456
    )

    private val countries = listOf(country)

    @Test
    fun `on get countries from api`() {

        Mockito.`when`(searchApi.getCountries()).thenReturn(Observable.just(countries))

        val testSingle = searchRepository.fetchCountries().test()

        testSingle.assertOf {
            it.assertComplete()
            it.assertNoErrors()
        }
    }

    @Test
    fun `on get countries by code from api`() {

        Mockito.`when`(searchApi.getCountriesByCodes("GER")).thenReturn(Observable.just(countries))

        val testSingle = searchRepository.getCountriesByCodes("GER").test()

        testSingle.assertOf {
            it.assertComplete()
            it.assertNoErrors()
        }
    }

    @Test
    fun `on get countries by name from api`() {

        Mockito.`when`(searchApi.getCountriesByName("Germany")).thenReturn(Observable.just(countries))

        val testSingle = searchRepository.getCountriesByName("Germany").test()

        testSingle.assertOf {
            it.assertComplete()
            it.assertNoErrors()
        }
    }

    @Test
    fun `on get countries info by code from api`() {

        Mockito.`when`(searchApi.getCountryInfo("GER")).thenReturn(Observable.just(countries))

        val testSingle = searchRepository.getCountryInfo("GER").test()

        testSingle.assertOf {
            it.assertComplete()
            it.assertNoErrors()
        }
    }

    @Test
    fun `on get countries from api and receive error`() {

        Mockito.`when`(searchApi.getCountries()).thenReturn(Observable.error(Throwable()))

        val testSingle = searchRepository.fetchCountries().test()

        testSingle.assertOf {
            it.assertNotComplete()
        }
    }

    @Test
    fun `on get countries by code from api and receive error`() {

        Mockito.`when`(searchApi.getCountriesByCodes("GER")).thenReturn(Observable.error(Throwable()))

        val testSingle = searchRepository.getCountriesByCodes("GER").test()

        testSingle.assertOf {
            it.assertNotComplete()
        }
    }

    @Test
    fun `on get countries by name from api and receive error`() {

        Mockito.`when`(searchApi.getCountriesByName("Germany")).thenReturn(Observable.error(Throwable()))

        val testSingle = searchRepository.getCountriesByName("Germany").test()

        testSingle.assertOf {
            it.assertNotComplete()
        }
    }

    @Test
    fun `on get countries info by code from api and receive error`() {

        Mockito.`when`(searchApi.getCountryInfo("GER")).thenReturn(Observable.error(Throwable()))

        val testSingle = searchRepository.getCountryInfo("GER").test()

        testSingle.assertOf {
            it.assertNotComplete()
        }
    }

}