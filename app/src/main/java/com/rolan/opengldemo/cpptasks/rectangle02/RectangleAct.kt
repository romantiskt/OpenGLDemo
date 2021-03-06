package com.rolan.opengldemo.cpptasks.rectangle02

import com.wang.advance.tasks.kotlin.base.BaseKotlinAct

/**
 * Created by wangyang on 2018/7/26.下午3:59
 */
class RectangleAct : BaseKotlinAct() {
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