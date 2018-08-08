package com.rolan.opengldemo.cpptasks.shaders03

import android.content.Context
import com.rolan.opengldemo.base.BaseOpenGLCppView
import javax.microedition.khronos.opengles.GL10

/**
 * Created by wangyang on 2018/7/30.下午3:43
 */
class ShadersView:BaseOpenGLCppView{
    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        ShadersWidgetEngine.init(width,height)
    }

    override fun onDrawFrame(gl: GL10) {
       ShadersWidgetEngine.render()
    }

    constructor(context: Context?) : super(context)
}