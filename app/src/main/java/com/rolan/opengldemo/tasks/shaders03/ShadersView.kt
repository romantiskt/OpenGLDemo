package com.rolan.opengldemo.tasks.shaders03

import android.content.Context
import com.rolan.opengldemo.base.BaseOpenGLView
import javax.microedition.khronos.opengles.GL10

/**
 * Created by wangyang on 2018/7/30.下午3:43
 */
class ShadersView:BaseOpenGLView{
    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        ShadersWidgetEngine.init(width,height)
    }

    override fun onDrawFrame(gl: GL10) {
       ShadersWidgetEngine.step()
    }

    constructor(context: Context?) : super(context)
}