@file:JvmName("ContextExtension")

package com.sample.socket.base.extensions

import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.Settings.System
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.sample.socket.R

import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

fun Context.getColorCompat(@ColorRes colorId: Int): Int =
    ContextCompat.getColor(this, colorId)

fun Context.getDrawableCompat(@DrawableRes drawableId: Int): Drawable? =
    ContextCompat.getDrawable(this, drawableId)

fun Context.getDimension(@DimenRes resourceId: Int) = resources.getDimension(resourceId)

fun Context.getDimensionPixelSize(@DimenRes dimenRes: Int) =
    resources.getDimensionPixelSize(dimenRes)

fun Context.resourceToUri(resourceId: Int): Uri = Uri.Builder()
    .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
    .authority(resources.getResourcePackageName(resourceId))
    .appendPath(resources.getResourceTypeName(resourceId))
    .appendPath(resources.getResourceEntryName(resourceId))
    .build()


fun Context.isAppInstalled(packageName: String): Boolean {
    return try {
        packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

fun Context.getVersionCode(): Int {
    try {
        return packageManager.getPackageInfo(packageName, 0).versionCode
    } catch (ex: Exception) { }
    return 0
}

fun Context.isTablet(): Boolean {
    // TODO think about a better approach to do this
    val sizeConfig = resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
    return sizeConfig == Configuration.SCREENLAYOUT_SIZE_XLARGE
}

fun Context.isTablet7inch(): Boolean {
    val sizeConfig = resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
    return sizeConfig == Configuration.SCREENLAYOUT_SIZE_LARGE
}

fun Context.isAutoRotationEnabled(): Boolean {
    return (System.getInt(contentResolver, System.ACCELEROMETER_ROTATION, 0) == 1)
}

fun Context.toast(text: String, length: Int = Toast.LENGTH_LONG) = Toast.makeText(
    this, text,
    length
).show()

fun Context.rawToast(text: String, length: Int = Toast.LENGTH_LONG): Toast = Toast.makeText(
    this, text,
    length
)

fun Context.toast(@StringRes textRes: Int, length: Int = Toast.LENGTH_LONG) = toast(
    getString(textRes), length
)

fun Context.toast(
    text: String,
    length: Int = Toast.LENGTH_LONG,
    gravity: Int,
    enableDarkMode: Boolean = false
) {
    val toast = Toast.makeText(applicationContext, text, length)
    toast.setGravity(gravity, 0, 0)
    if (enableDarkMode) {
        val view = toast.view
        view?.let {
            view.background?.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN)
            val textView: TextView = view.findViewById(android.R.id.message)
            textView.setTextColor(Color.WHITE)
        }
    }
    toast.show()
}

fun Context.getApplicationName(): String? {

    return try {
        packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, 0))
            .toString()
    } catch (e: NameNotFoundException) {
        null
    }

}

fun Context.loadJSONFromAsset(fileName: String): String? {
    var json: String? = null
    json = try {
        val `is`: InputStream = assets.open(fileName)
        val size: Int = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        String(buffer, Charset.defaultCharset())
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
    return json
}