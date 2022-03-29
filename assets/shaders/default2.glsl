#type vertex
#version 330 core
layout (location = 0) in vec2 Position;

uniform mat4 projectionMatrix;
uniform mat4 transform;

void main()
{
    gl_Position = transform * vec4(Position, 0.0, 1.0);
}

#type fragment
#version 330 core

out vec4 FragColor;

uniform sampler2D u_texture;
uniform vec2 TexCoord;

void main()
{
    FragColor = texture(u_texture, TexCoord);
}