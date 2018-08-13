package com.rolan.opengldemo.base

import android.content.Context
import android.graphics.PixelFormat
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.Log
import com.rolan.opengldemo.utils.LogUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLContext
import javax.microedition.khronos.egl.EGLDisplay
import javax.microedition.khronos.opengles.GL10

/**
 * Created by wangyang on 2018/7/26.下午4:02
 */
abstract class BaseOpenGLKotlinView : GLSurfaceView {
    var TAG: String? = this.javaClass.simpleName;
    var grey: Float = 0f;
    var mContext:Context=this.context;

    constructor(context: Context?) : super(context) {
        init(false, 0, 0)
    }

    private fun init(translucent: Boolean, depth: Int, stencil: Int) {
        if (translucent) {//设置一个半透明的背景
            this.holder.setFormat(PixelFormat.TRANSLUCENT)
        }
        setEGLContextClientVersion(2)
        setEGLContextFactory(ContextFactory())//绑定一个context
        setRenderer(Renderer())//设置一个视图提供者
    }

    inner class ContextFactory : GLSurfaceView.EGLContextFactory {
        val EGL_CONTEXT_CLIENT_VERSION = 0x3098
        override fun createContext(egl: EGL10, display: EGLDisplay, eglConfig: EGLConfig): EGLContext {
            LogUtils.dTag(this@BaseOpenGLKotlinView.TAG, "creating OpenGL ES 2.0 context")
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
            grey += 0.01f;
            if (grey > 1.0f) {
                grey = 0.0f
            }
            GLES20.glClearColor(grey, grey,grey, 1.0f)
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
            this@BaseOpenGLKotlinView.onDrawFrame(gl)
        }

        override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
            GLES20.glViewport(0, 0, width, height)
            this@BaseOpenGLKotlinView.onSurfaceChanged(gl, width, height);
        }

        override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
            GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f)
            this@BaseOpenGLKotlinView.onSurfaceCreated(gl, config)
        }
    }

    abstract fun onDrawFrame(gl: GL10)
    abstract fun onSurfaceChanged(gl: GL10, width: Int, height: Int)
    open fun onSurfaceCreated(gl: GL10, config: EGLConfig) {}

    fun loadShader(shaderType: Int, sourceCode: String): Int {
        var shader: Int = GLES20.glCreateShader(shaderType)//1.创建一个着色器，返回着色器id，如果返回0则为创建失败
        if (shader != 0) {
            GLES20.glShaderSource(shader, sourceCode)//2.如果着色器创建成功, 为创建的着色器加载脚本代码
            GLES20.glCompileShader(shader)//3.编译已经加载脚本代码的着色器
            var compiled = IntArray(1)
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0)//4.获取着色器的编译情况, 如果结果为0, 说明编译失败
            if (compiled[0] == 0) {//若编译失败则显示错误日志并删除此shader
                LogUtils.dTag(TAG, "Could not compile shader " + shaderType + ":")
                LogUtils.dTag(TAG, GLES20.glGetShaderInfoLog(shader))
                GLES20.glDeleteShader(shader)
                shader = 0//删除着色器
            }
        }
        return shader;
    }


    fun createProgram(vertexSource: String, fragmentSource: String): Int {
        var vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource)//加载顶点着色器
        if (vertexShader == 0) {
            return 0
        }
        var pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource)//加载片元着色器
        if (pixelShader == 0) {
            return 0;
        }
        var program: Int = GLES20.glCreateProgram() //3. 创建着色程序, 返回0说明创建失败
        if (program != 0) {// 若程序创建成功则向程序中加入顶点着色器与片元着色器
            GLES20.glAttachShader(program, vertexShader)//4. 向着色程序中加入顶点着色器
            GLES20.glAttachShader(program, pixelShader)//5. 向着色程序中加入片元着色器
            GLES20.glLinkProgram(program)//6. 链接程序
            var linkStatus:IntArray = IntArray(1);// 存放链接成功program数量的数组
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);//获取链接程序结果
            if (linkStatus[0] != GLES20.GL_TRUE) {//链接程序失败
                LogUtils.dTag(TAG, "Could not link program: ")
                LogUtils.dTag(TAG, GLES20.glGetProgramInfoLog(program))
                GLES20.glDeleteProgram(program)
                program = 0
            }
        }
        return program
    }

    fun getFloatArraryBuffer(data:FloatArray): FloatBuffer {
        // data.size*4是因为一个float占四个字节
        var vbb: ByteBuffer = ByteBuffer.allocateDirect(data.size * 4)  // 创建数据缓冲
        vbb.order(ByteOrder.nativeOrder())           //设置字节顺序
        var vertexBuf: FloatBuffer = vbb.asFloatBuffer()   //转换为Float型缓冲
        vertexBuf.put(data)                       //向缓冲区中放入顶点坐标数据
        vertexBuf.position(0)                         //设置缓冲区起始位置
        return vertexBuf;
    }
    fun getShortArraryBuffer(data:ShortArray): ShortBuffer {
        // data.size*4是因为一个float占四个字节
        var vbb: ByteBuffer = ByteBuffer.allocateDirect(data.size * 2)  // 创建数据缓冲
        vbb.order(ByteOrder.nativeOrder())           //设置字节顺序
        var vertexBuf: ShortBuffer = vbb.asShortBuffer()   //转换为Float型缓冲
        vertexBuf.put(data)                       //向缓冲区中放入顶点坐标数据
        vertexBuf.position(0)                         //设置缓冲区起始位置
        return vertexBuf;
    }
}