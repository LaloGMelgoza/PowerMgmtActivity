package com.example.powermanagement

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var powerStatus: TextView
    private lateinit var batteryLevel: TextView
    private lateinit var plugInfoReceiver: BroadcastReceiver
    private lateinit var batteryLevelInfoReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeComponents()
        initializeReceivers()
    }
    private fun initializeComponents()
    {
        powerStatus = findViewById<TextView>(R.id.txt_powerStatus)
        batteryLevel = findViewById<TextView>(R.id.txt_batteryLevel)

        plugInfoReceiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val plugged = intent?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)

                when (plugged) {
                    BatteryManager.BATTERY_PLUGGED_AC -> powerStatus.text = "AC is connected"
                    BatteryManager.BATTERY_PLUGGED_USB -> powerStatus.text = "USB is connected"
                    BatteryManager.BATTERY_PLUGGED_WIRELESS -> powerStatus.text = "Wireless"
                    else -> powerStatus.text = "Not Plugged"
                }
            }
        }
        batteryLevelInfoReceiver = object: BroadcastReceiver()
        {
            override fun onReceive(context: Context?, intent: Intent?) {
                val newLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                batteryLevel.text = "${newLevel}%"
            }
        }
    }

    private fun initializeReceivers()
    {
        this.registerReceiver(this.batteryLevelInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        this.registerReceiver(this.plugInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }
}
