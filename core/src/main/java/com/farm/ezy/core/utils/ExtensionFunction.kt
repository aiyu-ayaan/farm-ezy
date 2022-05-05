/*
 * Phar Ezy
 *
 * Copyright (c) 2022 . All rights reserved.
 * Created by ayaan
 * Last modified 13/05/2022 03:05 AM
 *
 */

package com.farm.ezy.core.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.LocaleList
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.farm.ezy.core.R
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
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


fun String.loadImageCircular(parentView: View, view: ImageView, progressBar: ProgressBar) =
    Glide.with(parentView)
        .load(this)
        .centerCrop()
        .apply(RequestOptions().circleCrop())
        .error(R.drawable.ic_errors)
        .transition(DrawableTransitionOptions.withCrossFade())
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibility = View.GONE
                return false
            }

        })
        .timeout(10000)
        .into(view)

fun String.loadImageDefault(
    parentView: View,
    view: ImageView,
    progressBar: ProgressBar?
) = this.apply {
    Glide.with(parentView)
        .load(this)
        .error(R.drawable.ic_errors)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.visibility = View.GONE

                return false
            }

        })
        .timeout(6000)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)
}


@SuppressLint("SimpleDateFormat")
fun Long.convertLongToTime(pattern: String = "dd/MM/yyyy"): String =
    SimpleDateFormat(pattern).run {
        val date = Date(this@convertLongToTime)
        this.format(date)
    }

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

fun String.openLinks(activity: Activity) {
    try {
        activity.startActivity(
            Intent(Intent.ACTION_VIEW).also {
                it.data = Uri.parse(this)
            }
        )
    } catch (e: Exception) {
        Toast.makeText(
            activity,
            activity.resources.getString(R.string.no_activity),
            Toast.LENGTH_SHORT
        ).show()
    }
}

fun Activity.openQuery() =
    this.startActivity(
        Intent.createChooser(
            Intent()
                .also {
                    it.putExtra(Intent.EXTRA_EMAIL, resources.getStringArray(R.array.email))
                    it.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.query))
                    it.type = "text/html"
                    it.setPackage(resources.getString(R.string.gmail_package))
                }, resources.getString(R.string.query)
        )
    )