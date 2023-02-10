package com.buttontoaction.innovecs.features.actions.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.buttontoaction.innovecs.di.component.AppComponentProvider
import com.buttontoaction.innovecs.features.actions.domain.GetActionsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.Calendar
import javax.inject.Inject

class ActionsViewModel(app: Application) : AndroidViewModel(app) {

    val actionTypeLiveData = MutableLiveData<String>()

    @Inject
    lateinit var getActionsUseCase: GetActionsUseCase

    private val compositeDisposable = CompositeDisposable()

    init {
        (app as AppComponentProvider).provideAppComponent().inject(this)
    }

    fun loadActionType() {
        compositeDisposable.add(
            getActionsUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ actions ->
                    if (!actions.isNullOrEmpty()) {
                        val sortedActions = actions
                            .filter { it.enabled }
                            .filter {
                                it.validDays.contains(
                                    Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1
                                )
                            }
                            .sortedByDescending { it.priority }
                        actionTypeLiveData.value = sortedActions.first().type
                    } else {
                        Log.e("---ERROR---", "Available actions is null or empty")
                    }
                }, { error -> Log.e("---ERROR---", error.message.toString()) })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}