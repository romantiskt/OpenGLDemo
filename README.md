# OpenGLDemo

#### OpenGL ES 2.0 和 OpenGL ES 3.0
 
 * android版本支持
 
    OpenGL ES 2.0 :api>12 
    
    OpenGL ES 3.0 :api>18 

#### GLSL 100 和 GLSL 300 
[参考链接](https://blog.csdn.net/ym19860303/article/details/44115135)
* GLSL 100
```
uniform mat4 projTrans;

attribute vec2 Position;
attribute vec2 TexCoord;

varying vec2 vTexCoord;

void main() {
    vTexCoord = TexCoord;
    gl_Position = u_projView * vec4(Position, 0.0, 1.0);
}
```

```
uniform sampler2D tex0;

varying vec2 vTexCoord;

void main() {
    vec4 color = texture2D(tex0, vTexCoord);
    gl_FragColor = color;
}
```
* GLSL 300
```
#version 330

uniform mat4 projTrans;

layout(location = 0) in vec2 Position;
layout(location = 1) in vec2 TexCoord;

out vec2 vTexCoord;

void main() {
    vTexCoord = TexCoord;
    gl_Position = u_projView * vec4(Position, 0, 1);
}
```
```
#version 330
uniform sampler2D tex0;

in vec2 vTexCoord;

//use your own output instead of gl_FragColor 
out vec4 fragColor;

void main() {
    //'texture' instead of 'texture2D'
    fragColor = texture(tex0, vTexCoord);
}
```

* 三角形
* 着色器
* 纹理
* 变换
* 坐标系统
* 摄像机
