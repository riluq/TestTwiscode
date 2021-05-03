package com.riluq.testtwiscode.di.builder

import com.riluq.testtwiscode.ui.cart.CartActivity
import com.riluq.testtwiscode.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindCartActivity(): CartActivity
}