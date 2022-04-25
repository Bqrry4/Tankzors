package Managers;

import auxiliar.Direction;
import auxiliar.TextureRegion;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;
import renderer.Shader;
import renderer.Texture;
import renderer.TextureMap;

import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

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


/*        //position attribute
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
        //texture coord attribute
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);*/



        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);


        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        vertices = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, vertices.capacity(), vertices);

    }

    public void Clear()
    {
        glUnmapBuffer(GL_ARRAY_BUFFER);
        vertices.clear();
        MemoryUtil.memFree(vertices);
    }


/*
    public void Draw(TextureMap atlas, int RegionID, Vector4f srcRect, Vector4f destRect)
    {
        //Transforming SDL-like coordinates in normalized one

        */
/* Calculate Texture coordinates *//*

        TextureRegion region = atlas.getRegion(RegionID);

        float s1 = (srcRect.x + region.x()) / atlas.Width();
        float t1 =  (srcRect.y + region.y()) / atlas.Height();
        float s2 = (srcRect.x + srcRect.z + region.x()) / atlas.Width();
        float t2 = (srcRect.y + srcRect.w + region.y()) / atlas.Height();


        float halfW = (float) Settings.getWidth() / 2;
        float halfH = (float) Settings.getHeigth() / 2;
        //Calculate Vertex positions
        float x1 = (destRect.x - halfW) / halfW;
        float y1 = (halfH - destRect.y - destRect.w)  / halfH;
        float x2 = (destRect.x + destRect.z - halfW) / halfW;
        float y2 = (halfH - destRect.y) / halfH;

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
*/

    public void Draw(Texture texture, Vector4f srcRect, Vector4f destRect)
    {
        Draw(texture, srcRect, destRect, Direction.Up);
    }




    //Rotation, Up(by 0 degree), Left(+90d), Right(-90d), Down(fliping)
    public void Draw(Texture texture, Vector4f srcRect, Vector4f destRect, Direction rotation)
    {
        //Transforming SDL-like coordinates in normalized one

        //If srcRect is null it takes the entire texture
        float s1 = 0f, t1 = 0f, s2 = 1f, t2 = 1f;
        if(srcRect != null)
        {
            // Calculate Texture coordinates
            s1 = (srcRect.x) / texture.Width();
            t1 =  (srcRect.y) / texture.Height();
            s2 = (srcRect.x + srcRect.z) / texture.Width();
            t2 = (srcRect.y + srcRect.w) / texture.Height();
        }

        //Rotation part
        Vector2f v1, v2, v3, v4;

        switch (rotation)
        {
            case Left:
                v1 = new Vector2f(s1, t1);
                v2 = new Vector2f(s2, t1);
                v3 = new Vector2f(s2, t2);
                v4 = new Vector2f(s1, t2);
                break;
            case Right:
                v1 = new Vector2f(s2, t2);
                v2 = new Vector2f(s1, t2);
                v3 = new Vector2f(s1, t1);
                v4 = new Vector2f(s2, t1);
                break;
            case Down:
                v1 = new Vector2f(s1, t1);
                v2 = new Vector2f(s1, t2);
                v3 = new Vector2f(s2, t2);
                v4 = new Vector2f(s2, t1);
                break;
            case Up:
            default:
                v1 = new Vector2f(s1, t2);
                v2 = new Vector2f(s1, t1);
                v3 = new Vector2f(s2, t1);
                v4 = new Vector2f(s2, t2);
                break;
        }


        //If destRect is null it takes the entire screen
        float x1 = -1f, y1 = -1f, x2 = 1f, y2 = 1f;
        if(destRect != null)
        {
            float halfW = (float) Settings.getWidth() / 2;
            float halfH = (float) Settings.getHeigth() / 2;

            //Calculate Vertex positions
            x1 = (destRect.x - halfW) / halfW;
            y1 = (halfH - destRect.y - destRect.w)  / halfH;
            x2 = (destRect.x + destRect.z - halfW) / halfW;
            y2 = (halfH - destRect.y) / halfH;
        }


        //Put data into buffer
        vertices.putFloat(x1).putFloat(y1).putFloat(v1.x).putFloat(v1.y).putFloat(texture.GetOrderID());
        vertices.putFloat(x1).putFloat(y2).putFloat(v2.x).putFloat(v2.y).putFloat(texture.GetOrderID());
        vertices.putFloat(x2).putFloat(y2).putFloat(v3.x).putFloat(v3.y).putFloat(texture.GetOrderID());

        vertices.putFloat(x1).putFloat(y1).putFloat(v1.x).putFloat(v1.y).putFloat(texture.GetOrderID());
        vertices.putFloat(x2).putFloat(y1).putFloat(v4.x).putFloat(v4.y).putFloat(texture.GetOrderID());
        vertices.putFloat(x2).putFloat(y2).putFloat(v3.x).putFloat(v3.y).putFloat(texture.GetOrderID());

        //Increment with number of added vertices
        numVertices += 6;
    }


    public void Present(Shader shader, int bufferFormat) //Present the buffer to OpenGL
    {
        glUnmapBuffer(GL_ARRAY_BUFFER);

        if (numVertices > 0) {
            vertices.flip();
            shader.use();

            glBindVertexArray(vao);

            switch (bufferFormat)
            {
                case 0:
                    //position attribute
                    glVertexAttribPointer(0, 2, GL_FLOAT, false, 5 * Float.BYTES, 0);
                    glEnableVertexAttribArray(0);
                    //texture coord attribute
                    glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * Float.BYTES, 2 * Float.BYTES);
                    glEnableVertexAttribArray(1);
                    //TexId
                    glVertexAttribPointer(2, 1, GL_FLOAT, false, 5 * Float.BYTES, 4 * Float.BYTES);
                    glEnableVertexAttribArray(2);
                    break;

                case 1:
                    //position attribute
                    glVertexAttribPointer(0, 2, GL_FLOAT, false,8 * Float.BYTES, 0);
                    glEnableVertexAttribArray(0);
                    //texture coord attribute
                    glVertexAttribPointer(1, 2, GL_FLOAT, false, 8 * Float.BYTES, 2 * Float.BYTES);
                    glEnableVertexAttribArray(1);
                    //Color attribute
                    glVertexAttribPointer(2, 4, GL_FLOAT, false, 8 * Float.BYTES, 4 * Float.BYTES);
                    glEnableVertexAttribArray(2);
            }

            glDrawArrays(GL_TRIANGLES, 0, numVertices); //Triangle strips maybe

        }

        vertices.clear();

        numVertices = 0;
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        vertices = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, vertices.capacity(), vertices);

    }


    public void addBufferInfo(ByteBuffer buffer, int cantity)
    {
        vertices.put(buffer);
        numVertices += cantity;
    }

    public void ShapeByBuffer(FloatBuffer position, FloatBuffer uvs, Vector4f color,  int cantity)
    {
        for(int i = 0; i < cantity * 2; i += 2 )
        {
            vertices.putFloat(position.get(i)).putFloat(position.get(i+1)).putFloat(uvs.get(i)).putFloat(uvs.get(i+1)).putFloat(color.x).putFloat(color.y).putFloat(color.z).putFloat(color.w);
        }
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
