package com.app.freightCyber.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.app.freightCyber.R
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


object HelperUtils {


    fun <T> parseAndHandleResponse(
        jsonData: Any?,
        modelClass: Class<T>,
        onSuccess: (T) -> Unit,
        onFailure: () -> Unit
    ) {
        val modelData = try {
            Gson().fromJson(jsonData.toString(), modelClass)
        } catch (e: JsonSyntaxException) {
            null
        }
        if (modelData != null) {
            onSuccess(modelData)
        } else {
            onFailure()
        }
    }


    fun ImageView.setTint(@ColorRes colorRes: Int) {

        ImageViewCompat.setImageTintList(
            this,
            ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
        )
    }

    fun navigateWithSlideAnimations(navController: NavController, destinationId: Int) {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_left)
            .setExitAnim(R.anim.wait_anim)
            .setPopEnterAnim(R.anim.wait_anim)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()
        navController.navigate(destinationId, null, navOptions)
    }

    fun navigateWithSlideAnimations(
        navController: NavController,
        destinationId: Int,
        bundle: Bundle
    ) {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_left)
            .setExitAnim(R.anim.wait_anim)
            .setPopEnterAnim(R.anim.wait_anim)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()
        navController.navigate(destinationId, bundle, navOptions)
    }


    private var datePickerDialog: DatePickerDialog? = null
    fun openDatePickerDialog(context: Context, textView: TextView) {
        val c: Calendar = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)

        datePickerDialog = DatePickerDialog(
            context,
            android.R.style.Theme_Material_Dialog_Alert,
            { view, year, monthOfYear, dayOfMonth ->
                val date = "$year-${monthOfYear + 1}-${dayOfMonth}"
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val parsedDate = sdf.parse(date)
                val formattedDate = sdf.format(parsedDate)
                textView.text = formattedDate
            },
            year,
            month,
            day
        )
        datePickerDialog?.show()
    }


    private var timePickerDialog: TimePickerDialog? = null
    fun openTimePickerDialog(context: Context, textView: TextView) {
        val c = Calendar.getInstance()
        val hour = c[Calendar.HOUR_OF_DAY]
        val minute = c[Calendar.MINUTE]
        timePickerDialog = TimePickerDialog(
            context,
            android.R.style.Theme_Material_Dialog_Alert,
            { view, hourOfDay, minute ->
                val time = "$hourOfDay:$minute"
                val sdf = SimpleDateFormat("HH:mm", Locale.US)
                val parsedTime = sdf.parse(time)
                val neededFormat = SimpleDateFormat("hh:mm a", Locale.US)
                val formattedTime = neededFormat.format(parsedTime)
                textView.text = formattedTime
            },
            hour,
            minute,
            false
        )
        timePickerDialog?.show()
    }


    private var popupMenu: PopupMenu? = null
    fun showPopupMenu(context: Context, textView: TextView, list: List<String>) {
        val wrapper: Context = ContextThemeWrapper(context, R.style.PopupMenuStyle)
        popupMenu = PopupMenu(wrapper, textView)
        for (item in list) {
            popupMenu?.menu?.add(item)
        }
        popupMenu?.setOnMenuItemClickListener { menuItem ->
            textView.text = menuItem.title
            true
        }
        popupMenu?.show()
    }

    fun ComponentActivity.pickMultipleImagesFromGallery(
        maxImageCount: Int = 5,
        onImagesSelected: (List<Uri>) -> Unit
    ) {
        val pickMultipleImages = registerForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(
                maxImageCount
            )
        ) { uris ->
            onImagesSelected(uris)
        }
        pickMultipleImages.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

}