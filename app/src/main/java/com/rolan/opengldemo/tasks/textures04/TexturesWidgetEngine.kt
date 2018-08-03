package com.rolan.opengldemo.tasks.textures04

import com.rolan.opengldemo.utils.LogUtils

/**
 * Created by wangyang on 2018/8/1.下午2:30
 */
class TexturesWidgetEngine {
    companion object {
        init {
            System.loadLibrary("native-lib")

        }
    }

    external fun init(width: Int, height: Int)
    external fun render()
}