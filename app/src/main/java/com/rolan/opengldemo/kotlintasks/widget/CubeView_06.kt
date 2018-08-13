package com.rolan.opengldemo.kotlintasks.widget

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.glGetAttribLocation
import android.opengl.Matrix
import com.rolan.opengldemo.base.BaseOpenGLKotlinView
import com.rolan.opengldemo.utils.LogUtils
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by wangyang on 2018/8/8.下午6:12
 * 正方体
 */

class CubeView_06(context: Context?) : BaseOpenGLKotlinView(context) {
    private var gProgram: Int = 0
    private var gPosition: Int = 0
    private var gColor: Int = 0
    private var mMatrixHandle: Int = 0
    var vertices: FloatArray = floatArrayOf(
            -1.0f,1.0f,1.0f,    //正面左上0
            -1.0f,-1.0f,1.0f,   //正面左下1
            1.0f,-1.0f,1.0f,    //正面右下2
            1.0f,1.0f,1.0f,     //正面右上3
            -1.0f,1.0f,-1.0f,    //反面左上4
            -1.0f,-1.0f,-1.0f,   //反面左下5
            1.0f,-1.0f,-1.0f,    //反面右下6
            1.0f,1.0f,-1.0f    //反面右上7
    )
    var indices: ShortArray = shortArrayOf(
            6,7,4,6,4,5,    //后面
            6,3,7,6,2,3,    //右面
            6,5,1,6,1,2,    //下面
            0,3,2,0,2,1,    //正面
            0,1,5,0,5,4,    //左面
            0,7,3,0,4,7   //上面
    )
    var color: FloatArray = floatArrayOf(
            0f,1f,0f,1f,
            0f,1f,0f,1f,
            0f,1f,0f,1f,
            0f,1f,0f,1f,
            1f,0f,1f,1f,
            1f,0f,1f,1f,
            1f,1f,0f,1f,
            1f,1f,0f,1f
    )
    var gVertexShader =
            "attribute vec4 vPosition;\n" +
                    "attribute vec4 color;\n"+
                    "varying vec4 vertexColor;\n"+
                    "uniform mat4 uMVPMatrix;\n" +
                    "void main() {\n" +
                    "  gl_Position = uMVPMatrix*vPosition;\n" +
                    "vertexColor = color;\n"+
//					"   gl_PointSize = 100;\n"+   //设置点的大小
                    "}\n"
    var gFragmentShader =
            "precision mediump float;\n" +
                    "varying vec4 vertexColor;\n"+
                    "void main() {\n" +
                    "  gl_FragColor = vertexColor;\n" +
                    "}\n"
    private val mMVPMatrix =FloatArray(16
    )
    private val mMatrixCamera = FloatArray(16)    //相机矩阵
    private val mMatrixProjection = FloatArray(16)    //投影矩阵
    override fun onDrawFrame(gl: GL10) {
        // 获取图形的顶点坐标
        var verticesBuf = getFloatArraryBuffer(vertices);
        var indexBuf = getShortArraryBuffer(indices);
        var colorBuf = getFloatArraryBuffer(color);

        GLES20.glUseProgram(gProgram)

        GLES20.glUniformMatrix4fv(mMatrixHandle, 1, false, mMVPMatrix, 0);
        Matrix.rotateM(mMVPMatrix, 0, 1f, 0f, 0f, 1.0f)//旋转
        GLES20.glVertexAttribPointer(gPosition, 3, GLES20.GL_FLOAT, false, 3*4, verticesBuf);
        //设置绘制三角形的颜色
        GLES20.glEnableVertexAttribArray(gPosition)
        GLES20.glVertexAttribPointer(gColor, 4,GLES20.GL_FLOAT, false,3*4, colorBuf)
        GLES20.glEnableVertexAttribArray(gColor)

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.size, GLES20.GL_UNSIGNED_SHORT, indexBuf)
        GLES20.glDisableVertexAttribArray(gPosition)
        GLES20.glDisableVertexAttribArray(gColor)
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        val ratio = width.toFloat() / height
        Matrix.frustumM(mMatrixProjection, 0, -ratio, ratio, -1f, 1f, 3f, 20f)//设置透视投影
        Matrix.setLookAtM(mMatrixCamera, 0, 8.0f, 8.0f, 5.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f) //设置相机位置
        Matrix.multiplyMM(mMVPMatrix, 0, mMatrixProjection, 0, mMatrixCamera, 0)//计算变换矩阵
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        super.onSurfaceCreated(gl, config)
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)//开启深度测试,检查颜色覆盖层级
        gProgram = createProgram(gVertexShader, gFragmentShader)
        if (gProgram == 0) {
            LogUtils.dTag(TAG, "Could not create gProgram")
            return
        }
        gPosition = GLES20.glGetAttribLocation(gProgram, "vPosition")
        gColor = GLES20.glGetAttribLocation(gProgram, "color")
        mMatrixHandle = GLES20.glGetUniformLocation(gProgram, "uMVPMatrix")
    }

}