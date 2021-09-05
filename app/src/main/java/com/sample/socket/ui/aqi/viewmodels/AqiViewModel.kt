package com.sample.socket.ui.aqi.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.sample.socket.base.viewmodels.BaseDataFlowViewModel
import com.sample.socket.datalib.models.cache.ListDataModel
import com.sample.socket.datalib.models.socket.SocketDataResponse
import com.sample.socket.datalib.useCases.cache.FetchLastUpdateUseCase
import com.sample.socket.datalib.useCases.cache.SaveLatestUpdateUseCase
import com.sample.socket.datalib.useCases.socket.ConnectSocketUseCase
import com.sample.socket.datalib.useCases.socket.DisconnectSocketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("CheckResult")
class AqiViewModel @Inject constructor(private val connectSocketUseCase: ConnectSocketUseCase, private val disconnectSocketUseCase: DisconnectSocketUseCase,
     private val saveLatestUpdateUseCase: SaveLatestUpdateUseCase, private val fetchLastUpdateUseCase: FetchLastUpdateUseCase) : BaseDataFlowViewModel(){

    fun connectToSocket() {
        connectSocketUseCase.execute()
            .subscribe({}, {})
    }

    fun disconnectSocket() {
        disconnectSocketUseCase.execute()
            .subscribe({}, {})
    }

    fun updateData(list: List<SocketDataResponse>) {
        ioScope.launch {
            saveLatestUpdateUseCase.process(ListDataModel(list))
        }
    }

    private val aqiListOfCity = MutableLiveData<List<SocketDataResponse>>()
    fun getAQIOfCity() = aqiListOfCity

    private val aqiListCityWise = MutableLiveData<List<SocketDataResponse>>()
    fun getAQI() = aqiListCityWise

    fun getAqiDataHistory(cityName:String) {
        ioScope.launch {
            val it = fetchLastUpdateUseCase.process()
            if (it.isNotEmpty()) {
                val list = it.filter { data -> data.cityName == cityName }
                    .sortedBy { data->data.timestamp }
                    .takeLast(15)
                aqiListOfCity.postValue(list)
            }
        }
    }

    fun getLatestAqi() {
        ioScope.launch {
            val it = fetchLastUpdateUseCase.process()
            if (it.isNotEmpty()) {
                val list = it.sortedByDescending { it.timestamp }
                    .distinctBy { it.cityName }
                    .sortedBy { it.cityName }
                aqiListCityWise.postValue(list)
            }
        }
    }
}
