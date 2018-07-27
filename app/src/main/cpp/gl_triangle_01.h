//
// Created by 王阳 on 2018/7/27.
//

#ifndef OPENGLDEMO_GL_TRIANGLE_01_H
#define OPENGLDEMO_GL_TRIANGLE_01_H
#include <jni.h>

extern "C" {

GLuint gProgram;
GLuint gvPositionHandle;

    JNIEXPORT void JNICALL
    Java_com_rolan_opengldemo_tasks_triangle01_TriangleWidgetEngine_init(JNIEnv *env, jobject obj,
                                                                         jint width, jint height);

    JNIEXPORT void JNICALL
    Java_com_rolan_opengldemo_tasks_triangle01_TriangleWidgetEngine_step(JNIEnv *env, jobject obj);
};


#endif //OPENGLDEMO_GL_TRIANGLE_01_H
