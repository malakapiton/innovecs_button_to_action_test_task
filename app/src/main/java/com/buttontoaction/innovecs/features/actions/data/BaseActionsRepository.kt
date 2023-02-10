package com.buttontoaction.innovecs.features.actions.data

import com.buttontoaction.innovecs.features.actions.domain.ActionsDomainModel
import io.reactivex.Single

interface BaseActionsRepository {
    fun getAvailableActions(): Single<List<ActionsDomainModel>>
}