package com.app.freightCyber.core.base.location
import android.location.Location

interface LocationResultListener {
    fun getLocation(location: Location)
}