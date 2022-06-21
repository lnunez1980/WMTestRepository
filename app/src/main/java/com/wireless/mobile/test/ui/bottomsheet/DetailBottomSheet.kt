package com.wireless.mobile.test.ui.bottomsheet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyItemSpacingDecorator
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.wireless.mobile.test.R
import com.wireless.mobile.test.api.models.Country
import com.wireless.mobile.test.databinding.BottomSheetDetailBinding
import com.wireless.mobile.test.factories.ViewModelFactory
import com.wireless.mobile.test.ui.controllers.SearchAdapterController
import com.wireless.mobile.test.util.DrawableAlwaysCrossFadeFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class DetailBottomSheet : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: RequestManager

    private var _binding: BottomSheetDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels { viewModelFactory }
    private lateinit var searchAdapterController: SearchAdapterController

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (dialog as BottomSheetDialog).apply {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        }
        setupViewModel()
        setupRecyclerView()
        setupCloseListener()
    }

    private fun setupCloseListener() {
        binding.closeImageview.setOnClickListener {
            dismiss()
        }
    }

    private fun setupViewModel() {
        lifecycle.addObserver(viewModel)
        listenToObservers()
        val args: DetailBottomSheetArgs by navArgs()
        viewModel.getCountryInfo(args.codeCioc, args.borders)
    }

    private fun listenToObservers() {
        viewModel.countryActions.observe(viewLifecycleOwner) { actions ->
            when (actions) {
                is CountryActions.CountryLoading -> {
                    binding.loaderView.isVisible = actions.loading
                    binding.animationView.playAnimation()
                }
                is CountryActions.CountryFound -> updateUi(actions.country, actions.borders)
                is CountryActions.OnError -> {
                    actions.error.printStackTrace()
                    Snackbar.make(
                        requireView(),
                        actions.error.message.toString(),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun updateUi(country: Country, borders: List<Country>) {
        searchAdapterController.dispatch(borders)
        binding.apply {
            countryNameTextView.text = country.name.official
            imageLoader.load(country.flag.png)
                .transform(RoundedCorners(requireContext().resources.getDimensionPixelSize(R.dimen.radius_6)))
                .transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory()))
                .into(binding.countryImageview)
        }
    }

    private fun setupRecyclerView() {
        searchAdapterController = SearchAdapterController(imageLoader = imageLoader)
        binding.recycleView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recycleView.addItemDecoration(
            EpoxyItemSpacingDecorator(resources.getDimensionPixelSize(R.dimen.space_4))
        )
        binding.recycleView.setHasFixedSize(false)
        binding.recycleView.setController(searchAdapterController)
    }
}