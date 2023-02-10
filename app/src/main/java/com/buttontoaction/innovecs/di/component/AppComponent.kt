package com.buttontoaction.innovecs.di.component

import com.buttontoaction.innovecs.di.module.AppModule
import com.buttontoaction.innovecs.di.module.NetworkModule
import com.buttontoaction.innovecs.features.actions.ui.ActionsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NetworkModule::class
])
interface AppComponent {

    fun inject(actionsViewModel: ActionsViewModel)
}