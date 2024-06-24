package com.app.freightCyber.data.source.local.prefrences

import android.content.SharedPreferences
import com.app.freightCyber.data.source.local.prefrences.SharedPrefManager.KEY.SAVE_TOKEN
import com.app.freightCyber.data.source.local.prefrences.SharedPrefManager.KEY.USER_NAME
import com.app.freightCyber.domain.model.response.LoginSignUpResponse
import com.app.freightCyber.utils.getValue
import com.app.freightCyber.utils.saveValue
import com.google.gson.Gson
import javax.inject.Inject

class SharedPrefManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    object KEY {
        const val USER = "user"
        const val USER_NAME = "user_name"
        const val SAVE_TOKEN = "save_token"
    }

    fun saveUser(bean: LoginSignUpResponse) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY.USER, Gson().toJson(bean))
        editor.apply()
    }

    fun getCurrentUser(): LoginSignUpResponse? {
        val value : String? = sharedPreferences.getString(KEY.USER, null)
        return Gson().fromJson(value, LoginSignUpResponse::class.java)
    }


    fun updateCurrentUser(newValue: String , key : String) {
        val editor = sharedPreferences.edit()
        val currentUser: LoginSignUpResponse? = getCurrentUser()
        currentUser?.let { user ->
            when (key) {
                USER_NAME -> user.data?.user?.name = newValue
            }
        }
        val json = Gson().toJson(currentUser)
        editor.putString(KEY.USER, json)
        editor.apply()
    }

    fun saveToken(token : String?){
        sharedPreferences.saveValue(SAVE_TOKEN ,token)
    }

     fun getToken(): String {
         val token = sharedPreferences.getValue<String>(SAVE_TOKEN)
         return "Bearer $token"
     }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}