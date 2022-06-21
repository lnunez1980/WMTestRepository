package com.wireless.mobile.test.helpers

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.os.ConfigurationCompat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*
import kotlin.math.abs

fun View.addRippleForeground() {
    val outValue = TypedValue()
    context.theme.resolveAttribute(
        android.R.attr.selectableItemBackgroundBorderless,
        outValue,
        true
    )
    foreground = ContextCompat.getDrawable(context, outValue.resourceId)
}

fun TextView.setDrawableLeft(drawableRes: Drawable?, @ColorInt colorRes: Int) {
    val drawables = this.compoundDrawables
    if (colorRes == 0) {
        this.setCompoundDrawablesWithIntrinsicBounds(
            drawableRes,
            drawables[1],
            drawables[2],
            drawables[3]
        )
    } else {
        drawableRes?.let {
            val wrappedDrawable = DrawableCompat.wrap(it).mutate()
            DrawableCompat.setTint(wrappedDrawable, colorRes)
            this.setCompoundDrawablesWithIntrinsicBounds(
                wrappedDrawable,
                drawables[1],
                drawables[2],
                drawables[3]
            )
        }
    }
}

fun TextView.setDrawableRight(drawableRes: Drawable?, @ColorInt colorRes: Int) {
    val drawables = this.compoundDrawables
    if (colorRes == 0) {
        this.setCompoundDrawablesWithIntrinsicBounds(
            drawables[0],
            drawables[1],
            drawableRes,
            drawables[3]
        )
    } else {
        drawableRes?.let {
            val wrappedDrawable = DrawableCompat.wrap(it).mutate()
            DrawableCompat.setTint(wrappedDrawable, colorRes)
            this.setCompoundDrawablesWithIntrinsicBounds(
                drawables[0],
                drawables[1],
                wrappedDrawable,
                drawables[3]
            )
        }
    }
}

fun Context.toAmount(amount: Long): String? {
    val currentLocale = ConfigurationCompat.getLocales(resources.configuration)[0]
    return NumberFormat.getNumberInstance(currentLocale).format(amount)
}