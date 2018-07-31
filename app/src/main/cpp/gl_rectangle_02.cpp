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
	
	auto gVertexShader =
			"attribute vec4 vPosition;\n"
					"void main() {\n"
					"  gl_Position = vPosition;\n"
//					"   gl_PointSize = 100;\n"   //设置点的大小
					"}\n";
	
	auto gFragmentShader =
			"precision mediump float;\n"
					"void main() {\n"
					"  gl_FragColor = vec4(0.0, 1.0, 0.0, 1.0);\n"
					"}\n";
	const GLfloat vertices[] = {
			0.5f, 0.5f, 0.0f,   // 右上角
			0.5f, -0.5f, 0.0f,  // 右下角
			-0.5f, -0.5f, 0.0f, // 左下角
			-0.5f, 0.5f, 0.0f,   // 左上角
			0.0f, 1.0f, 0.0f  //中间上
	};
	unsigned int indices[] = { // 注意索引从0开始!
			0,1, 2,// 第一个三角形
			2,3,0,
	};
	GLuint gProgram;
	GLuint gPosition;
	GLuint gVertexBuff;
	unsigned int EBO;
	
	bool init (int width, int height) {
		gProgram = createProgram(gVertexShader, gFragmentShader);
		if (!gProgram) {
			LOGI("Could not create gProgram");
			return false;
		}
		gPosition = glGetAttribLocation(gProgram, "vPosition");
		glViewport(0, 0, width, height);
		
		glGenBuffers(1, &gVertexBuff);
		glBindBuffer(GL_ARRAY_BUFFER, gVertexBuff);
		glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);
		
		
		glGenBuffers(1, &EBO);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(indices), indices, GL_STATIC_DRAW);
		
		return true;
		
	}
	
	void render () {
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);//设置屏幕背景色
		glClear(GL_DEPTH_BUFFER_BIT|GL_COLOR_BUFFER_BIT);
		glEnableVertexAttribArray(gPosition);
		
		glUseProgram(gProgram);
		
		glBindBuffer(GL_ARRAY_BUFFER,gVertexBuff);
		glVertexAttribPointer(gPosition,3,GL_FLOAT,GL_FALSE,0,(void*)0);
//		GL_POINTS  需要设置点的大小 在着色器中
//		GL_LINES   每一对顶点解释成一条直线
//		GL_LINE_LOOP  连接尾部的连续的直线
//		GL_LINE_STRIP 连续的直线
//		GL_TRIANGLES    每三个顶点绘制一个三角形
//		GL_TRIANGLE_STRIP  依赖于前两个出现的顶点，如果当前顶点是奇数，则三角形为 n-1 n-2 n  如果为偶数 则 n-2 n-1 n
//		GL_TRIANGLE_FAN
		glDrawElements(GL_TRIANGLES,6,GL_UNSIGNED_INT,0);
		glDisableVertexAttribArray(gPosition);
		
	}
	
	JNIEXPORT void JNICALL
	Java_com_rolan_opengldemo_tasks_rectangle02_RectangleWidgetEngine_init (JNIEnv *env,
	                                                                        jobject obj,
	                                                                        jint width,
	                                                                        jint height) {
		init(width, height);
	}
	
	JNIEXPORT void JNICALL
	Java_com_rolan_opengldemo_tasks_rectangle02_RectangleWidgetEngine_render (JNIEnv *env,
	                                                                        jobject obj) {
		render();
	}
	
}