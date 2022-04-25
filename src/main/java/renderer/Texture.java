package renderer;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL15.*;

public final class Texture {

    static int instances = 0;
    final int orderID;

    private int texId;
    private int width;
    private int height;

    public Texture() {
        orderID = instances++;
    }

    public void Generate(ByteBuffer data, int width, int height, int channels)
    {
        this.width = width;
        this.height = height;

        texId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, texId);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);


        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_BASE_LEVEL, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, 0);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);


        if(channels == 3)
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB8, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, data);
        else if (channels == 4)
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);

    }

    public int Width()
    {
        return width;
    }
    public int Height()
    {
        return height;
    }

    public int GetOrderID()
    {
        return orderID;
    }

    public void Bind(int slot)
    {
        glActiveTexture(GL_TEXTURE0 + slot);
        glBindTexture(GL_TEXTURE_2D, texId);
    }

    //Make it deprecated later
    public void BindByOrder()
    {
        glActiveTexture(GL_TEXTURE0 + orderID);
        glBindTexture(GL_TEXTURE_2D, texId);
    }


}
