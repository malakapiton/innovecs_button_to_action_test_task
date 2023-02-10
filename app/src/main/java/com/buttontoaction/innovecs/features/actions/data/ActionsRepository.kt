package com.buttontoaction.innovecs.features.actions.data

import com.buttontoaction.innovecs.features.actions.domain.ActionsDomainModel
import com.buttontoaction.innovecs.network.ApiService
import io.reactivex.Single
import javax.inject.Inject

class ActionsRepository @Inject constructor(
    private val apiService: ApiService
): BaseActionsRepository {
    override fun getAvailableActions(): Single<List<ActionsDomainModel>> {
        return apiService.getActions()
    }
}