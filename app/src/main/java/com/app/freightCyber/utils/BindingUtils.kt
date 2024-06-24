package com.app.freightCyber.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.app.freightCyber.R
import java.text.SimpleDateFormat
import java.util.Locale

object BindingUtils {

    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(view: ImageView, image: Int) {
        view.setImageResource(image)
    }

    @JvmStatic
    @BindingAdapter("setCheckedImage")
    fun setCheckedImage(view: ImageView, value: Boolean) {
        if (value) {
            view.setImageResource(R.drawable.img_check)
        } else {
            view.setImageResource(R.drawable.img_uncheck)
        }
    }

    @JvmStatic
    @BindingAdapter("setCheckedImage2")
    fun setCheckedImage2(view: ImageView, value: Boolean) {
        if (value) {
            view.setImageResource(R.drawable.img_check_yellow)
        } else {
            view.setImageResource(R.drawable.img_uncheck)
        }
    }

    @JvmStatic
    @BindingAdapter("licenseDate")
    fun licenseDate(view: TextView, value: String?) {
        if (value != null) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MM/yy", Locale.getDefault())

            try {
                val date = inputFormat.parse(value)
                if (date != null) {
                    val text = outputFormat.format(date)
                    view.text = text
                } else {
                    view.text = "Expiry Date: "
                }
            } catch (_: Exception) {
            }
        } else {
            view.text = "Expiry Date: N/A"
        }
    }

    @JvmStatic
    @BindingAdapter("licenseDate2")
    fun licenseDate2(view: TextView, value: String?) {
        if (value != null) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MM/yy", Locale.getDefault())

            try {
                val date = inputFormat.parse(value)
                if (date != null) {
                    val text = outputFormat.format(date)
                    view.text = text
                }
            } catch (_: Exception) {
            }
        } else {
            view.text = "N/A"
        }
    }
}