package com.buttontoaction.innovecs

import android.app.Application
import com.buttontoaction.innovecs.di.component.AppComponent
import com.buttontoaction.innovecs.di.component.AppComponentProvider
import com.buttontoaction.innovecs.di.component.DaggerAppComponent
import com.buttontoaction.innovecs.di.module.AppModule

class App : Application(), AppComponentProvider {

    private var appComponent: AppComponent? = null

    override fun provideAppComponent(): AppComponent {
        val appComponentNonNull: AppComponent = appComponent ?: DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        appComponent = appComponentNonNull

        return appComponentNonNull
    }
}