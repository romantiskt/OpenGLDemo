package com.rolan.opengldemo.utils

import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.rolan.opengldemo.BuildConfig
import kotlin.math.log

/**
 * Created by wangyang on 2018/7/26.下午3:05
 */
object LogUtils{
    val V = Log.VERBOSE
    val D = Log.DEBUG
    val I = Log.INFO
    val W = Log.WARN
    val E = Log.ERROR

    val TAG = "LG_SI"
    val CONSOLE_FILTER = V
    private var gson = Gson()


    fun getGson(): Gson {
        if (gson == null)
            gson = Gson()
        return gson
    }

    fun v(contents: String) {
        log(V, TAG, contents)
    }

    fun vTag(tag: String, contents: String) {
        log(V, tag, contents)
    }

    fun d(contents: String) {
        log(D, TAG, contents)
    }

    fun dTag(tag: String?, contents: String) {
        log(D, tag, contents)
    }

    fun i(contents: String) {
        log(I, TAG, contents)
    }

    fun iTag(tag: String, contents: String) {
        log(I, tag, contents)
    }

    fun w(contents: String) {
        log(W, TAG, contents)
    }

    fun wTag(tag: String, contents: String) {
        log(W, tag, contents)
    }

    fun e(contents: String) {
        log(E, TAG, contents)
    }

    fun eTag(tag: String, contents: String) {
        log(E, tag, contents)
    }

    fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }

    fun json(contents: Any) {
        json(TAG, contents)
    }

    fun json(tag: String, contents: Any?) {
        if (isDebug() && contents != null) {
            log(D, tag, getGson().toJson(contents))
        }
    }

    fun log(type: Int, tag: String?, contents: String) {
        if (isDebug()) {
            if (type < CONSOLE_FILTER || TextUtils.isEmpty(contents)) return
            when (type) {
                V -> Log.v(tag, contents)
                D -> Log.d(tag, contents)
                W -> Log.w(tag, contents)
                E -> Log.e(tag, contents)
            }
        }
    }

}