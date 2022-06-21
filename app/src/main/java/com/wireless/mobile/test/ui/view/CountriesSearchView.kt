package com.wireless.mobile.test.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import androidx.core.widget.doOnTextChanged
import com.wireless.mobile.test.R
import com.wireless.mobile.test.helpers.setDrawableLeft
import com.wireless.mobile.test.helpers.setDrawableRight


const val DRAWABLE_RIGHT = 2

@SuppressLint("ClickableViewAccessibility", "AppCompatCustomView")
class CountriesSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle
) : AutoCompleteTextView(context, attrs, defStyleAttr) {

    enum class ArrowType {
        DOWN, UP
    }

    interface SearchViewListener {
        /**
         * event called when press enter
         * @param search search word
         */
        fun onSearch(search: String)
        fun onEmptySearch()
    }

    private var listener: SearchViewListener? = null
    private var isDropDownShowing: Boolean = false
    private var currentWith = 0

    init {
        setupStyle()
        isSingleLine = true
        post {
            currentWith = width
        }
        doOnTextChanged { text, _, _, _ ->
            error = null
            dropDownWidth = 0
            isDropDownShowing = false
            if (text.toString().isNotEmpty()) {
                listener?.onSearch(text.toString())
            }
        }
        setupListenerClearIcon()
    }

    private fun setupListenerClearIcon() {
        setOnTouchListener(OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= right - compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                    handleDropDown()
                    return@OnTouchListener true
                }
            }
            false
        })
        setOnDismissListener {
            setArrow(ArrowType.DOWN)
        }
    }

    fun setListener(listener: SearchViewListener) {
        this.listener = listener
        setOnKeyListener { _, keyCode, event ->
            when {
                isKeyCodeEnterPressed(event, keyCode, !text.isNullOrEmpty()) -> {
                    listener.onSearch(text.toString())
                }
                isKeyCodeEnterPressed(event, keyCode, text.isNullOrEmpty()) -> {
                    error = resources.getString(R.string.search_view_error)
                }
                isKeyCodeDelPressed(event, keyCode) -> {
                    listener.onEmptySearch()
                }
            }
            false
        }
    }

    private fun handleDropDown() {
        if (!isDropDownShowing) {
            showDropDown()
            isDropDownShowing = true
            setArrow(ArrowType.UP)
            if (currentWith != 0) {
                dropDownWidth = currentWith
            }
        } else {
            dismissDropDown()
            isDropDownShowing = false
        }
    }

    private fun setArrow(arrowType: ArrowType) {
        if (arrowType == ArrowType.DOWN) {
            setDrawableRight(
                ResourcesCompat.getDrawable(resources, R.drawable.ic_drop_down, null),
                ResourcesCompat.getColor(resources, R.color.grey, null)
            )
        } else {
            setDrawableRight(
                ResourcesCompat.getDrawable(resources, R.drawable.ic_drop_up, null),
                ResourcesCompat.getColor(resources, R.color.grey, null)
            )
        }
    }

    private fun setupStyle() {
        setHint()
        setPadding(12)
        setDrawableLeft(
            ResourcesCompat.getDrawable(resources, R.drawable.ic_search, null),
            ResourcesCompat.getColor(resources, R.color.grey, null)
        )
        setDrawableRight(
            ResourcesCompat.getDrawable(resources, R.drawable.ic_drop_down, null),
            ResourcesCompat.getColor(resources, R.color.grey, null)
        )
        setRoundedBackground()
    }

    private fun setHint() {
        setTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
        setHintTextColor(ResourcesCompat.getColor(resources, R.color.grey, null))
        hint = resources.getString(R.string.search_hint)
    }

    private fun setRoundedBackground() {
        background = ContextCompat.getDrawable(context, R.drawable.rounded_corner)
    }

    private fun isKeyCodeEnterPressed(event: KeyEvent, keyCode: Int, isEmpty: Boolean) =
        event.action == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_ENTER &&
                isEmpty

    private fun isKeyCodeDelPressed(event: KeyEvent, keyCode: Int) =
        text.toString().length <= 1 &&
                event.action == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_DEL
}