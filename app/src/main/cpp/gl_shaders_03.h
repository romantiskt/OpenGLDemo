//
// Created by 王阳 on 2018/7/30.
//

#ifndef OPENGLDEMO_GL_SHADERS_03_H
#define OPENGLDEMO_GL_SHADERS_03_H
#include <jni.h>

extern "C" {


JNIEXPORT void JNICALL
Java_com_rolan_opengldemo_tasks_shaders03_ShadersWidgetEngine_init (JNIEnv *env, jobject obj,
                                                                      jint width, jint height);

JNIEXPORT void JNICALL
Java_com_rolan_opengldemo_tasks_shaders03_ShadersWidgetEngine_render (JNIEnv *env, jobject obj);
};

#endif //OPENGLDEMO_GL_SHADERS_03_H
