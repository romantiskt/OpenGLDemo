package com.rolan.opengldemo

import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.rolan.opengldemo.cpptasks.rectangle02.RectangleAct
import com.rolan.opengldemo.cpptasks.shaders03.ShadersAct
import com.rolan.opengldemo.cpptasks.textures04.TexturesAct
import com.rolan.opengldemo.cpptasks.triangle01.TriangleAct
import com.wang.advance.tasks.kotlin.base.BaseKotlinAct

/**
 * Created by wangyang on 2018/8/6.下午9:09
 */
class KotlinAct:BaseKotlinAct(){
    override fun inflateLayoutId(): Any {
        return R.layout.activity_kotlin
    }

    override fun afterInflateView() {
        super.afterInflateView()
        setUnBinder(ButterKnife.bind(this))
    }

    @OnClick(R.id.tv_triangle,R.id.tv_rectangle,R.id.tv_shaders,R.id.tv_textures)
    fun click(view: View) {
        when (view.id) {
            R.id.tv_triangle -> {
                go(TriangleAct::class.java)
            }
            R.id.tv_rectangle->{
                go(RectangleAct::class.java)
            }
            R.id.tv_shaders->{
                go(ShadersAct::class.java)
            }
            R.id.tv_textures->{
                go(TexturesAct::class.java)
//                Test().getImage()
            }
        }
    }

}