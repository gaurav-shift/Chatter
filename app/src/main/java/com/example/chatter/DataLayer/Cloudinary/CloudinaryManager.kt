package com.example.chatter.DataLayer.Cloudinary

import android.content.Context
import com.cloudinary.android.MediaManager

object CloudinaryManager {

    fun init(context: Context) {
        val config = mapOf(
            "cloud_name" to "dglqolesa"
        )
        MediaManager.init(context, config)
    }
}