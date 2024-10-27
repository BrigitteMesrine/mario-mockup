#type vertex
#version 330 core

// attributes
layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;

// go out to fragment shader
out vec4 fColor;

void main() {
    fColor = aColor;
    // giving GL the position that we passed in
    gl_Position = vec4(aPos, 1.0);
}

#type fragment
#version 330 core

// gets fColor from previous "out" statement
in vec4 fColor;

out vec4 color;

void main() {
    color = fColor;
}
