#type vertex
#version 330 core
layout (location = 0) in vec2 Position;
layout (location = 1) in vec2 texCoord;
layout (location = 2) in float TexID;

uniform mat4 projection;
uniform mat4 transform;

out vec2 outTexCoord;
out float oTexID;

void main()
{
    gl_Position = vec4(Position, 0.0, 1.0);
    outTexCoord = texCoord;
    oTexID = TexID;
}

#type fragment
#version 330 core

in vec2 outTexCoord;
in float oTexID;
out vec4 FragColor;

uniform sampler2D u_texture[8];

void main()
{
    int index = int (oTexID);
    FragColor = texture(u_texture[index], outTexCoord);
}