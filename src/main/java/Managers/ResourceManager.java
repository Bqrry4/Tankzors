package Managers;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTTPackContext;
import org.lwjgl.stb.STBTTPackedchar;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import renderer.Font;
import renderer.Shader;
import renderer.Texture;
import renderer.TextureMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryStack.stackPush;


public class ResourceManager {
    //This class in a Singleton
    private static ResourceManager instance = new ResourceManager();
    private ResourceManager(){};
    public static ResourceManager Instance() {
        return instance;
    }


    HashMap<String, Shader> Shaders = new HashMap<String, Shader>();
    HashMap<String, Texture> Textures = new HashMap<String, Texture>();
    HashMap<String, TextureMap> TextureAtlases = new HashMap<String, TextureMap>();
    HashMap<String, Font> Fonts = new HashMap<String, Font>();

    public Shader LoadShader(String path, String name)
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

        Shader shader = new Shader();
        shader.Compile(vertex, fragment);

        Shaders.put(name, shader);

        return shader;
    }

    public Texture LoadTexture(String path, String name)
    {
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

            //Only rgb or rgba images
            if(_channels.get(0) != 3 && _channels.get(0) != 4)
            {
                throw new RuntimeException("Unknown channel format for image" +path + stbi_failure_reason());
            }

            Texture texture = new Texture();
            texture.Generate(image, _width.get(0), _height.get(0), _channels.get(0));

            Textures.put(name, texture);

            //Freeing the unnecesary buffer
            stbi_image_free(image);

            return texture;
        }
    }

    public Font LoadFont(String path, String name)
    {
        ByteBuffer fontData;

        try{
            //Reading ttf file into a byte buffer
            byte[] buffer = Files.readAllBytes(Path.of(path));
            fontData = MemoryUtil.memAlloc(buffer.length).put(buffer).flip();
        }
        catch (IOException s)
        {
            throw new RuntimeException("Error at loading font " +path);
        }

        try (MemoryStack stack = stackPush()) {

            //Allocating packing context
            STBTTPackContext context = STBTTPackContext.create();

            //Allocating a bitmap buffer image, 1024^2 power of 2
            ByteBuffer pixels = MemoryUtil.memAlloc(1024 * 1024);

            //Allocating an arrat of 94 char rectangle region for bitmap
            STBTTPackedchar.Buffer charinfo = STBTTPackedchar.create(94);

            if(!stbtt_PackBegin(context, pixels,  1024, 1024, 0, 4))
            {
                throw new RuntimeException("Error at loading font " +path);
            }

            //Should increase the quality a bit...
            stbtt_PackSetOversampling(context, 2, 2);

            //Packing 94 chars from ' '\32 to '~'\126 of 72 standart size
            stbtt_PackFontRange(context, fontData, 0, 72, 32, charinfo);

            stbtt_PackEnd(context);


            //Buffer was filled in the packing process, flipping is needed to restore pointer
            pixels.flip();

            Font font = new Font();
            font.Generate(charinfo ,pixels, 1024, 1024);

            Fonts.put(name, font);

            //Free unnecesary buffers
            MemoryUtil.memFree(pixels);
            MemoryUtil.memFree(fontData);

            return font;
        }
    }

    public Shader GetShader(String name)
    {
        return Shaders.get(name);
    }

    public Texture GetTexture(String name)
    {
        return Textures.get(name);
    }

    public Font GetFont(String name)
    {
        return Fonts.get(name);
    }

    public void Free()
    {


        for(Font font : Fonts.values())
        {
            font.Clear();
        }
    }

}
