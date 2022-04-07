package renderer;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glShaderSource;

public class Shader {
    private int shader;

/*    String vertexs = "#type vertex\n" +
            "#version 330 core\n" +
            "layout (location=0) in vec3 aPos;\n" +
            "layout (location=1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}\n";
    String fragments = "#type fragment\n" +
            "#version 330 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    color = fColor;\n" +
            "}";*/

    public Shader()
    {}

    public void Compile(String vertex, String fragment)
    {
//Compiling vertex shader
        int vs = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vs, vertex);
        glCompileShader(vs);
        if(glGetShaderi(vs, GL_COMPILE_STATUS) == GL_FALSE)
            throw new RuntimeException(glGetShaderInfoLog(vs));

        //Compiling fragment shader
        int fs = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fs, fragment);
        glCompileShader(fs);
        if(glGetShaderi(fs, GL_COMPILE_STATUS) == GL_FALSE)
            throw new RuntimeException(glGetShaderInfoLog(fs));

        //Linking shaders to a program
        shader = glCreateProgram();
        glAttachShader(shader, vs);
        glAttachShader(shader, fs);
        glLinkProgram(shader);
        if(glGetProgrami(shader, GL_LINK_STATUS) == GL_FALSE)
            throw new RuntimeException(glGetProgramInfoLog(shader));

        //Clear the shaders object
        glDetachShader(shader, vs);
        glDetachShader(shader, fs);
        glDeleteShader(vs);
        glDeleteShader(fs);
    }

    public void use()
    {
        glUseProgram(shader);
    }
    public void detach()
    {
        glUseProgram(0);
    }

    public int getShader()
    {
        return shader;
    }


    public void SetUniform(String Identifier, int value)
    {
        glUniform1i(glGetUniformLocation(shader, Identifier), value);
    }

    public void SetUniform(String Identifier, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(16);
            value.get(buffer);
            glUniformMatrix4fv(glGetUniformLocation(shader, Identifier), false, buffer);
        }
    }

}
