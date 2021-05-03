package com.riluq.testtwiscode.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.riluq.testtwiscode.data.local.entity.FavoriteEntity
import com.riluq.testtwiscode.data.remote.response.SearchResponse
import com.riluq.testtwiscode.utils.LoggedInMode
import com.riluq.testtwiscode.vo.Resource

interface AppDataSource {

    suspend fun onSearch(): LiveData<Resource<List<SearchResponse>>>

    // Database
    fun getAllFavorite(): LiveData<PagedList<FavoriteEntity>>
    fun getFavorite(id: Int): LiveData<FavoriteEntity>
    suspend fun addFavorite(favoriteEntity: FavoriteEntity)
    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity)
    fun searchFavorite(query: String): LiveData<PagedList<FavoriteEntity>>

    // Shared Preference
    fun updateUserInfo(
        accessToken: String?,
        userId: String?,
        loggedInMode: LoggedInMode,
        userName: String?,
        email: String?,
        phoneNumber: String?,
        userLocationId: Int,
        userLocationName: String?)

    fun setUserAsLoggedOut()
    fun setDeviceToken(token: String?)
    fun setUserName(name: String?)
    fun setUserLocationId(locationId: Int?)
    fun setUserLocationName(locationName: String?)
    fun setUserLocationSearchId(locationId: Int?)
    fun setUserLocationSearchName(locationName: String?)
    fun getDeviceToken(): String?
    fun getAccessToken(): String?
    fun getUserId(): String?
    fun getUserName(): String?
    fun getUserEmail(): String?
    fun getUserPhoneNumber(): String?
    fun getLoggedInMode(): LoggedInMode?
    fun getUserLocationId(): Int?
    fun getUserLocationName(): String?
    fun getUserLocationSearchId(): Int?
    fun getUserLocationSearchName(): String?
}