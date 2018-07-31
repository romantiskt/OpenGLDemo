//
// Created by 王阳 on 2018/7/30.
//

#ifndef OPENGLDEMO_GL_RECTANGLE_02_H
#define OPENGLDEMO_GL_RECTANGLE_02_H
#include <jni.h>

extern "C" {

JNIEXPORT void JNICALL
Java_com_rolan_opengldemo_tasks_rectangle02_RectangleWidgetEngine_init (JNIEnv *env, jobject obj,
                                                                        jint width, jint height);
JNIEXPORT void JNICALL
Java_com_rolan_opengldemo_tasks_rectangle02_RectangleWidgetEngine_render (JNIEnv *env,
                                                                        jobject obj);
};


#endif