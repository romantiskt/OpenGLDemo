//
// Created by 王阳 on 2018/7/30.
//
#include <GLES2/gl2ext.h>
#include <GLES2/gl2.h>
#include <stdio.h>
#include <stdlib.h>
#define  LOG_TAG    "libgl2jni"

#include <android/log.h>

#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
namespace util {
	
	
	void printGLString (const char *name, GLenum s) {
		const char *v = (const char *) glGetString(s);
		LOGI("GL %s = %s\n", name, v);
	}
	
	void checkGlError (const char *op) {
		for (GLint error = glGetError(); error; error = glGetError()) {
			LOGI("after %s() glError (0x%x)\n", op, error);
		}
	}
	
	GLuint loadShader (GLenum shaderType, const char *pSource) {
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
	GLuint createProgram (const char *pVertexSource, const char *pFragmentSource) {
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
}