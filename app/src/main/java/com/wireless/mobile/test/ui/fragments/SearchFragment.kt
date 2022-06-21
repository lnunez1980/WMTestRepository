package com.wireless.mobile.test.ui.fragments

import android.app.ActionBar
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyItemSpacingDecorator
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.wireless.mobile.test.R
import com.wireless.mobile.test.api.models.Country
import com.wireless.mobile.test.databinding.FragmentSearchBinding
import com.wireless.mobile.test.factories.ViewModelFactory
import com.wireless.mobile.test.ui.controllers.SearchAdapterController
import com.wireless.mobile.test.ui.listeners.CountryCardListener
import com.wireless.mobile.test.ui.view.CountriesSearchView
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class SearchFragment : DaggerFragment(), CountryCardListener,
    CountriesSearchView.SearchViewListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: RequestManager

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchAdapterController: SearchAdapterController

    private val viewModel: SearchViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(viewModel)
        listenToObservers()
        setupRecyclerView()
        binding.searchView.setListener(this)
    }

    override fun onSearch(search: String) {
        searchAdapterController.clear()
        viewModel.filterCountries(search)
    }

    override fun onEmptySearch() {
        searchAdapterController.clear()
        viewModel.restoreData()
    }

    override fun onCountryClicked(country: Country) {
        val action = SearchFragmentDirections.toDetailBottomSheet(
            country.code.orEmpty(),
            country.borders?.toTypedArray()
        )
        findNavController().navigate(action)
    }

    private fun setupRecyclerView() {
        searchAdapterController = SearchAdapterController(this@SearchFragment, imageLoader)
        binding.recycleView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recycleView.addItemDecoration(
            EpoxyItemSpacingDecorator(resources.getDimensionPixelSize(R.dimen.space_4))
        )
        binding.recycleView.setHasFixedSize(false)
        binding.recycleView.setController(searchAdapterController)
    }

    private fun listenToObservers() {
        viewModel.countryActions.observe(viewLifecycleOwner, Observer { actions ->
            when (actions) {
                is CountriesActions.CountriesLoading -> {
                    binding.loaderView.isVisible = actions.loading
                    binding.animationView.playAnimation()
                }
                is CountriesActions.CountriesFound -> {
                    if (actions.isFiltering) {
                        searchAdapterController.clear()
                        searchAdapterController.dispatch(actions.countries)
                    } else {
                        searchAdapterController.dispatch(actions.countries)
                        setDataDropDownList(actions.countries)
                    }
                }
                is CountriesActions.OnError -> {
                    actions.error.printStackTrace()
                    showToast(getString(R.string.error))
                }
            }
        })
    }

    private fun showToast(message: String) {
        val toast = Snackbar.make(this.requireView(), message, Snackbar.LENGTH_SHORT)
        val layoutParams = ActionBar.LayoutParams(toast.view.layoutParams)

        layoutParams.gravity = Gravity.TOP
        toast.view.setPadding(0, 10, 0, 0)
        toast.view.layoutParams = layoutParams
        toast.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        toast.show()
    }

    private fun setDataDropDownList(countries: List<Country>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            countries.map { it.name.official }
        )
        binding.searchView.setDropDownBackgroundResource(R.color.white)
        binding.searchView.setAdapter(adapter)
    }
}