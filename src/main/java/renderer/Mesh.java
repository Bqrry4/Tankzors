package renderer;

import Managers.Settings;
import org.joml.Vector4i;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {
    private int vao, vbo = 0;

    private final Vector4i size = new Vector4i();

    public Mesh(int x, int y, int w, int h)
    {
        size.x = x;
        size.y = y;
        size.w = w;
        size.z = h;

/*        float[] vertices = new float[]{
                //x   y   tx  ty
                -1f,  1f, 0f, 0f,
                -1f, -1f, 0f, 1f,
                 1f, -1f, 1f, 1f,
                 1f,  1f, 1f, 0f
        };*/

        float _x = (float)x / Settings.getWidth();
        float _y = (float)y / Settings.getHeigth();
        float _w = (float)w / Settings.getWidth();
        float _h = (float)h / Settings.getHeigth();

        float[] vertices = new float[]{
                //x   y   tx  ty
                _x,  _y+_h, 0f, 0f,
                _x, _y, 0f, 1f,
                _x+_w, _y, 1f, 1f,
                _x+_w,  _y+_h, 1f, 0f
        };

        int[] indices = {
                0, 1, 3,   // first triangle
                3, 1, 2    // second triangle
        };

        vao = glGenVertexArrays();
        glBindVertexArray(vao);


        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        //position attribute
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
        //texture coord attribute
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);


        int ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);



        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

    }

/*    private static final Mesh instance = new Mesh();
    public static Mesh StandartMesh() {
        return instance;
    }*/
    //A standart mesh without any other bounds

    public Mesh()
    {
/*        float[] vertices = new float[]{
                -1f, 1f, 0f, 0f,
                -1f, -1f, 0f, 1f,
                1f, -1f, 1f, 1f,
                1f, 1f, 1f, 0f
        };
        int[] indices = {
                0, 1, 3,   // first triangle
                3, 1, 2    // second triangle
        };*/

        vao = glGenVertexArrays();
        glBindVertexArray(vao);


/*
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_DYNAMIC_DRAW);
*/


        //position attribute
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
        //texture coord attribute
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);

/*

        int ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_DYNAMIC_DRAW);


*/

/*        glBindBuffer(GL_ARRAY_BUFFER, 0);*/
        glBindVertexArray(0);

    }


   /* public Mesh(int a)
    {
        float[] vertices = new float[]{
                -1f, 1f, 0f, 0f,
                -1f, -1f, 0f, 1f,
                1f, -1f, 1f, 1f,
                1f, 1f, 1f, 0f
        };
        int[] indices = {
                0, 1, 3,   // first triangle
                3, 1, 2    // second triangle
        };

        vao = glGenVertexArrays();
        glBindVertexArray(vao);


        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_DYNAMIC_DRAW);


        //position attribute
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
        //texture coord attribute
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);


        int ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_DYNAMIC_DRAW);



        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

    }*/

    public void update()
    {
/*        ByteBuffer bbf = glMapBufferRange(GL_ARRAY_BUFFER, 0, 0, );*/
    }

    public void Bind()
    {
        glBindVertexArray(vao);
    }

    public static void Enable()
    {
        //position attribute
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
        //texture coord attribute
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);
    }

    public void render()
    {
        glBindVertexArray(vao);
        //drawing shape
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

    }
}














