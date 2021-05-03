package com.riluq.testtwiscode.di.component

import android.app.Application
import com.riluq.testtwiscode.App
import com.riluq.testtwiscode.di.builder.ActivityBuilder
import com.riluq.testtwiscode.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityBuilder::class])
interface AppComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}