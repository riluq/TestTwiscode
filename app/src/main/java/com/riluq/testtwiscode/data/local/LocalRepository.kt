package com.riluq.testtwiscode.data.local

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.riluq.testtwiscode.data.local.entity.FavoriteEntity
import com.riluq.testtwiscode.data.local.prefs.AppPreferences
import com.riluq.testtwiscode.data.local.room.AppDatabase
import com.riluq.testtwiscode.utils.LoggedInMode
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    private val appPreferences: AppPreferences
) {

    @WorkerThread
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity) {
        appDatabase.appDao().insert(favoriteEntity)
    }

    @WorkerThread
    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        appDatabase.appDao().delete(favoriteEntity)
    }

    fun getAllFavorite(): DataSource.Factory<Int, FavoriteEntity> {
        return appDatabase.appDao().getAllFavorite()
    }

    fun searchFavorite(query: String): DataSource.Factory<Int, FavoriteEntity> {
        return appDatabase.appDao().search("%$query%")
    }

    fun getFavoriteById(id: Int): LiveData<FavoriteEntity> {
        return appDatabase.appDao().getFavoriteByDoctorId(id)
    }

    fun updateUserInfo(
        accessToken: String?,
        userId: String?,
        loggedInMode: LoggedInMode,
        userName: String?,
        email: String?,
        phoneNumber: String?,
        userLocationId: Int,
        userLocationName: String?
    ) {
        appPreferences.setAccessToken("Bearer $accessToken")
        appPreferences.setUserId(userId)
        appPreferences.setLoggedInMode(loggedInMode)
        appPreferences.setUserName(userName)
        appPreferences.setUserEmail(email)
        appPreferences.setPhoneNumber(phoneNumber)
        appPreferences.setUserLocationId(userLocationId)
        appPreferences.setUserLocationName(userLocationName)
    }

    fun setUserAsLoggedOut() {
        appPreferences.setAccessToken(null)
        appPreferences.setUserId(null)
        appPreferences.setLoggedInMode(LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT)
        appPreferences.setUserName(null)
        appPreferences.setUserEmail(null)
        appPreferences.setPhoneNumber(null)
        appPreferences.setUserLocationId(0)
        appPreferences.setUserLocationName(null)
    }

    fun preferences() = appPreferences
}