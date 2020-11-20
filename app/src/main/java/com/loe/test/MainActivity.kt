package com.loe.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.baidu.location.LocationClient
import com.loe.location.LoeLocation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    var locationClient: LocationClient? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener()
        {
            locationClient?.stop()
            locationClient = LoeLocation.location(this)
            {
                if (it != null)
                {
                    textView.text = it.address.address + Math.random()
                } else
                {
                    textView.text = "定位失败"
                }
            }
        }
    }
}