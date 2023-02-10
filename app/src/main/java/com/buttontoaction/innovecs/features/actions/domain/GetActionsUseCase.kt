package com.buttontoaction.innovecs.features.actions.domain

import com.buttontoaction.innovecs.common.SingleUseCase
import com.buttontoaction.innovecs.features.actions.domain.ActionsDomainModel
import com.buttontoaction.innovecs.features.actions.data.BaseActionsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetActionsUseCase @Inject constructor(
    private val actionsRepository: BaseActionsRepository
): SingleUseCase<List<ActionsDomainModel>> {

    override fun invoke(): Single<List<ActionsDomainModel>> {
        return actionsRepository.getAvailableActions()
    }
}