package Managers;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.joml.Vector4i;
import org.lwjgl.system.MemoryUtil;
import renderer.Mesh;
import renderer.Shader;
import renderer.Texture;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer //Probably Singleton
{
    //Singleton part
    private static Renderer instance = new Renderer();
    private Renderer(){};
    public static Renderer Instance() {
        return instance;
    }


    //A constant that contains the name of texture uniform in shader
    private final String textureUniform = "u_texture";
    private final String transformUniform = "transform";


    private final List<Shader> shaderList = new ArrayList<Shader>();
    private final List<Mesh> meshList = new ArrayList<Mesh>();
    private final List<Texture> textureList = new ArrayList<Texture>();

    public void addShader(Shader shader)
    {
        shaderList.add(shader);
    }
    public void addTexture(Texture tex)
    {
        textureList.add(tex);
    }
    public void addMesh(Mesh mesh)
    {
        meshList.add(mesh);
    }


    private final int bufferSize = 16384 * Float.BYTES; //~65kB
    private ByteBuffer vertices;
    private int numVertices = 0;
    private boolean isDrawing;

    private int vbo, vao; //Vertex buffer object ID

    public void InitBatch()
    {

        vao = glGenVertexArrays();
        glBindVertexArray(vao);


        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        vertices = MemoryUtil.memAlloc(bufferSize);
        long size = (long) vertices.capacity() * Float.BYTES;
        glBufferData(GL_ARRAY_BUFFER, size, GL_DYNAMIC_DRAW);


        //position attribute
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
        //texture coord attribute
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);



        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
        
    }
    public void beginBatching() {

        isDrawing = true;
        numVertices = 0;

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        vertices = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, vertices.capacity(), vertices);
    }

    public void endBatching(int MeshID, int ShaderID, Matrix4f mx) {
        glUnmapBuffer(GL_ARRAY_BUFFER);


        if (numVertices > 0) {
            vertices.flip();
            shaderList.get(0).use();
            shaderList.get(0).SetUniform(transformUniform, mx);

            glBindVertexArray(vao);

            glDrawArrays(GL_TRIANGLES, 0, numVertices);

        }

        vertices.clear(); //Maybe not nedded
        isDrawing = false;
    }

    public void DrawBatching(int texID, Vector4f srcRect, Vector4f destRect)
    {
        //Transforming SDL-like coordinates in normalized one

        /* Calculate Texture coordinates */
        float s1 = srcRect.x / textureList.get(texID).Width();
        float t1 =  srcRect.y / textureList.get(texID).Height();
        float s2 = (srcRect.x + srcRect.z) / textureList.get(texID).Width();
        float t2 = (srcRect.y + srcRect.w) / textureList.get(texID).Height();

         //Calculate Vertex positions
        float x1 = destRect.x / Settings.getWidth() - 1f;
        float y1 = (destRect.y + destRect.w) *-1  / Settings.getHeigth() + 1f;
        float x2 = (destRect.x + destRect.z) / Settings.getWidth() - 1f;
        float y2 = (destRect.y)* -1 / Settings.getHeigth() + 1f;




        //Put data into buffer
        vertices.putFloat(x1).putFloat(y1).putFloat(s1).putFloat(t2);
        vertices.putFloat(x1).putFloat(y2).putFloat(s1).putFloat(t1);
        vertices.putFloat(x2).putFloat(y2).putFloat(s2).putFloat(t1);

        vertices.putFloat(x1).putFloat(y1).putFloat(s1).putFloat(t2);
        vertices.putFloat(x2).putFloat(y1).putFloat(s2).putFloat(t2);
        vertices.putFloat(x2).putFloat(y2).putFloat(s2).putFloat(t1);

/*         //Put data into buffer
        vertices.putFloat(x1).putFloat(y1).putFloat(s1).putFloat(t1);
        vertices.putFloat(x1).putFloat(y2).putFloat(s1).putFloat(t2);
        vertices.putFloat(x2).putFloat(y2).putFloat(s2).putFloat(t2);

        vertices.putFloat(x1).putFloat(y1).putFloat(s1).putFloat(t1);
        vertices.putFloat(x2).putFloat(y2).putFloat(s2).putFloat(t2);
        vertices.putFloat(x2).putFloat(y1).putFloat(s2).putFloat(t1);*/

        //* Increment with number of added vertices
        numVertices += 6;

        shaderList.get(0).SetUniform(textureUniform, 0);
        textureList.get(texID).Bind(0);

    }

    //Will render to the current context of window buffer
    public void DrawObject(int shaderId, int textureId, int meshId, Matrix4f mx)
    {
        shaderList.get(shaderId).use();
        shaderList.get(shaderId).SetUniform(transformUniform, mx);
        shaderList.get(shaderId).SetUniform(textureUniform, 0);
        textureList.get(textureId).Bind(0);
        meshList.get(meshId).render();
    }

    public void DrawObject(int shaderId, int textureId, Mesh meshId, Matrix4f mx)
    {
        shaderList.get(shaderId).use();
        shaderList.get(shaderId).SetUniform(transformUniform, mx);
        shaderList.get(shaderId).SetUniform(textureUniform, 0);
        textureList.get(textureId).Bind(0);
        meshId.render();
    }

    public void DrawObject(int shaderId, int textureId, int meshId, Matrix4f mx, int frame)
    {
        shaderList.get(shaderId).use();
        shaderList.get(shaderId).SetUniform(transformUniform, mx);
        shaderList.get(shaderId).SetUniform(textureUniform, 0);
        textureList.get(textureId).Bind(0);
        meshList.get(meshId).render();
    }

    public void useShader(int shaderId)
    {
        shaderList.get(shaderId).use();
    }

    public void useTexture(int textureId)
    {

    }

    public void applyUniform()
    {

    }


}
