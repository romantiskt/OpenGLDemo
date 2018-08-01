package com.rolan.opengldemo.tasks.textures04

import android.content.Context
import com.rolan.opengldemo.base.BaseOpenGLView
import javax.microedition.khronos.opengles.GL10

/**
 * Created by wangyang on 2018/8/1.下午2:31
 */
class TexturesView : BaseOpenGLView {
    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        TexturesWidgetEngine.init(width,height)
    }

    override fun onDrawFrame(gl: GL10) {
        TexturesWidgetEngine.render()
    }

    constructor(context: Context?) : super(context)
}