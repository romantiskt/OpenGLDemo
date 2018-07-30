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


#include <jni.h>
#include <GLES2/gl2.h>
#include <stdio.h>
#include <stdlib.h>


namespace gl_rectangle_02 {

#include "gl_rectangle_02.h"
#include "util.cpp"
using namespace util;
	
	const GLfloat vertices[] = {
			0.5f, 0.5f, 0.0f,   // 右上角
			0.5f, -0.5f, 0.0f,  // 右下角
			-0.5f, -0.5f, 0.0f, // 左下角
			-0.5f, 0.5f, 0.0f,   // 左上角
			0.0f,1.0f,0.0f  //中间上
	};
	unsigned int indices[] = { // 注意索引从0开始!
			0, 1, 3, // 第一个三角形
			1, 2, 3,  // 第二个三角形
			0,3,4,
	};
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
	
	GLuint gProgram;
	GLuint gvPositionHandle;
	GLuint vertexbuffer;
	unsigned int EBO;
	bool setupGraphics (int w, int h) {
		gProgram = createProgram(gVertexShader, gFragmentShader);
		if (!gProgram) {
			LOGE("Could not create program.");
			return false;
		}
		gvPositionHandle = glGetAttribLocation(gProgram, "vPosition");//属性绑定到 vPostion 关键字上
		checkGlError("glGetAttribLocation");//创建
		glViewport(0, 0, w, h);//指定绘图区域，（坐标x ,坐标y,坐标x+width,坐标y+height） 后两个参数不是坐标，而是用宽高来间接指定坐标
		checkGlError("glViewport");
		
		
		glGenBuffers(1, &vertexbuffer);
		glBindBuffer(GL_ARRAY_BUFFER, vertexbuffer);
		glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);
		//创建索引缓冲对象
	
		glGenBuffers(1, &EBO);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(indices), indices, GL_STATIC_DRAW);
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
//		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(gvPositionHandle);//  //启用顶点位置数据
		glBindBuffer(GL_ARRAY_BUFFER, vertexbuffer);
		glVertexAttribPointer(
				0,                  // attribute 0. No particular reason for 0, but must match the layout in the shader.
				3,                  // size
				GL_FLOAT,           // type
				GL_FALSE,           // normalized?
				0,                  // stride
				(void*)0            // array buffer offset
		);
		
		// Draw the triangle !
//        glDrawArrays(GL_TRIANGLES, 0, 3); // 3 indices starting at 0 -> 1 triangle 根据数据画三角形
//		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);//线框模式
//         glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);//填充模式
		glDrawElements(GL_TRIANGLES, 9, GL_UNSIGNED_INT, 0);//根据索引绘制
		glDisableVertexAttribArray(0);
	
	}
	
	JNIEXPORT void JNICALL
	Java_com_rolan_opengldemo_tasks_rectangle02_RectangleWidgetEngine_init (JNIEnv *env,
	                                                                        jobject obj,
	                                                                        jint width,
	                                                                        jint height) {
		setupGraphics(width, height);
	}
	
	JNIEXPORT void JNICALL
	Java_com_rolan_opengldemo_tasks_rectangle02_RectangleWidgetEngine_step (JNIEnv *env,
	                                                                        jobject obj) {
		renderFrame();
	}
	
}