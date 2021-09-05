package com.sample.socket.ui.activities

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.sample.socket.R
import com.sample.socket.base.activities.BaseAndroidDataFlowViewModelActivity
import com.sample.socket.databinding.ActivityMainBinding
import com.sample.socket.datalib.models.socket.SocketDataResponse
import com.sample.socket.datalib.socket.SocketEventListener
import com.sample.socket.datalib.socket.SocketManager
import com.sample.socket.ui.aqi.viewmodels.AqiViewModel
import com.sample.socket.utils.Constants.SECOND
import com.sample.socket.utils.Constants.TIME_GAP

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseAndroidDataFlowViewModelActivity<ActivityMainBinding, AqiViewModel>(
    ActivityMainBinding::inflate, AqiViewModel::class) {

    var lastTimeStamp = System.currentTimeMillis()
    var city:String=""
    @Inject
    lateinit var socketManager: SocketManager

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).findNavController()
    }

    override fun ActivityMainBinding.setupViews(savedInstanceState: Bundle?) {
        getViewModel().connectToSocket()
    }

    override fun ActivityMainBinding.initListeners(vm: AqiViewModel) {
        socketManager.setEventListener(object: SocketEventListener {
            override fun onEventRecieved(data: List<SocketDataResponse>) {
                val currentTimestamp= System.currentTimeMillis()
                vm.getLatestAqi()
                if(currentTimestamp - lastTimeStamp >= TIME_GAP * SECOND){
                    vm.getAqiDataHistory(city)
                    lastTimeStamp = currentTimestamp
                }

                vm.updateData(data)
            }
        })
    }

    fun navigateToCity(city:String){
        val bundle = Bundle()
        this.city = city
        bundle.putString(com.sample.socket.utils.Bundle.CITY, city)
        navController.navigate(R.id.actionNavigateToChild, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        getViewModel().disconnectSocket()
    }

    //Back button tracking through Nav Graph
    override fun onSupportNavigateUp() =
        navController.navigateUp()
}