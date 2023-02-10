package com.buttontoaction.innovecs.common

import io.reactivex.Single

interface SingleUseCase<Result> : BaseUseCase {
    operator fun invoke(): Single<Result>
}
