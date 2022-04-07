#type vertex
#version 330 core
layout (location = 0) in vec2 Position;
layout (location = 1) in vec2 texCoord;

uniform mat4 projection;
uniform mat4 transform;

out vec2 outTexCoord;

void main()
{
    gl_Position = vec4(Position, 0.0, 1.0);
    outTexCoord = texCoord;
}

#type fragment
#version 330 core

in  vec2 outTexCoord;
out vec4 FragColor;

uniform sampler2D u_texture;

void main()
{
    FragColor = texture(u_texture, outTexCoord);
}