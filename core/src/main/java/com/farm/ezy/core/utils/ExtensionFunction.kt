/*
 * Phar Ezy
 *
 * Copyright (c) 2022 . All rights reserved.
 * Created by ayaan
 * Last modified 13/05/2022 03:05 AM
 *
 */

package com.farm.ezy.core.utils

import android.content.Context
import android.os.LocaleList
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import java.util.*

/**
 * Check weather phone number is valid or not
 * @param number 10 Digit phone number
 * @return whether number is correct or not
 */
fun checkNumber(number: String): Boolean =
    number.length == 10

/**
 * Make SnackBar
 * @param message Message
 * @param actionName ActionName
 * @param action Action
 */
fun View.snackBar(message: String, actionName: String?, action: (() -> Unit)?) =
    this.apply {
        Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
            .apply {
                action?.let {
                    this.setAction(actionName) {
                        action.invoke()
                    }
                }
            }
            .show()
    }

/**
 * Return Current fragment.
 * @return Current Fragment
 * @since 4.0
 */
val FragmentManager.currentNavigationFragment: Fragment?
    get() = primaryNavigationFragment?.childFragmentManager?.fragments?.first()


//fun String.loadImage(
//    parentView: View,
//    view: ImageView,
//    progressBar: ProgressBar?,
//    cornerRadius: Int = DEFAULT_CORNER_RADIUS,
//    isProfile: Int = 0
//) =
//    Glide.with(parentView)
//        .load(this)
//        .fitCenter()
//        .error(if (isProfile == 0) R.drawable.ic_running_error else R.drawable.ic_profile)
//        .listener(object : RequestListener<Drawable> {
//            override fun onLoadFailed(
//                e: GlideException?,
//                model: Any?,
//                target: com.bumptech.glide.request.target.Target<Drawable>?,
//                isFirstResource: Boolean
//            ): Boolean {
//                progressBar?.visibility = View.GONE
//                return false
//            }
//
//            override fun onResourceReady(
//                resource: Drawable?,
//                model: Any?,
//                target: com.bumptech.glide.request.target.Target<Drawable>?,
//                dataSource: DataSource?,
//                isFirstResource: Boolean
//            ): Boolean {
//                progressBar?.visibility = View.GONE
//                return false
//            }
//
//        })
//        .apply(RequestOptions.bitmapTransform(RoundedCorners(cornerRadius)))
//        .timeout(6000)
//        .transition(DrawableTransitionOptions.withCrossFade())
//        .into(view)

private fun getSystemLocale() =
    LocaleList.getDefault().get(0).language

fun setContent(en: () -> Unit, hi: () -> Unit) {
    when (getSystemLocale()) {
        "hi" -> hi.invoke()
        else -> en.invoke()
    }
}

fun setAppTheme(type: Int) {
    AppCompatDelegate.setDefaultNightMode(type)
}

@Suppress("DEPRECATION")
fun Context.applyNewLocale(locale: Locale): Context {
    val config = this.resources.configuration
    val sysLocale =
        config.locales.get(0)
    if (sysLocale.language != locale.language) {
        Locale.setDefault(locale)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
    return this
}
