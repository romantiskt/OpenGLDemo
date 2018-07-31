//
// Created by 王阳 on 2018/7/30.
//


#include <GLES2/gl2.h>
#include "Shader.h"

namespace gl_shaders_03 {

//

#include "gl_shaders_03.h"

#include "util.cpp"
	
	
	using namespace util;
	
	auto gVertexShader =
			"#version 100"
			"attribute vec4 vPosition;"//默认精度修饰符
					"varying vec4 vertexColor;"
					"void main() {"
					"  gl_Position = vPosition;"
					"vertexColor = vec4(0.5, 0.0, 0.0, 1.0);"
//					"   gl_PointSize = 100;"   //设置点的大小
					"}";
	
	auto gFragmentShader =
			"#version 100"
			"precision mediump float;"
					"varying vec4 vertexColor;"//切记，着色器里是不支持c++注释的
					"uniform vec4 ourColor;"
					"void main() {"
//					"  gl_FragColor = vertexColor;"
					"  gl_FragColor = ourColor;"
					"}";
	const GLfloat vertices[] = {
			//顶点数据          //颜色
			0.5f, 0.5f, 0.0f,   //1.0f, 0.0f, 0.0f,   // 右上角
			0.5f, -0.5f, 0.0f,  //0.0f, 1.0f, 0.0f,  // 右下角
			-0.5f, -0.5f, 0.0f, //0.0f, 0.0f, 1.0f,// 左下角
			-0.5f, 0.5f, 0.0f,  //1.0f, 0.0f, 1.0f, // 左上角
			0.0f, 1.0f, 0.0f,   //1.0f, 0.0f, 0.0f, //中间上
	};
	unsigned int indices[] = { // 注意索引从0开始!
			0, 1, 2,// 第一个三角形
			2, 3, 0,
	};
	GLuint gProgram;
	GLuint gPosition;
	GLuint gVertexBuff;
	unsigned int EBO;
	Shader ourShader("shader.vs", "shader.fs");
	
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



//		ourShader.use();
		return true;
		
	}
	
	void render () {
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);//设置屏幕背景色
		glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
		glEnableVertexAttribArray(gPosition);
		
		static float grey;
		grey += 0.01f;
		if (grey > 1.0f) {
			grey = 0.0f;
		}
		
		glUseProgram(gProgram);
		int vertexColorLocation = glGetUniformLocation(gProgram, "ourColor");
		glUniform4f(vertexColorLocation, 0.0f, grey, 0.0f, 1.0f);
		
		glBindBuffer(GL_ARRAY_BUFFER, gVertexBuff);
		glVertexAttribPointer(gPosition, 3, GL_FLOAT, GL_FALSE, 0, (void *) 0);
//		GL_POINTS  需要设置点的大小 在着色器中
//		GL_LINES   每一对顶点解释成一条直线
//		GL_LINE_LOOP  连接尾部的连续的直线
//		GL_LINE_STRIP 连续的直线
//		GL_TRIANGLES    每三个顶点绘制一个三角形
//		GL_TRIANGLE_STRIP  依赖于前两个出现的顶点，如果当前顶点是奇数，则三角形为 n-1 n-2 n  如果为偶数 则 n-2 n-1 n
//		GL_TRIANGLE_FAN
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(gPosition);
		
	}
	
	JNIEXPORT void JNICALL
	Java_com_rolan_opengldemo_tasks_shaders03_ShadersWidgetEngine_init (JNIEnv *env, jobject obj,
	                                                                    jint width, jint height) {
		init(width, height);
	}
	
	
	JNIEXPORT void JNICALL
	Java_com_rolan_opengldemo_tasks_shaders03_ShadersWidgetEngine_render (JNIEnv *env,
	                                                                      jobject obj) {
		render();
	}
	
	
}