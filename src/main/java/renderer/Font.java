package renderer;

import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTPackedchar;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.GL_GENERATE_MIPMAP_HINT;
import static org.lwjgl.opengl.GL30.GL_R8;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBTruetype.stbtt_GetPackedQuad;

public class Font {

    int bitmapID;
    int bitmapW, bitmapH;

    STBTTPackedchar.Buffer charinfo;

    public Font(){}

    public void Generate(STBTTPackedchar.Buffer charinfo, ByteBuffer data, int w, int h)
    {
        this.bitmapW = w;
        this.bitmapH = h;
        this.charinfo = charinfo;

        bitmapID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, bitmapID);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_R8, bitmapW, bitmapH, 0, GL_RED, GL_UNSIGNED_BYTE, data);

        glHint(GL_GENERATE_MIPMAP_HINT, GL_NICEST);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    public void Bind(int slot)
    {
        glActiveTexture(GL_TEXTURE0 + slot);
        glBindTexture(GL_TEXTURE_2D, bitmapID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST_MIPMAP_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAX_ANISOTROPY_EXT, 8);
    }


    public void GetChar(STBTTAlignedQuad quad, char character, FloatBuffer x, FloatBuffer y)
    {
            stbtt_GetPackedQuad(charinfo, bitmapW, bitmapH, character - 32, x, y, quad, true);
    }

    public void Clear()
    {
        charinfo.free();
    }

    public void Use()
    {
        glBindTexture(GL_TEXTURE_2D, bitmapID);
    }

}
