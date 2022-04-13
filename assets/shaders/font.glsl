#type vertex
#version 330 core
layout (location = 0) in vec2 Position;
layout (location = 1) in vec2 texCoord;
layout (location = 2) in vec4 color;

uniform mat4 projectionMatrix;
uniform mat4 transform;

out vec2 outTexCoord;
out vec4 outColor;

void main()
{
    gl_Position = vec4(Position, 0.0, 1.0);
    outTexCoord = texCoord;
    outColor = color;
}

#type fragment
#version 330 core

in vec2 outTexCoord;
in vec4 outColor;
out vec4 FragColor;

uniform sampler2D u_texture;

void main()
{
    vec4 tex = texture(u_texture, outTexCoord);
    FragColor = vec4(tex.r, tex.r, tex.r, tex.r) * outColor;
}