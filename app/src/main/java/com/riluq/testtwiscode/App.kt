package com.riluq.testtwiscode

import android.app.Application
import androidx.startup.AppInitializer
import com.riluq.testtwiscode.di.component.DaggerAppComponent
import com.riluq.testtwiscode.utils.AppLogger
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import net.danlew.android.joda.JodaTimeInitializer
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()

        AppInitializer.getInstance(this).initializeComponent(JodaTimeInitializer::class.java)

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

        AppLogger.init()

    }

}