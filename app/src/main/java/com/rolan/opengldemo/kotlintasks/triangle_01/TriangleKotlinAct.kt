package com.rolan.opengldemo.kotlintasks.triangle_01

import com.rolan.opengldemo.cpptasks.triangle01.TriangleKotlinView
import com.wang.advance.tasks.kotlin.base.BaseKotlinAct

/**
 * Created by wangyang on 2018/8/8.上午10:50
 */

class TriangleKotlinAct:BaseKotlinAct(){
    override fun inflateLayoutId(): Any {
        mView = TriangleView(this);
        return mView as TriangleView;
    }

    var mView: TriangleView?=null;


    override fun onPause() {
        super.onPause()
        mView?.onPause();
    }

    override fun onResume() {
        super.onResume()
        mView?.onResume();
    }
}