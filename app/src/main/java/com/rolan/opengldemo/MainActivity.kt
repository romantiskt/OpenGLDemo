package com.rolan.opengldemo

import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.rolan.opengldemo.tasks.rectangle02.RectangleAct
import com.rolan.opengldemo.tasks.shaders03.ShadersAct
import com.rolan.opengldemo.tasks.textures04.Test
import com.rolan.opengldemo.tasks.textures04.TexturesAct
import com.rolan.opengldemo.tasks.triangle01.TriangleAct
import com.wang.advance.tasks.kotlin.base.BaseKotlinAct

class MainActivity : BaseKotlinAct() {

    override fun inflateLayoutId(): Any {
        return R.layout.activity_main
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
