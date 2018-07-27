package com.rolan.opengldemo.tasks.rectangle02

import android.content.Context
import android.opengl.GLSurfaceView
import com.rolan.opengldemo.base.BaseOpenGLView
import javax.microedition.khronos.opengles.GL10

/**
 * Created by wangyang on 2018/7/26.下午4:00
 */

class RectangleView :BaseOpenGLView{
    override fun onDrawFrame(gl: GL10) {
       RectangleWidgetEngine.step()
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
      RectangleWidgetEngine.init(width,height)
    }

    constructor(context: Context?) : super(context)
}