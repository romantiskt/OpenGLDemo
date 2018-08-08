package com.rolan.opengldemo.kotlintasks.triangle_01

import android.content.Context
import android.opengl.GLES20
import com.rolan.opengldemo.base.BaseOpenGLKotlinView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by wangyang on 2018/8/8.上午10:48
 */
class TriangleView : BaseOpenGLKotlinView {
    private var program: Int = 0
    private var vPosition: Int = 0
    private var uColor: Int = 0
    var vertices: FloatArray = floatArrayOf(
            -0.5f, 0.5f,1.0f,
            0.5f, 0.5f,1.0f,
            -0.5f, -0.5f,1.0f);

    constructor(context: Context?) : super(context)

    override fun onDrawFrame(gl: GL10) {
        // 获取图形的顶点坐标
        var vertices = getFloatArraryBuffer(vertices);
        // 使用某套shader程序
        GLES20.glUseProgram(program);
        // 为画笔指定顶点位置数据(vPosition)
        GLES20.glVertexAttribPointer(vPosition, 3, GLES20.GL_FLOAT, false, 0, vertices);
        // 允许顶点位置数据数组
        GLES20.glEnableVertexAttribArray(vPosition);
        // 设置属性uColor(颜色 索引,R,G,B,A)
        GLES20.glUniform4f(uColor, 0.0f, 1.0f, 0.0f, 1.0f);
        // 绘制
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 3)
        GLES20.glDisableVertexAttribArray(vPosition)
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height);
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        super.onSurfaceCreated(gl, config)
        program = createProgram(verticesShader, fragmentShader);
        // 获取着色器中的属性引用id(传入的字符串就是我们着色器脚本中的属性名)
        vPosition = GLES20.glGetAttribLocation(program, "vPosition");
        uColor = GLES20.glGetUniformLocation(program, "uColor");
    }

    var verticesShader =
            "attribute vec2 vPosition;            \n" +
                    "void main(){                         \n" +
                    "   gl_Position = vec4(vPosition,0,1);\n" +
                    "}"
    var fragmentShader =
            "precision mediump float;         \n" +
                    "uniform vec4 uColor;             \n" +
                    "void main(){                     \n" +
                    "   gl_FragColor = uColor;        \n" +
                    "}"


}