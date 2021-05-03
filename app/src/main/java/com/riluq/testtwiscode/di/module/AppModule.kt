package com.riluq.testtwiscode.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.riluq.testtwiscode.BuildConfig.BASE_URL
import com.riluq.testtwiscode.data.AppRepository
import com.riluq.testtwiscode.data.local.LocalRepository
import com.riluq.testtwiscode.data.local.prefs.AppPreferences
import com.riluq.testtwiscode.data.local.room.AppDatabase
import com.riluq.testtwiscode.data.remote.ApiService
import com.riluq.testtwiscode.data.remote.RemoteRepositoryImpl
import com.riluq.testtwiscode.utils.AppLogger
import com.riluq.testtwiscode.vo.ResponseHandler
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideGuthubUserRepository(
        localRepository: LocalRepository,
        remoteRepositoryImpl: RemoteRepositoryImpl
    ) = AppRepository(localRepository, remoteRepositoryImpl)

    @Provides
    @Singleton
    internal fun provideLocalRepository(
        appDatabase: AppDatabase,
        appPreferences: AppPreferences
    ) =
        LocalRepository(appDatabase, appPreferences)

    @Provides
    @Singleton
    internal fun provideAppPreferences(context: Context) =
        AppPreferences(context)

    @Provides
    @Singleton
    internal fun provideRemoteRepository(api: ApiService, responseHandler: ResponseHandler) =
        RemoteRepositoryImpl(api, responseHandler)

    @Provides
    @Singleton
    internal fun provideDatabase(context: Context) = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java, AppDatabase.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    internal fun provideResponseHandler(moshi: Moshi): ResponseHandler = ResponseHandler(moshi)

    @Provides
    @Singleton
    internal fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    internal fun provideRetrofit(httpClient: OkHttpClient, moshi: MoshiConverterFactory) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(moshi)
            .build()

    @Provides
    @Singleton
    internal fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        cache: Cache
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val request = chain.request().newBuilder()
//                    .addHeader("ClientId", ClientId)
//                    .addHeader("ClientSecret", ClientSecret)
                chain.proceed(request.build())
            })
            .cache(cache)
            .build()

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                AppLogger.d("okhttp : $message")
            }
        }).apply { level = HttpLoggingInterceptor.Level.BODY }


    @Provides
    @Singleton
    internal fun provideCache(context: Context): Cache {
        val cacheDir = File(context.cacheDir, "cache")
        return Cache(cacheDir, 10 * 1024 * 1024)
    }

    @Provides
    @Singleton
    internal fun provideMoshiConverterFactory(): MoshiConverterFactory =
        MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build())
            .asLenient()

    @Provides
    @Singleton
    internal fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application
}