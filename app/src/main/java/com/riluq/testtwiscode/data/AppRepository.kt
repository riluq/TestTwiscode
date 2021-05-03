package com.riluq.testtwiscode.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.riluq.testtwiscode.data.local.LocalRepository
import com.riluq.testtwiscode.data.local.entity.FavoriteEntity
import com.riluq.testtwiscode.data.remote.RemoteRepository
import com.riluq.testtwiscode.data.remote.RemoteRepositoryImpl
import com.riluq.testtwiscode.data.remote.ResponseCallback
import com.riluq.testtwiscode.data.remote.response.SearchResponse
import com.riluq.testtwiscode.utils.LoggedInMode
import com.riluq.testtwiscode.vo.Resource
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : AppDataSource {

    override suspend fun onSearch(): LiveData<Resource<List<SearchResponse>>> {
        val data = MutableLiveData<Resource<List<SearchResponse>>>()
        remoteRepository.search(
            object : ResponseCallback<List<SearchResponse>> {
                override fun onComplete(response: Resource<List<SearchResponse>>) {
                    data.postValue(response)
                }

                override fun onErrorResponse(response: Resource<List<SearchResponse>>) {
                    data.postValue(response)
                }

                override fun onFailed(
                    response: Resource<List<SearchResponse>>,
                    exception: Exception
                ) {
                    data.postValue(response)
                }

            })
        return data
    }


    // Database
    override fun getAllFavorite(): LiveData<PagedList<FavoriteEntity>> =
        LivePagedListBuilder(localRepository.getAllFavorite(), 20).build()

    override fun getFavorite(id: Int): LiveData<FavoriteEntity> = localRepository.getFavoriteById(id)

    override suspend fun addFavorite(favoriteEntity: FavoriteEntity) = localRepository.insertFavorite(favoriteEntity)

    override suspend fun deleteFavorite(favoriteEntity: FavoriteEntity) = localRepository.deleteFavorite(favoriteEntity)

    override fun searchFavorite(query: String): LiveData<PagedList<FavoriteEntity>> =
        LivePagedListBuilder(localRepository.searchFavorite(query), 20).build()


    override fun updateUserInfo(
        accessToken: String?,
        userId: String?,
        loggedInMode: LoggedInMode,
        userName: String?,
        email: String?,
        phoneNumber: String?,
        userLocationId: Int,
        userLocationName: String?
    ) = localRepository.updateUserInfo(accessToken, userId, loggedInMode, userName, email, phoneNumber, userLocationId, userLocationName)

    override fun setUserAsLoggedOut() = localRepository.setUserAsLoggedOut()

    override fun setDeviceToken(token: String?) = localRepository.preferences().setDeviceToken(token)

    override fun setUserName(name: String?) = localRepository.preferences().setUserName(name)

    override fun setUserLocationId(locationId: Int?) = localRepository.preferences().setUserLocationId(locationId ?: 0)

    override fun setUserLocationName(locationName: String?) = localRepository.preferences().setUserLocationName(locationName)

    override fun setUserLocationSearchId(locationId: Int?) = localRepository.preferences().setUserLocationSearchId(locationId ?: 0)

    override fun setUserLocationSearchName(locationName: String?) = localRepository.preferences().setUserLocationSearchName(locationName)

    override fun getDeviceToken() = localRepository.preferences().getDeviceToken()

    override fun getAccessToken() = localRepository.preferences().getAccessToken()

    override fun getUserId() = localRepository.preferences().getUserId()

    override fun getUserName() = localRepository.preferences().getUserName()

    override fun getUserEmail() = localRepository.preferences().getUserEmail()

    override fun getUserPhoneNumber(): String? = localRepository.preferences().getPhoneNumber()

    override fun getLoggedInMode() = localRepository.preferences().getLoggedInMode()

    override fun getUserLocationId() = localRepository.preferences().getUserLocationId()

    override fun getUserLocationName(): String? = localRepository.preferences().getUserLocationName()

    override fun getUserLocationSearchId(): Int? = localRepository.preferences().getUserLocationSearchId()

    override fun getUserLocationSearchName(): String? = localRepository.preferences().getUserLocationSearchName()

}