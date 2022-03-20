package renderer;

import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private final int shader;

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

    public Shader(String path)
    {

        String vertex = "", fragment = "";
        try {
            String source = Files.readString(Path.of(path));

            //parse the source after #type keywords
            String[] SplitSource = source.split("#type [a-zA-Z]+",3);

            int id = source.indexOf("#type") + 6;
            int eol = source.indexOf("\r\n", id);
            String FirstPattern = source.substring(id, eol);

            id = source.indexOf("#type", eol) + 6;
            eol = source.indexOf("\r\n", id);
            String SecondPattern = source.substring(id, eol);


            if(FirstPattern.equals("vertex"))
            {
                vertex = SplitSource[1];
            }
            else if(FirstPattern.equals("fragment"))
            {
                fragment = SplitSource[1];
            }
            else throw new IOException("Unexpected token type from " +path);

            if(SecondPattern.equals("vertex"))
            {
                vertex = SplitSource[2];
            }
            else if(SecondPattern.equals("fragment"))
            {
                fragment = SplitSource[2];
            }
            else throw new IOException("Unexpected token type from " +path);

        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());

        }

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


    public void SetUniform1f(String Identifier, int value)
    {
        glUniform1i(glGetUniformLocation(shader, Identifier), value);
    }


}
