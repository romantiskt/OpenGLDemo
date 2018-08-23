package com.rolan.opengldemo.kotlintasks.widget

import android.content.Context
import android.opengl.GLES20
import com.rolan.opengldemo.base.BaseOpenGLKotlinView
import com.rolan.opengldemo.utils.LogUtils
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by wangyang on 2018/8/8.下午6:12
 * 颜色混合的三角形
 */

class ShaderView_03(context: Context?) : BaseOpenGLKotlinView(context) {
    private var gProgram: Int = 0
    private var gPosition: Int = 0
    private var gColor: Int = 0
    private var EBO: Int = 0
    var vertices: FloatArray = floatArrayOf(
            0.5f, 0.5f, 0.0f,   // 右上角
            0.5f, -0.5f, 0.0f,  // 右下角
            -0.5f, -0.5f, 0.0f, // 左下角
            -0.5f, 0.5f, 0.0f,   // 左上角
            0.0f, 1.0f, 0.0f  //中间上
    )
    var indices: ShortArray = shortArrayOf(
            0, 1, 2,// 第一个三角形
            2, 3, 0
    )
    var color: FloatArray = floatArrayOf(
            1.0f, 0.0f, 0.0f ,
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 1.0f
    )
    var gVertexShader =
            "attribute vec4 vPosition;\n" +
                    "attribute vec3 color;\n"+
                    "varying vec4 vertexColor;\n"+
                    "void main() {\n" +
                    "  gl_Position = vPosition;\n" +
                    "vertexColor = vec4(color,1.0);\n"+
//					"   gl_PointSize = 100;\n"+   //设置点的大小
                    "}\n"
    var gFragmentShader =
            "precision mediump float;\n" +
                    "varying vec4 vertexColor;\n"+
                    "void main() {\n" +
                    "  gl_FragColor = vertexColor;\n" +
                    "}\n"

    override fun onDrawFrame(gl: GL10) {
        // 获取图形的顶点坐标
        var verticesBuf = getFloatArraryBuffer(vertices);
        var indexBuf = getShortArraryBuffer(indices);
        var colorBuf = getFloatArraryBuffer(color);

        GLES20.glUseProgram(gProgram);


        GLES20.glVertexAttribPointer(gPosition, 3, GLES20.GL_FLOAT, false, 3*4, verticesBuf);
        //设置绘制三角形的颜色
        GLES20.glEnableVertexAttribArray(gPosition);
        GLES20.glVertexAttribPointer(gColor, 4,GLES20.GL_FLOAT, false,3*4, colorBuf)
        GLES20.glEnableVertexAttribArray(gColor)

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.size, GLES20.GL_UNSIGNED_SHORT, indexBuf)
        GLES20.glDisableVertexAttribArray(gPosition)
        GLES20.glDisableVertexAttribArray(gColor)
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        super.onSurfaceCreated(gl, config)
        gProgram = createProgram(gVertexShader, gFragmentShader)
        if (gProgram == 0) {
            LogUtils.dTag(TAG, "Could not create gProgram")
            return
        }
        gPosition = GLES20.glGetAttribLocation(gProgram, "vPosition");
        gColor = GLES20.glGetAttribLocation(gProgram, "color");
    }

}