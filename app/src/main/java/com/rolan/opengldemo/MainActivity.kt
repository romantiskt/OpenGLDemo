package com.rolan.opengldemo

import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
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

    @OnClick(R.id.tv_triangle)
    fun click(view: View) {
        when (view.id) {
            R.id.tv_triangle -> {
                go(TriangleAct::class.java)
            }
        }
    }

}
