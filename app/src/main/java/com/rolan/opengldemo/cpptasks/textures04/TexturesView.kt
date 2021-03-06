package com.rolan.opengldemo.cpptasks.textures04

import android.content.Context
import com.rolan.opengldemo.base.BaseOpenGLCppView
import javax.microedition.khronos.opengles.GL10

/**
 * Created by wangyang on 2018/8/1.下午2:31
 */
class TexturesView : BaseOpenGLCppView {
    private val engine = TexturesWidgetEngine()
    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        engine.init(width,height)
    }

    override fun onDrawFrame(gl: GL10) {
        engine.render()
    }

    constructor(context: Context?) : super(context)
}