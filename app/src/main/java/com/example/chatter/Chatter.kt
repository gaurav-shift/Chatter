package com.example.chatter

import android.app.Application
import com.example.chatter.DataLayer.Cloudinary.CloudinaryManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Chatter:Application(){
    override fun onCreate() {
        super.onCreate()
        CloudinaryManager.init(this)
    }
}