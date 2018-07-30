package com.rolan.opengldemo.tasks.shaders03

import com.rolan.opengldemo.tasks.rectangle02.RectangleView
import com.wang.advance.tasks.kotlin.base.BaseKotlinAct

/**
 * Created by wangyang on 2018/7/30.下午3:42
 */
class ShadersAct:BaseKotlinAct() {
    override fun inflateLayoutId(): Any {
        mView = ShadersView(this);
        return mView as ShadersView;
    }

    var mView: ShadersView?=null;


    override fun onPause() {
        super.onPause()
        mView?.onPause();
    }

    override fun onResume() {
        super.onResume()
        mView?.onResume();
    }

}