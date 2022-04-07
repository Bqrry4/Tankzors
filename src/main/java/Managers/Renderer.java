package Managers;

import auxiliar.TextureRegion;
import gui.Text;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;
import renderer.Shader;
import renderer.Texture;
import renderer.TextureMap;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;


//I hate this code

public class Renderer
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


/*    private final List<Shader> shaderList = new ArrayList<Shader>();
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
    }*/


    private final int bufferSize = 16384 * Float.BYTES; //~65kB
    private ByteBuffer vertices;
    private int numVertices = 0;

    private int vbo, vao; //Vertex buffer object ID


    public void Init()
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


        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        vertices = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, vertices.capacity(), vertices);

    }

    public void Clear()
    {
        vertices.clear();
        MemoryUtil.memFree(vertices);
    }
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

        numVertices = 0;

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        vertices = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, vertices.capacity(), vertices);
    }

    public void endBatching(Shader shader) {
        glUnmapBuffer(GL_ARRAY_BUFFER);

        if (numVertices > 0) {
            vertices.flip();
            shader.use();
/*            shader.SetUniform(transformUniform, mx);*/

            glBindVertexArray(vao);

            glDrawArrays(GL_TRIANGLES, 0, numVertices); //Triangle strips maybe

        }

        vertices.clear(); //Maybe not nedded
    }

    public void DrawBatching(Texture texture, Shader shader, Vector4f srcRect, Vector4f destRect)
    {
        //Transforming SDL-like coordinates in normalized one

        /* Calculate Texture coordinates */
        float s1 = srcRect.x / texture.Width();
        float t1 =  srcRect.y / texture.Height();
        float s2 = (srcRect.x + srcRect.z) / texture.Width();
        float t2 = (srcRect.y + srcRect.w) / texture.Height();

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

        //* Increment with number of added vertices
        numVertices += 6;

        shader.SetUniform(textureUniform, 0);
        texture.Bind(0);

    }


    public void useShader()
    {

    }

    public void UseTexture()
    {

    }


    public void Draw(TextureMap atlas, int RegionID, Vector4f srcRect, Vector4f destRect)
    {
        //Transforming SDL-like coordinates in normalized one

        /* Calculate Texture coordinates */
        TextureRegion region = atlas.getRegion(RegionID);

        float s1 = (srcRect.x + region.x()) / atlas.Width();
        float t1 =  (srcRect.y + region.y()) / atlas.Height();
        float s2 = (srcRect.x + srcRect.z + region.x()) / atlas.Width();
        float t2 = (srcRect.y + srcRect.w + region.y()) / atlas.Height();

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

        //* Increment with number of added vertices
        numVertices += 6;
    }

    public void Draw(Texture texture, Vector4f srcRect, Vector4f destRect)
    {
        //Transforming SDL-like coordinates in normalized one

        // Calculate Texture coordinates
        float s1 = 0f, t1 = 0f, s2 = 1f, t2 = 1f;
        if(srcRect != null)
        {
            s1 = (srcRect.x) / texture.Width();
            t1 =  (srcRect.y) / texture.Height();
            s2 = (srcRect.x + srcRect.z) / texture.Width();
            t2 = (srcRect.y + srcRect.w) / texture.Height();
        }


        //Calculate Vertex positions
        float x1 = -1f, y1 = -1f, x2 = 1f, y2 = 1f;
        if(destRect != null)
        {
            x1 = destRect.x / Settings.getWidth() - 1f;
            y1 = (destRect.y + destRect.w) *-1  / Settings.getHeigth() + 1f;
            x2 = (destRect.x + destRect.z) / Settings.getWidth() - 1f;
            y2 = (destRect.y)* -1 / Settings.getHeigth() + 1f;
        }


        //Put data into buffer
        vertices.putFloat(x1).putFloat(y1).putFloat(s1).putFloat(t2);
        vertices.putFloat(x1).putFloat(y2).putFloat(s1).putFloat(t1);
        vertices.putFloat(x2).putFloat(y2).putFloat(s2).putFloat(t1);

        vertices.putFloat(x1).putFloat(y1).putFloat(s1).putFloat(t2);
        vertices.putFloat(x2).putFloat(y1).putFloat(s2).putFloat(t2);
        vertices.putFloat(x2).putFloat(y2).putFloat(s2).putFloat(t1);

        //Increment with number of added vertices
        numVertices += 6;
    }

    public void Present(Shader shader) //Present the buffer to OpenGL
    {
        glUnmapBuffer(GL_ARRAY_BUFFER);

        if (numVertices > 0) {
            vertices.flip();
            shader.use();

            glBindVertexArray(vao);

            glDrawArrays(GL_TRIANGLES, 0, numVertices); //Triangle strips maybe

        }

        vertices.clear();

        numVertices = 0;
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        vertices = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, vertices.capacity(), vertices);

    }


/*    public void DrawText(Text text)
    {

    }*/

    public void addBufferInfo(ByteBuffer buffer, int cantity)
    {
        vertices.put(buffer);
        numVertices += cantity;
    }

/*
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
*/

}
