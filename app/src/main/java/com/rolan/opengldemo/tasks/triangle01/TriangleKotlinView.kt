package com.rolan.opengldemo.tasks.triangle01

import android.content.Context
import android.graphics.PixelFormat
import android.opengl.EGL14.EGL_CONTEXT_CLIENT_VERSION
import android.opengl.GLSurfaceView
import android.util.Log
import com.rolan.opengldemo.base.BaseOpenGLView
import com.rolan.opengldemo.tasks.triangle01.TriangleWidgetEngine.init
import com.rolan.opengldemo.utils.LogUtils
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLContext
import javax.microedition.khronos.egl.EGLDisplay
import javax.microedition.khronos.opengles.GL10

/**
 * Created by wangyang on 2018/7/26.下午2:56
 */
class TriangleKotlinView : BaseOpenGLView {
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