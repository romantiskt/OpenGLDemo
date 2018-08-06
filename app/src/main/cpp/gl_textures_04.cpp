//
// Created by 王阳 on 2018/8/1.
//


#include <GLES2/gl2.h>
#include <string.h>
#include "Shader.h"
#include <android/bitmap.h>
namespace gl_textures_04 {

#include "gl_textures_04.h"




#include "util.cpp"



#include "stb_image.h"
	
	
	using namespace util;
	
	auto gVertexShader =
			"attribute vec3 vPosition;"//默认精度修饰符
					"varying vec4 vertexColor;"
					"varying vec2 TexCoord;"
					"attribute vec3 color;"
					"attribute vec2 aTexCoord;"
					"void main() {"
					"  gl_Position = vec4(vPosition,1.0);"
					"vertexColor =  vec4(color,1.0);"
					"TexCoord =  aTexCoord;"
					"}";
	
	auto gFragmentShader =
			"precision mediump float;"
					"uniform sampler2D uTexture;"
					"varying vec4 vertexColor;"//切记，着色器里是不支持c++注释的
					"varying vec2 TexCoord;"
					"uniform vec4 ourColor;"
					"void main() {"
					"  gl_FragColor = texture2D(uTexture, TexCoord);"
					"}";
	const GLfloat vertices[] = {
			//顶点数据          //颜色
			0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f,   // 右上角
			0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,// 右下角
			-0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, // 左下角
			-0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, // 左上角
			0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f,  //中间上
	};
	unsigned int indices[] = { // 注意索引从0开始!
			0, 1, 2,// 第一个三角形
			2, 3, 0,
	};
	GLuint gProgram;
	GLuint gPosition;
	GLuint gColor;
	GLuint gTexCoord;
	GLuint muTextureHandle;;
	GLuint gVertexBuff;
	unsigned int EBO;
	unsigned int texture;
	int width, height, nrChannels;
	
	 char * ConvertJByteaArrayToChars (JNIEnv *env, jbyteArray bytearray) {
		char *chars = NULL;
		jbyte *bytes;
		bytes = env->GetByteArrayElements(bytearray, 0);
		int chars_len = env->GetArrayLength(bytearray);
		chars = new char[chars_len + 1];
		memset(chars, 0, chars_len + 1);
		memcpy(chars, bytes, chars_len);
		chars[chars_len] = 0;
//		env->ReleaseByteArrayElements(bytearray, bytes, 0);
		return chars;
	}
	
	
	bool init (jint width, jint height, JNIEnv *env, jobject pJobject) {
		gProgram = createProgram(gVertexShader, gFragmentShader);
		if (!gProgram) {
			LOGI("Could not create gProgram");
			return false;
		}
//		jclass cs=env->GetObjectClass(pJobject);
//		jmethodID callbackStatic = env->GetStaticMethodID( cs, "createImage", "()V");
//		env->CallStaticVoidMethod(cs, callbackStatic);
		jclass clazz = env->FindClass("com/rolan/opengldemo/cpptasks/textures04/Test");
		jmethodID constructor = env->GetMethodID(clazz, "<init>", "()V");
		jobject storeObject = env->NewObject(clazz, constructor);
		
		jmethodID methodId = env->GetMethodID(clazz, "getImage", "()[B");
		jbyteArray jbyteArray1 = (jbyteArray) env->CallObjectMethod(storeObject, methodId);
		int count = env->GetArrayLength(jbyteArray1);
		 char *pmsg = ConvertJByteaArrayToChars(env, jbyteArray1);
		glGenTextures(1, &texture);
		glBindTexture(GL_TEXTURE_2D, texture);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
//        // set texture filtering parameters
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, 10, 10, 0, GL_RGB, GL_UNSIGNED_BYTE, pmsg);
		glGenerateMipmap(GL_TEXTURE_2D);
		
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
		gTexCoord = glGetAttribLocation(gProgram, "aTexCoord");
		static float grey;
		grey += 0.01f;
		if (grey > 1.0f) {
			grey = 0.0f;
		}
		
		glUseProgram(gProgram);
		
		muTextureHandle = glGetUniformLocation(gProgram, "uTexture");
//		glUniform4f(vertexColorLocation, 0.0f, grey, 0.0f, 1.0f);
		glUniform1i(muTextureHandle, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, gVertexBuff);
		glVertexAttribPointer(gPosition, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(GLfloat), (void *) 0);
		glEnableVertexAttribArray(gPosition);
		
		glVertexAttribPointer(gColor, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(GLfloat),
		                      (GLvoid *) (3 * sizeof(GLfloat)));
		glEnableVertexAttribArray(gColor);
		
		glVertexAttribPointer(gTexCoord, 2, GL_FLOAT, GL_FALSE, 8 * sizeof(float),
		                      (void *) (6 * sizeof(float)));
		glEnableVertexAttribArray(gTexCoord);
		
		glBindTexture(GL_TEXTURE_2D, texture);
		glDrawElements(GL_TRIANGLES, 8, GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(gPosition);
		
	}
	
	JNIEXPORT void JNICALL
	Java_com_rolan_opengldemo_cpptasks_textures04_TexturesWidgetEngine_init (JNIEnv *env, jobject obj,
	                                                                      jint width, jint height) {
		init(width, height, env, obj);
	}
	
	
	JNIEXPORT void JNICALL
	Java_com_rolan_opengldemo_cpptasks_textures04_TexturesWidgetEngine_render (JNIEnv *env,
	                                                                        jobject obj) {
		render();
	}
	
	
}