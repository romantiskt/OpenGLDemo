package com.rolan.opengldemo.cpptasks.triangle01

import android.content.Context
import com.rolan.opengldemo.base.BaseOpenGLCppView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by wangyang on 2018/7/26.下午2:56
 */
class TriangleKotlinView : BaseOpenGLCppView {
    constructor(context: Context?) : super(context)


    override fun onDrawFrame(gl: GL10) {
        TriangleWidgetEngine.render()
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        TriangleWidgetEngine.init(width, height)
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        super.onSurfaceCreated(gl, config)
    }
}