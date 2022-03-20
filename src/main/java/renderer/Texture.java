package renderer;

import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {
    private final int texId;
    private final IntBuffer width, height, channels;

    public Texture(String path) {

        texId = glGenTextures();
        System.out.println(texId);
        glBindTexture(GL_TEXTURE_2D, texId);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_BASE_LEVEL, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, 0);

/*        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);*/

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);


        width = BufferUtils.createIntBuffer(1);
        height = BufferUtils.createIntBuffer(1);
        channels = BufferUtils.createIntBuffer(1);


        ByteBuffer image = stbi_load(path, width, height, channels, 0);

        if(image == null)
        {
            throw new RuntimeException("Error at loading image " +path);
        }
        if(channels.get(0) == 3)
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB8, width.get(), height.get(), 0, GL_RGB, GL_UNSIGNED_BYTE, image);
        else if (channels.get(0) == 4)
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        else
            throw new RuntimeException("Unknown channel format for image" +path + stbi_failure_reason());


        stbi_image_free(image);


    }



    public int Width()
    {
        return width.get(0);
    }
    public int Height()
    {
        return height.get(0);
    }

    public int GetID()
    {
        return texId;
    }

    public void Bind(int slot)
    {
        glActiveTexture(GL_TEXTURE0 + slot);
        glBindTexture(GL_TEXTURE_2D, texId);
    }

}
