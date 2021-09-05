package com.sample.socket.base.extensions

import android.annotation.TargetApi
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.*
import android.text.*
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * An extension which performs an [action] if the view has been measured, otherwise waits for it to
 * be measured, and then performs the [action].
 */
@TargetApi(19)
inline fun View.measured(crossinline action: () -> Unit) {
    if (isLaidOut) {
        action()
    } else {
        addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                view: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                removeOnLayoutChangeListener(this)
                action()
            }
        })
    }
}

fun ViewGroup.inflate(@LayoutRes resourceId: Int): View =
    LayoutInflater.from(context).inflate(
        resourceId,
        this,
        false
    )

inline fun ViewGroup.forEach(action: (view: View) -> Unit) {
    for (index in 0 until childCount) {
        action(getChildAt(index))
    }
}

inline fun TextInputLayout.boxBackgroundColor(color: Int) {
    boxBackgroundColor = ResourcesCompat.getColor(resources, color, null)
}

inline fun View.backgroundColor(color: Int) {
    backgroundTintList = ColorStateList.valueOf(
        ResourcesCompat.getColor(
            resources,
            color,
            null
        )
    )
}

inline fun View.remove() {
    if (visibility != View.GONE) visibility = View.GONE
}

inline fun View.disable() {
    if (isEnabled) isEnabled = false
}

inline fun View.enable() {
    if (!isEnabled) isEnabled = true
}

inline fun View.show() {
    if (visibility != View.VISIBLE) visibility = View.VISIBLE
}

inline fun View.hide() {
    if (visibility != View.INVISIBLE) visibility = View.INVISIBLE
}

inline val View.isVisible: Boolean
    get() = this.visibility == View.VISIBLE

@ColorInt
fun View.getColorCompat(@ColorRes resourceId: Int) = context.getColorCompat(resourceId)

fun View.getDimension(@DimenRes resourceId: Int) = context.getDimension(resourceId)

fun View.getDrawableCompat(@DrawableRes resourceId: Int) = context.getDrawableCompat(resourceId)

fun View.setViewHeight(height: Int) {
    val params = this.layoutParams
    params.height = height
    this.layoutParams = params
    this.invalidate()
    this.requestLayout()
}


fun ImageView.changeImageColor(color: String, mode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN) =
    setColorFilter(
        Color.parseColor(color), mode
    )

fun ImageView.changeImageColor(
    @ColorRes colorRes: Int,
    mode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN
) =
    setColorFilter(context.getColorCompat(colorRes), mode)

fun dpToPx(context: Context, dpi_val: Int): Int {

    return TypedValue
        .applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpi_val.toFloat(),
            context.resources.displayMetrics
        ).toInt()
}

fun pxToDp(px: Int): Int {
    return (px / Resources.getSystem().displayMetrics.density).toInt()
}

fun ViewGroup.LayoutParams.resizeItemSize() {
    this.width = ConstraintLayout.LayoutParams.WRAP_CONTENT
    this.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
}


fun View.setLayoutParamsHeight(height: Int) {
    val lp = layoutParams
    lp.height = height
    layoutParams = lp
    requestLayout()
}

fun TextInputEditText.clearErrorOnTextChange() {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            // Nothing to do.
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Nothing to do.
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            error = null
        }

    })
}

fun TextInputEditText.clearErrorOnTextChange(inputLayout: TextInputLayout) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            // Nothing to do.
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Nothing to do.
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            inputLayout.clearError()
        }

    })
}

fun TextInputLayout.showError(message: String) {
    isErrorEnabled = true
    error = message
}

fun TextInputLayout.clearError() {
    isErrorEnabled = false
    error = null
}

fun View.activate() {
    isActivated = true
}

fun View.deactivate() {
    isActivated = false
}

fun AppCompatTextView.setCenterCircularGradient(
    @ColorRes centerColor: Int,
    @ColorRes edgeColor: Int
) {
    val width = paint?.measureText(text?.toString())
    val shader = RadialGradient(
        width?.div(2) ?: 0.0f,
        measuredHeight.div(2).toFloat(),
        width?.div(3) ?: 0.0f,
        ContextCompat.getColor(context, centerColor),
        ContextCompat.getColor(context, edgeColor),
        Shader.TileMode.CLAMP
    )
    paint?.shader = shader
}

fun AppCompatTextView.setForeGroundColorSpan(
    string: String,
    @ColorRes color: Int,
    ignoreCase: Boolean,
    vararg substring: String
) {
    val spannable = SpannableString(string)
    substring.forEach { s ->
        val startIndex = string.indexOf(s, ignoreCase = ignoreCase)
        val endIndex = startIndex + s.length
        spannable.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    context,
                    color
                )
            ),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    text = spannable
}

fun AppCompatTextView.setForeGroundColorSpan(
    string: String,
    vararg substring: String,
    @ColorRes color: Int,
    ignoreCase: Boolean
) {
    val spannable = SpannableString(string)
    substring.forEach { s ->
        val startIndex = string.indexOf(s, ignoreCase = ignoreCase)
        val endIndex = startIndex + s.length
        spannable.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    context,
                    color
                )
            ),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    text = spannable
}


fun AppCompatImageView.setTintColor(@ColorRes resourceId: Int) {
    this.setColorFilter(
        context.getColorCompat(resourceId),
        PorterDuff.Mode.SRC_IN
    )
}

fun <T : View> RecyclerView.ViewHolder.getView(@IdRes id: Int): T? = itemView.findViewById(id)


fun View.enableAndActivate() {
    isActivated = true
    isEnabled = true
}

fun View.enableAndDeactivate() {
    isActivated = false
    isEnabled = true
}