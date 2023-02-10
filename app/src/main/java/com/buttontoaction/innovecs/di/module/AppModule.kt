package com.buttontoaction.innovecs.di.module

import android.content.Context
import com.buttontoaction.innovecs.features.actions.data.ActionsRepository
import com.buttontoaction.innovecs.network.ApiService
import com.buttontoaction.innovecs.di.qualifier.AppContext
import com.buttontoaction.innovecs.features.actions.data.BaseActionsRepository
import com.buttontoaction.innovecs.features.actions.domain.GetActionsUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val appContext: Context) {

    @Provides
    @Singleton
    @AppContext
    fun provideApplicationContext(): Context {
        return appContext
    }

    @Singleton
    @Provides
    fun provideGetActionsUseCase(actionsRepository: BaseActionsRepository) =
        GetActionsUseCase(actionsRepository)

    @Singleton
    @Provides
    fun provideActionsRepository(apiService: ApiService): BaseActionsRepository =
        ActionsRepository(apiService)
}