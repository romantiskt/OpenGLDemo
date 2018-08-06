package com.rolan.opengldemo.cpptasks.textures04

import com.rolan.opengldemo.utils.LogUtils
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import com.rolan.opengldemo.MyApp
import com.rolan.opengldemo.R


/**
 * Created by wangyang on 2018/8/3.下午5:28
 */
class Test {

    fun getStr() {
        LogUtils.dTag("wang", "我就是这样被愉快得调用了")
    }

    fun getImage(): ByteArray {
        LogUtils.dTag("wang", "我就是这样被愉快得调用了*******getImage")
        val open = MyApp.context.assets.open("head.png")
        var size: Int = open.available()
        val buffer = ByteArray(size)
        open?.read(buffer)
        open?.close()
        return buffer
    }

    fun getBitmap():Bitmap{
        val bitmap = BitmapFactory.decodeResource(MyApp.context.resources, R.mipmap.ic_launcher);
        return bitmap;
    }
}