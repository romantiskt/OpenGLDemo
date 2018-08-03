package com.rolan.opengldemo

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

/**
 * Created by wangyang on 2018/8/3.下午5:39
 */
class MyApp:Application(){
    companion object {

        var context: Context by Delegates.notNull()

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}