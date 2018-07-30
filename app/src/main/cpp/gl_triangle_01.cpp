/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// OpenGL ES 2.0 code


#include <android/log.h>

#include <GLES2/gl2.h>
#include <GLES2/gl2ext.h>

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "util.cpp"


using namespace util;
GLuint gProgram;
GLuint gvPositionHandle;
namespace gl_triangle_01 {

#include "gl_triangle_01.h"
/**
 * 脚本代码
 */
    auto gVertexShader =
            "attribute vec4 vPosition;\n"
                    "void main() {\n"
                    "  gl_Position = vPosition;\n"
                    "}\n";

    auto gFragmentShader =
            "precision mediump float;\n"
                    "void main() {\n"
                    "  gl_FragColor = vec4(0.0, 1.0, 0.0, 1.0);\n"
                    "}\n";

    GLuint loadShader(GLenum shaderType, const char *pSource) {
        GLuint shader = glCreateShader(shaderType);//1.创建一个着色器，返回着色器id，如果返回0则为创建失败
        if (shader) {
            glShaderSource(shader, 1, &pSource, NULL);//2.如果着色器创建成功, 为创建的着色器加载脚本代码
            glCompileShader(shader); //3.编译已经加载脚本代码的着色器
            GLint compiled = 0;
            glGetShaderiv(shader, GL_COMPILE_STATUS, &compiled);  //4.获取着色器的编译情况, 如果结果为0, 说明编译失败
            if (!compiled) {//编译失败
                GLint infoLen = 0;
                glGetShaderiv(shader, GL_INFO_LOG_LENGTH, &infoLen);//如果失败，则获取编译log,
                if (infoLen) {//如果log的长度不为0，则下面处理log,输出，
                    char *buf = (char *) malloc(infoLen);
                    if (buf) {
                        glGetShaderInfoLog(shader, infoLen, NULL, buf);//显示log
                        LOGE("Could not compile shader %d:\n%s\n",
                             shaderType, buf);
                        free(buf);
                    }
                    glDeleteShader(shader);//删除着色器
                    shader = 0;
                }
            }
        }
        return shader;
    }

/**
 * 顶点着色器 片元着色器  可想象成 线段上的 点 和组成线的若干点的集合，着色器都运行在GPU中
 * @param pVertexSource
 * @param pFragmentSource
 * @return
 */
    GLuint createProgram(const char *pVertexSource, const char *pFragmentSource) {
        GLuint vertexShader = loadShader(GL_VERTEX_SHADER, pVertexSource);//加载顶点着色器
        if (!vertexShader) {
            return 0;
        }

        GLuint pixelShader = loadShader(GL_FRAGMENT_SHADER, pFragmentSource);//加载片元着色器
        if (!pixelShader) {
            return 0;
        }

        GLuint program = glCreateProgram(); //3. 创建着色程序, 返回0说明创建失败
        if (program) {
            glAttachShader(program, vertexShader);//4. 向着色程序中加入顶点着色器
            checkGlError("glAttachShader");  //检查glAttachShader操作有没有失败
            glAttachShader(program, pixelShader); //5. 向着色程序中加入片元着色器
            checkGlError("glAttachShader");
            glLinkProgram(program);//6. 链接程序
            GLint linkStatus = GL_FALSE;
            glGetProgramiv(program, GL_LINK_STATUS, &linkStatus);//获取链接程序结果
            if (linkStatus != GL_TRUE) {//链接程序失败
                GLint bufLength = 0;//获取日志打印
                glGetProgramiv(program, GL_INFO_LOG_LENGTH, &bufLength);
                if (bufLength) {
                    char *buf = (char *) malloc(bufLength);
                    if (buf) {
                        glGetProgramInfoLog(program, bufLength, NULL, buf);
                        LOGE("Could not link program:\n%s\n", buf);
                        free(buf);
                    }
                }
                glDeleteProgram(program);
                program = 0;
            }
        }
        return program;
    }


    bool setupGraphics(int w, int h) {
//    printGLString("Version", GL_VERSION);
//    printGLString("Vendor", GL_VENDOR);
//    printGLString("Renderer", GL_RENDERER);
//    printGLString("Extensions", GL_EXTENSIONS);
//
//    LOGI("setupGraphics(%d, %d)", w, h);
        gProgram = createProgram(gVertexShader, gFragmentShader);
        if (!gProgram) {
            LOGE("Could not create program.");
            return false;
        }
        gvPositionHandle = glGetAttribLocation(gProgram, "vPosition");//属性绑定到 vPostion 关键字上
        checkGlError("glGetAttribLocation");//创建
//    LOGI("glGetAttribLocation(\"vPosition\") = %d\n",
//            gvPositionHandle);

        glViewport(0, 0, w, h);//指定绘图区域，（坐标x ,坐标y,坐标x+width,坐标y+height） 后两个参数不是坐标，而是用宽高来间接指定坐标
        checkGlError("glViewport");
        return true;
    }

    const GLfloat gTriangleVertices[] = {
            -0.5f, 0.5f,
            0.5f, 0.5f,
            -0.5f, -0.5f,
            0.5f, -0.5f};

    void renderFrame() {
        static float grey;
        grey += 0.01f;
        if (grey > 1.0f) {
            grey = 0.0f;
        }
        glClearColor(grey, grey, grey, 1.0f);//设置屏幕背景色
        checkGlError("glClearColor");
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT); //清除深度缓冲与颜色缓冲
        checkGlError("glClear");

        glUseProgram(gProgram);  //根据着色程序id 指定要使用的着色器
        checkGlError("glUseProgram");

        glVertexAttribPointer(gvPositionHandle, 2, GL_FLOAT, GL_FALSE, 0,
                              gTriangleVertices);//将顶点位置数据传送进渲染管线, 为画笔指定定点的位置数据
        checkGlError("glVertexAttribPointer");
        glEnableVertexAttribArray(gvPositionHandle);//  //启用顶点位置数据
        checkGlError("glEnableVertexAttribArray");
        glDrawArrays(GL_TRIANGLES, 0, 3);// //执行绘制
        checkGlError("glDrawArrays");
    }

    JNIEXPORT void JNICALL
    Java_com_rolan_opengldemo_tasks_triangle01_TriangleWidgetEngine_init(JNIEnv *env, jobject obj,
                                                                         jint width, jint height) {
        setupGraphics(width, height);
    }

    JNIEXPORT void JNICALL
    Java_com_rolan_opengldemo_tasks_triangle01_TriangleWidgetEngine_step(JNIEnv *env, jobject obj) {
        renderFrame();
    }
}