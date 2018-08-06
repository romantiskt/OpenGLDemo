package com.rolan.opengldemo.cpptasks.triangle01;

// Wrapper for native library

object TriangleWidgetEngine {
    init {
        System.loadLibrary("native-lib")
    }

    external fun init(width: Int, height: Int);
    external fun render();


}
