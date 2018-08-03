//
// Created by 王阳 on 2018/8/1.
//


#include <GLES2/gl2.h>
#include "Shader.h"

namespace gl_textures_04 {

#include "gl_textures_04.h"

#include "util.cpp"
#include "stb_image.h"
	
	
	using namespace util;
	
	auto gVertexShader =
			"attribute vec3 vPosition;"//默认精度修饰符
					"varying vec4 vertexColor;"
					"attribute vec3 color;"
					"void main() {"
					"  gl_Position = vec4(vPosition,1.0);"
					"vertexColor =  vec4(color,1.0);"
					"}";
	
	auto gFragmentShader =
			"precision mediump float;"
					"varying vec4 vertexColor;"//切记，着色器里是不支持c++注释的
					"uniform vec4 ourColor;"
					"void main() {"
					"  gl_FragColor = vertexColor;"
					"}";
	const GLfloat vertices[] = {
			//顶点数据          //颜色
			0.5f, 0.5f, 0.0f,   1.0f, 0.0f, 0.0f,   // 右上角
			0.5f, -0.5f, 0.0f,  0.0f, 1.0f, 0.0f,  // 右下角
			-0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f,// 左下角
			-0.5f, 0.5f, 0.0f,  1.0f, 0.0f, 1.0f, // 左上角
			0.0f, 1.0f, 0.0f,   1.0f, 0.0f, 0.0f, //中间上
	};
	unsigned int indices[] = { // 注意索引从0开始!
			0, 1, 2,// 第一个三角形
			2, 3, 0,
	};
	GLuint gProgram;
	GLuint gPosition;
	GLuint gColor;
	GLuint gVertexBuff;
	unsigned int EBO;
	int width, height, nrChannels;
	bool init (jint width, jint height, JNIEnv *env, jobject pJobject) {
		gProgram = createProgram(gVertexShader, gFragmentShader);
		if (!gProgram) {
			LOGI("Could not create gProgram");
			return false;
		}
//		jclass cs=env->GetObjectClass(pJobject);
//		jmethodID callbackStatic = env->GetStaticMethodID( cs, "createImage", "()V");
//		env->CallStaticVoidMethod(cs, callbackStatic);
		jclass clazz = env->FindClass("com/rolan/opengldemo/tasks/textures04/Test");
		jmethodID constructor = env->GetMethodID(clazz, "<init>", "()V");
		jobject storeObject = env->NewObject(clazz, constructor);
		
		jmethodID methodId = env->GetMethodID(clazz, "getImage", "()[B");
		jbyteArray jbyteArray1=(jbyteArray)env->CallObjectMethod(storeObject,methodId);
		int count = env->GetArrayLength(jbyteArray1);
//		stbi_set_flip_vertically_on_load(true); // tell stb_image.h to flip loaded texture's on the y-axis.
		// The FileSystem::getPath(...) is part of the GitHub repository so we can find files on any IDE/platform; replace it with your own image path.
//		unsigned char *data = stbi_load(FileSystem::getPath("resources/textures/container.jpg").c_str(), &width, &height, &nrChannels, 0);
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
		glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
		
		gPosition = glGetAttribLocation(gProgram, "vPosition");
		gColor = glGetAttribLocation(gProgram, "color");
		
		
		glUseProgram(gProgram);
		
		glBindBuffer(GL_ARRAY_BUFFER, gVertexBuff);
		glVertexAttribPointer(gPosition, 3, GL_FLOAT, GL_FALSE,  6 * sizeof(GLfloat), (void *) 0);
		glEnableVertexAttribArray(gPosition);
		
		glVertexAttribPointer(gColor, 3, GL_FLOAT, GL_FALSE, 6 * sizeof(GLfloat), (GLvoid*)(3 * sizeof(GLfloat)));
		glEnableVertexAttribArray(gColor);
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(gPosition);
		
	}
	
	JNIEXPORT void JNICALL
	Java_com_rolan_opengldemo_tasks_textures04_TexturesWidgetEngine_init (JNIEnv *env, jobject obj,
	                                                                      jint width, jint height){
		init(width, height, env, obj);
	}
	
	
	JNIEXPORT void JNICALL
	Java_com_rolan_opengldemo_tasks_textures04_TexturesWidgetEngine_render (JNIEnv *env, jobject obj){
		render();
	}
	
	
}