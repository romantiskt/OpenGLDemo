package com.rolan.opengldemo.kotlintasks

import android.content.Context
import com.rolan.opengldemo.base.BaseOpenGLKotlinView
import com.rolan.opengldemo.model.Constants
import com.wang.advance.tasks.kotlin.base.BaseKotlinAct

/**
 * Created by wangyang on 2018/8/8.下午2:45
 * 展示不同view的activity
 */


class WidgetByTypeAct : BaseKotlinAct() {
    override fun inflateLayoutId(): Any {
        val classExtra: Class<out BaseOpenGLKotlinView> = intent.getSerializableExtra(Constants.EXA_INTENT_DATA) as Class<out BaseOpenGLKotlinView>
        val constructor = classExtra.getDeclaredConstructor(Context::class.java)
        constructor.isAccessible = true
        val newInstance = constructor.newInstance(this);
        return newInstance
    }
}