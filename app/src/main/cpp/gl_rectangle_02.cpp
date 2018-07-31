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
			0.0f, 1.0f, 0.0f  //中间上
	};
	unsigned int indices[] = { // 注意索引从0开始!
			0, 1, 3, // 第一个三角形
			1, 2, 3,  // 第二个三角形
			0, 3, 4,
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
		glViewport(0, 0, w, h);//指定绘图区域，（坐标x ,坐标y,坐标x+width,坐标y+height） 后两个参数不是坐标，而是用宽高来间接指定坐标
		
		//创建顶点数组缓冲对象
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
		glEnableVertexAttribArray(gvPositionHandle);  //启用顶点位置数据
//		glClearColor(grey, grey, grey, 1.0f);//设置屏幕背景色
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);//设置屏幕背景色
		glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT); //清除深度缓冲与颜色缓冲
		
		glUseProgram(gProgram);  //根据着色程序id 指定要使用的着色器
		
		glBindBuffer(GL_ARRAY_BUFFER, vertexbuffer);
		glVertexAttribPointer(
				0,                 // 顶点属性，即前面设置的gvPositionHandle                   
				3,                 // 顶点属性的大小，这里为ve3 ,即size=3                       
				GL_FLOAT,          // 顶点数据的数据类型                                     
				GL_FALSE,          // 数据是否需要标准化 会被映射成0~1之间  有符号为-1到1                
				0,                 // 连续的顶点属性之间的间隔                                  
				(void *) 0           // 位置数据在缓冲中的偏移量                                
		);
//		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);//线框模式
//         glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);//填充模式
		glDrawElements(GL_TRIANGLES, 9, GL_UNSIGNED_INT, 0);//根据索引绘制
		glDisableVertexAttribArray(0);     //关闭顶点位置数据
		
	}
	
	JNIEXPORT void JNICALL
	Java_com_rolan_opengldemo_tasks_rectangle02_RectangleWidgetEngine_init (JNIEnv *env,
	                                                                        jobject obj,
	                                                                        jint width,
	                                                                        jint height) {
		setupGraphics(width, height);
	}
	
	JNIEXPORT void JNICALL
	Java_com_rolan_opengldemo_tasks_rectangle02_RectangleWidgetEngine_render (JNIEnv *env,
	                                                                        jobject obj) {
		renderFrame();
	}
	
}