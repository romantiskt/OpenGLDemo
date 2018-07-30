package com.rolan.opengldemo.tasks.shaders03

/**
 * Created by wangyang on 2018/7/30.下午3:43
 */
object ShadersWidgetEngine{

    init {
        System.loadLibrary("native-lib")
    }

    external fun init(width: Int, height: Int);
    external fun step();
}