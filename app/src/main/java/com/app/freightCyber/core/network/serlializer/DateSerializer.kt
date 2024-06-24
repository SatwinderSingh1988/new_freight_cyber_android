package com.app.freightCyber.core.network.serlializer

import com.app.freightCyber.utils.Constants
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class DateSerializer : JsonDeserializer<Date> {

    private val dateFormat: SimpleDateFormat =
        SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT, Locale.US)
    private val timeFormat: SimpleDateFormat =
        SimpleDateFormat(Constants.DEFAULT_TIME_FORMAT, Locale.US)

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext?
    ): Date? {
        return try {
            val jsonString = json.asJsonPrimitive.asString
            parseDate(jsonString)
        } catch (e: ParseException) {
            e.printStackTrace()
            throw JsonParseException(e.message, e)
        }
    }

    @Throws(ParseException::class)
    private fun parseDate(dateString: String?): Date? {
        return if (dateString != null && dateString.trim { it <= ' ' }.isNotEmpty()) {
            try {
                dateFormat.parse(dateString)
            } catch (pe: ParseException) {
                timeFormat.parse(dateString)
            }
        } else {
            null
        }
    }

}