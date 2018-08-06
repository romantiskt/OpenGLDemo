//
// Created by 王阳 on 2018/8/1.
//

#ifndef OPENGLDEMO_GL_TEXTURES_04_H
#define OPENGLDEMO_GL_TEXTURES_04_H

#include <jni.h>

extern "C" {


JNIEXPORT void JNICALL
Java_com_rolan_opengldemo_cpptasks_textures04_TexturesWidgetEngine_init (JNIEnv *env, jobject obj,
                                                                    jint width, jint height);

JNIEXPORT void JNICALL
Java_com_rolan_opengldemo_cpptasks_textures04_TexturesWidgetEngine_render (JNIEnv *env, jobject obj);
};


#endif //OPENGLDEMO_GL_TEXTURES_04_H
