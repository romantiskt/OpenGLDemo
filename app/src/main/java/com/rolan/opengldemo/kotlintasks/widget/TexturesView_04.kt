package com.rolan.opengldemo.kotlintasks.widget

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.glGetAttribLocation
import com.rolan.opengldemo.base.BaseOpenGLKotlinView
import com.rolan.opengldemo.utils.LogUtils
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import android.opengl.GLUtils
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.opengl.Matrix
import com.rolan.opengldemo.MyApp
import com.rolan.opengldemo.R
import android.opengl.Matrix.perspectiveM

/**
 * Created by wangyang on 2018/8/8.下午6:12
 * 颜色混合的三角形
 */

class TexturesView_04(context: Context?) : BaseOpenGLKotlinView(context) {
    private var gProgram: Int = 0
    private var gPosition: Int = 0
    private var gColor: Int = 0
    private var mTexCoordHandle: Int = 0
    private var mTexSamplerHandle: Int = 0
    private var mMatrixHandle: Int = 0
    private var EBO: Int = 0
    private val mTexName:Int=0;
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
    var textures:FloatArray= floatArrayOf(//顺序改变可对纹理产生旋转的效果 支持 90 180 270度旋转
             // top left
             // bottom left

            1.0f, 0.0f, // bottom right
            1.0f, 1.0f, // top right
            0.0f, 1.0f,
            0.0f, 0.0f




    )
    var gVertexShader =
            "attribute vec4 vPosition;\n" +
                    "attribute vec3 color;\n"+
                    "varying vec4 vertexColor;\n"+
                    "attribute vec2 a_texCoord;\n" +
                    "varying vec2 v_texCoord;\n" +
                    "uniform mat4 uMVPMatrix;\n" +
                    "void main() {\n" +
                    "  gl_Position =  vPosition;;\n" +
                    "vertexColor = vec4(color,1.0);\n"+
                    " v_texCoord = vec2(a_texCoord.x,a_texCoord.y);" +
//					"   gl_PointSize = 100;\n"+   //设置点的大小
                    "}\n"
    var gFragmentShader =
            "precision mediump float;\n" +
                    "varying vec4 vertexColor;\n"+
                    "varying vec2 v_texCoord;\n" +
                    "uniform sampler2D s_texture;\n" +
                    "void main() {\n" +
                    "  gl_FragColor = texture2D(s_texture, v_texCoord);\n" +
                    "}\n"

    override fun onDrawFrame(gl: GL10) {
        // 获取图形的顶点坐标
        var verticesBuf = getFloatArraryBuffer(vertices);
        var indexBuf = getShortArraryBuffer(indices);
        var colorBuf = getFloatArraryBuffer(color);
        var texturesBuf = getFloatArraryBuffer(textures);

        GLES20.glUseProgram(gProgram);
        GLES20.glUniform1i(mTexSamplerHandle, 0);

        GLES20.glVertexAttribPointer(gPosition, 3, GLES20.GL_FLOAT, false, 3*4, verticesBuf);
        GLES20.glEnableVertexAttribArray(gPosition);

        //设置绘制三角形的颜色
        GLES20.glVertexAttribPointer(gColor, 4,GLES20.GL_FLOAT, false,3*4, colorBuf)
        GLES20.glEnableVertexAttribArray(gColor)


        GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0,
                texturesBuf)
        GLES20.glEnableVertexAttribArray(mTexCoordHandle)

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
        mTexCoordHandle = GLES20.glGetAttribLocation(gProgram, "a_texCoord");
        mTexSamplerHandle = GLES20.glGetUniformLocation(gProgram, "s_texture");
        mMatrixHandle = GLES20.glGetUniformLocation(gProgram, "uMVPMatrix");

         var texNames = IntArray(1)
        GLES20.glGenTextures(1, texNames, 0)
        val mTexName = texNames[0]
        val bitmap:Bitmap = BitmapFactory.decodeResource(mContext.resources,R.drawable.xiaoxiong)
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTexName)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_REPEAT)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_REPEAT)
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
        bitmap.recycle()
    }

}



