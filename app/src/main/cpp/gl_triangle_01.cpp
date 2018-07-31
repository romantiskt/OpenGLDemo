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

GLuint gProgram;
GLuint gvPositionHandle;
namespace gl_triangle_01 {

#include "gl_triangle_01.h"

#include "util.cpp"
	
	using namespace util;
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
	
	bool setupGraphics (int w, int h) {
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
	
	void renderFrame () {
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
	Java_com_rolan_opengldemo_tasks_triangle01_TriangleWidgetEngine_init (JNIEnv *env, jobject obj,
	                                                                      jint width, jint height) {
		setupGraphics(width, height);
	}
	
	JNIEXPORT void JNICALL
	Java_com_rolan_opengldemo_tasks_triangle01_TriangleWidgetEngine_render (JNIEnv *env,
	                                                                      jobject obj) {
		renderFrame();
	}
}