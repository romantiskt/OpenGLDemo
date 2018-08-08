package com.wang.advance.tasks.kotlin.utils

import android.content.Context
import android.content.Intent
import com.rolan.opengldemo.kotlintasks.WidgetByTypeAct
import com.rolan.opengldemo.model.Constants

/**
 * Created by wangyang on 2018/6/28.下午3:54
 */

 object ActUtil{

    @JvmStatic fun <T> go(context: Context, clazz: Class<T>) {
        var intent = Intent(context, clazz)
        context.startActivity(intent)
    }
    @JvmStatic fun <T> goActByWidgetType(context: Context, widgetType: Class<T>) {
        var intent = Intent(context, WidgetByTypeAct::class.java)
        intent.putExtra(Constants.EXA_INTENT_DATA, widgetType)
        context.startActivity(intent)
    }
}