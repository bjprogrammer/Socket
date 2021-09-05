package com.sample.socket.datalib.models.socket

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

import com.sample.socket.BR

@Entity(tableName = "air_quality")
class SocketDataResponse: BaseObservable() {
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null

    @get:Bindable
    var timestamp:Long = System.currentTimeMillis()
        set(value) {
            field = value
            notifyPropertyChanged(BR.timestamp)
        }

    @get:Bindable
    @SerializedName("city")
    var cityName:String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.cityName)
        }

    @get:Bindable
    @SerializedName("aqi")
    var airQuality:String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.airQuality)
        }
}