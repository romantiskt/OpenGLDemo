package com.rolan.opengldemo

import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.rolan.opengldemo.cpptasks.rectangle02.RectangleAct
import com.rolan.opengldemo.cpptasks.shaders03.ShadersAct
import com.rolan.opengldemo.cpptasks.textures04.TexturesAct
import com.rolan.opengldemo.cpptasks.triangle01.TriangleAct
import com.wang.advance.tasks.kotlin.base.BaseKotlinAct

class MainActivity : BaseKotlinAct() {

    override fun inflateLayoutId(): Any {
        return R.layout.activity_main
    }

    override fun afterInflateView() {
        super.afterInflateView()
        setUnBinder(ButterKnife.bind(this))
    }

    @OnClick(R.id.tv_cpp,R.id.tv_kotlin)
    fun click(view: View) {
        when (view.id) {
            R.id.tv_cpp -> {
                go(CppAct::class.java)
            }
            R.id.tv_kotlin->{
                go(KotlinAct::class.java)
            }
        }
    }

}
