package com.rolan.opengldemo.kotlintasks.rectangle_02

import com.rolan.opengldemo.kotlintasks.triangle_01.TriangleView
import com.wang.advance.tasks.kotlin.base.BaseKotlinAct

/**
 * Created by wangyang on 2018/8/8.下午2:11
 */

class RectangleAct:BaseKotlinAct(){
    override fun inflateLayoutId(): Any {
        mView = RectangleView(this);
        return mView as RectangleView;
    }

    var mView: RectangleView?=null;


    override fun onPause() {
        super.onPause()
        mView?.onPause();
    }

    override fun onResume() {
        super.onResume()
        mView?.onResume();
    }
}