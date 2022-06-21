package com.wireless.mobile.test.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.wireless.mobile.test.R
import com.wireless.mobile.test.api.models.Country
import com.wireless.mobile.test.databinding.CountryCardViewBinding
import com.wireless.mobile.test.helpers.addRippleForeground
import com.wireless.mobile.test.helpers.toAmount
import com.wireless.mobile.test.ui.listeners.CountryCardListener
import com.wireless.mobile.test.util.DrawableAlwaysCrossFadeFactory


@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CountryCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    private val binding = CountryCardViewBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    private lateinit var country: Country
    private var imageLoader: RequestManager? = null

    init {
        addRippleForeground()
    }

    @ModelProp
    fun setData(data: Country) {
        country = data
    }

    @ModelProp(ModelProp.Option.DoNotHash)
    fun setImageLoader(imageLoader: RequestManager?) {
        this.imageLoader = imageLoader
    }

    @CallbackProp
    fun setListener(listener: CountryCardListener?) {
        binding.cardView.setOnClickListener {
            listener?.onCountryClicked(country)
        }
    }

    @AfterPropsSet
    fun after() {
        binding.countryNameTextView.text = country.name.official
        binding.populationTextView.text =
            context.getString(R.string.population, context.toAmount(country.population))
        binding.capitalTextView.text = context.getString(R.string.capital, country.capital?.firstOrNull().orEmpty())
        binding.languageTextView.text =  context.getString(R.string.language, country.languages?.idiom.orEmpty())
        binding.currencyTextView.text =  context.getString(R.string.currency, country.currencies?.currency?.name.orEmpty())
        imageLoader?.load(country.flag.png)
            ?.transform(RoundedCorners(context.resources.getDimensionPixelSize(R.dimen.radius_6)))
            ?.transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory()))
            ?.into(binding.countryImageview)
    }
}