package com.rolan.opengldemo.base

import android.content.Context
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView
import android.util.Log
import com.rolan.opengldemo.utils.LogUtils
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLContext
import javax.microedition.khronos.egl.EGLDisplay
import javax.microedition.khronos.opengles.GL10

/**
 * Created by wangyang on 2018/7/26.下午4:02
 */
abstract class BaseOpenGLCppView : GLSurfaceView {
    var TAG: String? = this.javaClass.simpleName;

    constructor(context: Context?) : super(context) {
        init(false, 0, 0)
    }

    constructor(context: Context, translucent: Boolean, depth: Int, stencil: Int) : super(context) {
        init(translucent, depth, stencil)
    }

    private fun init(translucent: Boolean, depth: Int, stencil: Int) {
        if (translucent) {//设置一个半透明的背景
            this.holder.setFormat(PixelFormat.TRANSLUCENT)
        }
        setEGLContextFactory(ContextFactory())//绑定一个context
        setRenderer(Renderer())//设置一个视图提供者
    }

    inner class ContextFactory : GLSurfaceView.EGLContextFactory {
        val EGL_CONTEXT_CLIENT_VERSION = 0x3098
        override fun createContext(egl: EGL10, display: EGLDisplay, eglConfig: EGLConfig): EGLContext {
            LogUtils.dTag(this@BaseOpenGLCppView.TAG, "creating OpenGL ES 2.0 context")
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
                if (error == EGL10.EGL_SUCCESS) return
                Log.e(TAG, String.format("%s: EGL error: 0x%x", prompt, error))
            }
        }

        override fun destroyContext(egl: EGL10, display: EGLDisplay, context: EGLContext) {
            egl.eglDestroyContext(display, context)
        }

    }

    inner class Renderer : GLSurfaceView.Renderer {
        override fun onDrawFrame(gl: GL10) {
            this@BaseOpenGLCppView.onDrawFrame(gl);
        }

        override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
            this@BaseOpenGLCppView.onSurfaceChanged(gl, width, height);
        }

        override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
            this@BaseOpenGLCppView.onSurfaceCreated(gl, config)
        }
    }

    abstract fun onDrawFrame(gl: GL10)
    abstract fun onSurfaceChanged(gl: GL10, width: Int, height: Int)
    open fun onSurfaceCreated(gl: GL10, config: EGLConfig) {}
}