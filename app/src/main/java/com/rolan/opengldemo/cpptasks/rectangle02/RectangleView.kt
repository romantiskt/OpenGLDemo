package com.rolan.opengldemo.cpptasks.rectangle02

import android.content.Context
import com.rolan.opengldemo.base.BaseOpenGLCppView
import javax.microedition.khronos.opengles.GL10

/**
 * Created by wangyang on 2018/7/26.下午4:00
 */

class RectangleView :BaseOpenGLCppView{
    override fun onDrawFrame(gl: GL10) {
       RectangleWidgetEngine.render()
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
      RectangleWidgetEngine.init(width,height)
    }

    constructor(context: Context?) : super(context)
}