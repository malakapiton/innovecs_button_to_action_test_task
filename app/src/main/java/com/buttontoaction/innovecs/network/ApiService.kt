package com.buttontoaction.innovecs.network

import com.buttontoaction.innovecs.features.actions.domain.ActionsDomainModel
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("androidexam/butto_to_action_config.json")
    fun getActions(): Single<List<ActionsDomainModel>>
}