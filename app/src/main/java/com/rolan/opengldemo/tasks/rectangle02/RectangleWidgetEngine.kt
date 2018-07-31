package com.rolan.opengldemo.tasks.rectangle02

/**
 * Created by wangyang on 2018/7/26.下午4:26
 */
object RectangleWidgetEngine {

    init {
        System.loadLibrary("native-lib")
    }

    external fun init(width: Int, height: Int);
    external fun render();
}