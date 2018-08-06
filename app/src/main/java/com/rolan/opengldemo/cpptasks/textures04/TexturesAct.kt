package com.rolan.opengldemo.cpptasks.textures04

import com.wang.advance.tasks.kotlin.base.BaseKotlinAct

/**
 * Created by wangyang on 2018/8/1.下午2:31
 */
class TexturesAct: BaseKotlinAct() {
    override fun inflateLayoutId(): Any {
        mView = TexturesView(this);
        return mView as TexturesView;
    }

    var mView: TexturesView?=null;


    override fun onPause() {
        super.onPause()
        mView?.onPause();
    }

    override fun onResume() {
        super.onResume()
        mView?.onResume();
    }

}