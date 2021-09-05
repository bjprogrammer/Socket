package com.sample.socket.datalib.useCases.cache

import com.sample.socket.datalib.base.BaseUseCaseUnWrapped
import com.sample.socket.datalib.base.BaseUseCaseUnWrappedWithInput
import com.sample.socket.datalib.models.cache.ListDataModel
import com.sample.socket.datalib.models.socket.SocketDataResponse
import com.sample.socket.datalib.repositories.cache.AppCacheRepo
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SaveLatestUpdateUseCase @Inject constructor(
    private val appCacheRepo: AppCacheRepo
) : BaseUseCaseUnWrappedWithInput<ListDataModel, Boolean>() {

    override suspend fun process(input: ListDataModel): Boolean {
        return (appCacheRepo.saveLatestAQI(input.data)).run {
            if (isSuccess) {
                getSuccess() ?: false
            } else {
                val s = this.getError()

                false
            }
        }
    }
}

@ViewModelScoped
class FetchLastUpdateUseCase @Inject constructor(
    private val appCacheRepo: AppCacheRepo
) : BaseUseCaseUnWrapped<List<SocketDataResponse>>() {

    override suspend fun process(): List<SocketDataResponse> {
        val failure = arrayListOf<SocketDataResponse>()
        return (appCacheRepo.getAQIHistory().run {
            if (isSuccess) {
                getSuccess() ?: failure
            } else {
                failure
            }
        })
    }
}