package com.rolan.opengldemo.tasks.triangle01

import android.content.Context
import android.graphics.PixelFormat
import android.opengl.EGL14.EGL_CONTEXT_CLIENT_VERSION
import android.opengl.GLSurfaceView
import android.util.Log
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
 class TriangleKotlinView:GLSurfaceView {
    var TAG:String?=this.javaClass.simpleName;
    constructor(context: Context?) : super(context){
        init(false, 0, 0)
    }

    constructor(context: Context, translucent: Boolean, depth: Int, stencil: Int): super(context){
        init(translucent, depth, stencil)
    }

    private fun init(translucent: Boolean, depth: Int, stencil: Int) {
        if (translucent) {//设置一个半透明的背景
            this.holder.setFormat(PixelFormat.TRANSLUCENT)
        }
        setEGLContextFactory(ContextFactory())
        setRenderer(Renderer())
    }

    inner class ContextFactory : GLSurfaceView.EGLContextFactory {
        val EGL_CONTEXT_CLIENT_VERSION = 0x3098
        override fun createContext(egl: EGL10, display: EGLDisplay, eglConfig: EGLConfig): EGLContext {
            LogUtils.dTag( this@TriangleKotlinView.TAG, "creating OpenGL ES 2.0 context")
            checkEglError("Before eglCreateContext", egl)
            val attrib_list = intArrayOf(EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE)
            val context = egl.eglCreateContext(display, eglConfig, EGL10.EGL_NO_CONTEXT, attrib_list)
            checkEglError("After eglCreateContext", egl)

            return context
        }

         fun checkEglError(prompt: String, egl: EGL10) {
             var error: Int
             while (true) {
                 error = egl.eglGetError()
                 if(error== EGL10.EGL_SUCCESS)return
                 Log.e(TAG, String.format("%s: EGL error: 0x%x", prompt, error))
             }
        }

        override fun destroyContext(egl: EGL10, display: EGLDisplay, context: EGLContext) {
            egl.eglDestroyContext(display, context)
        }

    }

    private class Renderer : GLSurfaceView.Renderer {
        override fun onDrawFrame(gl: GL10) {
            TriangleWidgetEngine.step()
        }

        override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
            TriangleWidgetEngine.init(width, height)
        }

        override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
            // Do nothing.
        }
    }
}