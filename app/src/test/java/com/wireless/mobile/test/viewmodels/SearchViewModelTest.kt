package com.wireless.mobile.test.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.wireless.mobile.test.api.models.Country
import com.wireless.mobile.test.api.models.Flag
import com.wireless.mobile.test.api.models.Name
import com.wireless.mobile.test.ui.fragments.CountriesActions
import com.wireless.mobile.test.ui.fragments.SearchViewModel
import com.wireless.mobile.test.ui.repositories.SearchRepository
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: SearchRepository

    private lateinit var viewModel: SearchViewModel

    @Mock
    lateinit var eventObserver: Observer<CountriesActions>

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        viewModel = SearchViewModel(repository)
    }

    private val country = Country(
        flag = Flag("url imagen"),
        name = Name("Germany", "Germany"),
        population = 123456
    )

    private val countries = listOf(country)

    @Test
    fun `fetch country list from api`() {
        Mockito.`when`(repository.fetchCountries()).thenReturn(Observable.just(countries))

        viewModel.fetchCountries()
        viewModel.countryActions.observeForever(eventObserver)

        assert(viewModel.countryActions.value is CountriesActions.CountriesFound)
    }

    @Test
    fun `get country by name from api`() {
        Mockito.`when`(repository.getCountriesByName("GER")).thenReturn(Observable.just(countries))

        viewModel.getCountriesByName("GER")
        viewModel.countryActions.observeForever(eventObserver)

        assert(viewModel.countryActions.value is CountriesActions.CountriesFound)
    }

    @Test
    fun `fetch country list from api and receive error`() {
        Mockito.`when`(repository.fetchCountries()).thenReturn(Observable.error(Throwable()))

        viewModel.fetchCountries()
        viewModel.countryActions.observeForever(eventObserver)

        assert(viewModel.countryActions.value is CountriesActions.OnError)
    }

    @Test
    fun `get country by name from api and receive error`() {
        Mockito.`when`(repository.getCountriesByName("GER")).thenReturn(Observable.error(Throwable()))

        viewModel.getCountriesByName("GER")
        viewModel.countryActions.observeForever(eventObserver)

        assert(viewModel.countryActions.value is CountriesActions.OnError)
    }

}