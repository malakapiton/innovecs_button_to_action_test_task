package com.buttontoaction.innovecs.features.actions.domain

import com.google.gson.annotations.SerializedName

data class ActionsDomainModel(
    val type: String,
    val enabled: Boolean,
    val priority: Int,
    @SerializedName("valid_days")
    val validDays: List<Int>,
    @SerializedName("cool_down")
    val coolDown: Int
)