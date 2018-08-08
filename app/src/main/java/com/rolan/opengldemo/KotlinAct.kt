package com.rolan.opengldemo

import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.rolan.opengldemo.kotlintasks.widget.*
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

    @OnClick(R.id.tv_triangle,R.id.tv_rectangle,R.id.tv_shaders,R.id.tv_textures
    ,R.id.tv_transform)
    fun click(view: View) {
        when (view.id) {
            R.id.tv_triangle -> {
                goWidget(TriangleView_01::class.java)
            }
            R.id.tv_rectangle->{
                goWidget(RectangleView_02::class.java)
            }
            R.id.tv_shaders->{
                goWidget(ShaderView_03::class.java)
            }
            R.id.tv_textures->{
                goWidget(TexturesView_04::class.java)
            }
            R.id.tv_transform->{
                goWidget(TransformView_05::class.java)
            }
        }
    }

}