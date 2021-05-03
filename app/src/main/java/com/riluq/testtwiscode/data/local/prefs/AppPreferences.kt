package com.riluq.testtwiscode.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.riluq.testtwiscode.utils.LoggedInMode
import javax.inject.Inject

class AppPreferences @Inject constructor(context: Context) {
    private val PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE"
    private val PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID"
    private val PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME"
    private val PREF_KEY_CURRENT_USER_PHONE_NUMBER = "PREF_KEY_CURRENT_USER_PHONE_NUMBER"
    private val PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL"
    private val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"

    private val PREF_KEY_DEVICE_TOKEN = "PREF_KEY_DEVICE_TOKEN"

    private val PREF_KEY_CURRENT_USER_LOCATION_ID = "PREF_KEY_USER_LOCATION_ID"
    private val PREF_KEY_CURRENT_USER_LOCATION_NAME = "PREF_KEY_USER_LOCATION_NAME"

    private val PREF_KEY_CURRENT_USER_LOCATION_SEARCH_ID = "PREF_KEY_USER_LOCATION_SEARCH_ID"
    private val PREF_KEY_CURRENT_USER_LOCATION_SEARCH_NAME = "PREF_KEY_USER_LOCATION_SEARCH_NAME"

    private var mPrefs: SharedPreferences? = null
    private val sharedPreferencesName = "app_preferences"

    init {
        mPrefs = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
    }

    fun setAccessToken(accessToken: String?) {
        mPrefs?.edit {
            putString(PREF_KEY_ACCESS_TOKEN, accessToken)
        }
    }

    fun getAccessToken(): String? {
        return mPrefs?.getString(PREF_KEY_ACCESS_TOKEN, null)
    }

    fun setUserId(userId: String?) {
        mPrefs?.edit {
            putString(PREF_KEY_CURRENT_USER_ID, userId)
        }
    }

    fun getUserId(): String? {
        return mPrefs?.getString(PREF_KEY_CURRENT_USER_ID, null)
    }

    fun setUserName(username: String?) {
        mPrefs?.edit {
            putString(PREF_KEY_CURRENT_USER_NAME, username)
        }
    }

    fun getUserName(): String? {
        return mPrefs?.getString(PREF_KEY_CURRENT_USER_NAME, null)
    }

    fun setPhoneNumber(username: String?) {
        mPrefs?.edit {
            putString(PREF_KEY_CURRENT_USER_PHONE_NUMBER, username)
        }
    }

    fun getPhoneNumber(): String? {
        return mPrefs?.getString(PREF_KEY_CURRENT_USER_PHONE_NUMBER, null)
    }

    fun setUserEmail(email: String?) {
        mPrefs?.edit {
            putString(PREF_KEY_CURRENT_USER_EMAIL, email)
        }
    }

    fun getUserEmail(): String? {
        return mPrefs?.getString(PREF_KEY_CURRENT_USER_EMAIL, null)
    }

    fun setDeviceToken(deviceToken: String?) {
        mPrefs?.edit {
            putString(PREF_KEY_DEVICE_TOKEN, deviceToken)
        }
    }

    fun getDeviceToken(): String? {
        return mPrefs?.getString(PREF_KEY_DEVICE_TOKEN, null)
    }

    fun setUserLocationId(userLocationId: Int) {
        mPrefs?.edit {
            putInt(PREF_KEY_CURRENT_USER_LOCATION_ID, userLocationId)
        }
    }

    fun getUserLocationId(): Int? {
        return mPrefs?.getInt(PREF_KEY_CURRENT_USER_LOCATION_ID, 0)
    }

    fun setUserLocationName(userLocationName: String?) {
        mPrefs?.edit {
            putString(PREF_KEY_CURRENT_USER_LOCATION_NAME, userLocationName)
        }
    }

    fun getUserLocationName(): String? {
        return mPrefs?.getString(PREF_KEY_CURRENT_USER_LOCATION_NAME, null)
    }

    fun setUserLocationSearchId(userLocationId: Int) {
        mPrefs?.edit {
            putInt(PREF_KEY_CURRENT_USER_LOCATION_SEARCH_ID, userLocationId)
        }
    }

    fun getUserLocationSearchId(): Int? {
        return mPrefs?.getInt(PREF_KEY_CURRENT_USER_LOCATION_SEARCH_ID, 0)
    }

    fun setUserLocationSearchName(userLocationName: String?) {
        mPrefs?.edit {
            putString(PREF_KEY_CURRENT_USER_LOCATION_SEARCH_NAME, userLocationName)
        }
    }

    fun getUserLocationSearchName(): String? {
        return mPrefs?.getString(PREF_KEY_CURRENT_USER_LOCATION_SEARCH_NAME, null)
    }

    fun setLoggedInMode(mode: LoggedInMode) {
        mPrefs?.edit {
            putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.type)
        }
    }

    fun getLoggedInMode(): LoggedInMode? {
        val loggedInModePrefs = mPrefs?.getInt(
            PREF_KEY_USER_LOGGED_IN_MODE,
            LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.type
        )
        var loggedInMode: LoggedInMode = LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT

        when (loggedInModePrefs) {
            LoggedInMode.LOGGED_IN_MODE_FB.type -> loggedInMode = LoggedInMode.LOGGED_IN_MODE_FB
            LoggedInMode.LOGGED_IN_MODE_GOOGLE.type -> loggedInMode =
                LoggedInMode.LOGGED_IN_MODE_GOOGLE
            LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.type -> loggedInMode =
                LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT
            LoggedInMode.LOGGED_IN_MODE_SERVER.type -> loggedInMode =
                LoggedInMode.LOGGED_IN_MODE_SERVER
        }

        return loggedInMode
    }

}