package renderer;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Stack;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Texture {
    private final int texId;
    private final int width, height, channels;

    public Texture(String path) {

        texId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, texId);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_BASE_LEVEL, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, 0);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);


        ByteBuffer image;
        try (MemoryStack stack = stackPush()) {
            IntBuffer _width = stack.callocInt(1);
            IntBuffer _height = stack.callocInt(1);
            IntBuffer _channels = stack.callocInt(1);

            image = stbi_load(path, _width, _height, _channels, 0);
            if(image == null)
            {
                throw new RuntimeException("Error at loading image " +path);
            }

            width = _width.get();
            height = _height.get();
            channels = _channels.get();
        }


        if(channels == 3)
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB8, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, image);
        else if (channels == 4)
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        else
            throw new RuntimeException("Unknown channel format for image" +path + stbi_failure_reason());


        stbi_image_free(image);
    }



    public int Width()
    {
        return width;
    }
    public int Height()
    {
        return height;
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
