package com.example.internetspeedmonitor.view

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.internetspeedmonitor.InternetSpeedMonitorService
import com.example.internetspeedmonitor.R
import com.example.internetspeedmonitor.utils.Utils
import com.example.internetspeedmonitor.databinding.ActivityMainBinding
import com.example.internetspeedmonitor.model.repository.InternetSpeedRepository
import com.example.internetspeedmonitor.model.room.InternetRoomDatabase
import com.example.internetspeedmonitor.viewmodel.InternetSpeedViewModel
import com.example.internetspeedmonitor.viewmodel.InternetViewModelProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var viewModel: InternetSpeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        startInternetSpeedService()
        initViewModel()
        setBindingAttributes()
        // bind the activity with service to get internet speed
        bindInternetSpeedService()
    }

    private fun startInternetSpeedService() {
        val intent = Intent(this, InternetSpeedMonitorService::class.java)
        startForegroundService(intent)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            InternetViewModelProvider(getRepository())
        )[InternetSpeedViewModel::class.java]

        viewModel.getInternetSpeeds()
    }

    private fun getRepository(): InternetSpeedRepository {
        val roomDatabase = InternetRoomDatabase.getRoomDatabase(this)
        return InternetSpeedRepository(roomDatabase.getInternetSpeedDAO())
    }

    private fun setBindingAttributes() {
        activityMainBinding.apply {
            viewmodel = viewModel
            utils = Utils.Companion
            lifecycleOwner = this@MainActivity
        }
    }

    private fun bindInternetSpeedService() {
        val intent = Intent(this, InternetSpeedMonitorService::class.java)
        bindService(intent, connection, 0)
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName?, binder: IBinder?) {
            val service =
                (binder as InternetSpeedMonitorService.InternetSpeedBinder).getService()

            lifecycleScope.launch {
                while (true) {
                    val internetSpeed = service.getInternetSpeed()
                    activityMainBinding.tvCurrentSpeed.text = String.format(
                        getString(R.string.current_speed_text),
                        Utils.formatSpeed(internetSpeed)
                    )
                    delay(10000)
                }
            }
        }

        override fun onServiceDisconnected(componentName: ComponentName?) {
            //empty overridden method
        }
    }
}