package gui;

import Managers.DataManager;
import Managers.Renderer;
import Managers.Settings;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.system.MemoryStack;
import renderer.Font;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.system.MemoryStack.stackPush;

public class Text {

    private int w, h;

    private String text;
    private Font font;

    //Whhite color on default
    private Vector4f Color;
    private FloatBuffer position;
    private FloatBuffer uvs;

    public Text(String text, Font font)
    {
        this.text = text;
        this.font = font;
        Color = new Vector4f(1f);

        //6 vertices per quad with two floats

        position = BufferUtils.createFloatBuffer(12 * this.text.length());
        uvs = BufferUtils.createFloatBuffer(12 * this.text.length());

//        position = MemoryUtil.memAllocFloat(12 * this.text.length());
//        uvs = MemoryUtil.memAllocFloat(12 * this.text.length());

        setText(this.text);
    }

    public Text(String text, Font font, Vector4f Color)
    {
        this.text = text;
        this.font = font;
        this.Color = Color;

        //6 vertices per quad with two floats
        position = BufferUtils.createFloatBuffer(12 * this.text.length());
        uvs = BufferUtils.createFloatBuffer(12 * this.text.length());

        setText(this.text);
    }

    public Text(String text, Font font, Vector2f size, Vector2f pos)
    {
        this.text = text;
        this.font = font;

        position = BufferUtils.createFloatBuffer(12 * this.text.length());
        uvs = BufferUtils.createFloatBuffer(12 * this.text.length());

        setText(this.text);
        ScaleAt(size.x, size.y);
        TranslateTo(pos.x, pos.y);

    }

    public void setText(String text)
    {

        //TODO Reseting the TEXT value

        //If new text length is different from previous, realloc the buffers
        if(this.text.length() != text.length())
        {
//            MemoryUtil.memFree(position);
//            MemoryUtil.memFree(uvs);
//
//            position = MemoryUtil.memAllocFloat(6 * 2 * this.text.length());
//            uvs = MemoryUtil.memAllocFloat(6 * 2 * this.text.length());
            position = BufferUtils.createFloatBuffer(12 * this.text.length());
            uvs = BufferUtils.createFloatBuffer(12 * this.text.length());
        }

        this.text = text;


        //Using a quad to extract char rectangle from bitmap
        STBTTAlignedQuad quad = STBTTAlignedQuad.create();


        try (MemoryStack stack = stackPush()) {
            FloatBuffer x = stack.callocFloat(1);
            FloatBuffer y = stack.callocFloat(1);


            h = 0;
            for (int i = 0; i < text.length(); ++i) {
                font.GetChar(quad, text.charAt(i), x, y);


                if(h < (-1 * quad.y0()))
                    h = (int) (-1 * quad.y0());

                //Default position is on center-right of the screen
                position.put(quad.x0() / Settings.getWidth()).put(-1 * quad.y1() / Settings.getHeigth());
                position.put(quad.x0() / Settings.getWidth()).put(-1 * quad.y0() / Settings.getHeigth());
                position.put(quad.x1() / Settings.getWidth()).put(-1 * quad.y0() / Settings.getHeigth());

                position.put(quad.x0() / Settings.getWidth()).put(-1 * quad.y1() / Settings.getHeigth());
                position.put(quad.x1() / Settings.getWidth()).put(-1 * quad.y1() / Settings.getHeigth());
                position.put(quad.x1() / Settings.getWidth()).put(-1 * quad.y0() / Settings.getHeigth());

                uvs.put(quad.s0()).put(quad.t1());
                uvs.put(quad.s0()).put(quad.t0());
                uvs.put(quad.s1()).put(quad.t0());

                uvs.put(quad.s0()).put(quad.t1());
                uvs.put(quad.s1()).put(quad.t1());
                uvs.put(quad.s1()).put(quad.t0());

            }
             w = (int)x.get(0);
//            h = (int)y.get(0);
        }


        position.clear();
        uvs.clear();

        //Free the quad

        //Note: GC may try to dealloc, that will result a SEgFault
//        quad.free();
    }

    public void setColor(float r, float g, float b, float a)
    {
        Color.x = r;
        Color.y = g;
        Color.z = b;
        Color.w = a;
    }

    public void TranslateTo(float x, float y)
    {
        float halfW = (float) Settings.getWidth() / 2;
        float halfH = (float) Settings.getHeigth() / 2;

        float xt = position.get(0) - ((x - halfW) / halfW);
        float yt = position.get(1) - ((halfH - y) / halfH);

        for(int i = 0; i < position.limit(); i +=2)
        {
            position.put(i,position.get(i) - xt).put(i+1, position.get(i+1) - yt);
        }

    }
    public void ScaleAt(float x, float y)
    {
        for(int i = 0; i < position.limit(); i +=2)
        {
            position.put(i,position.get(i) * x).put(i+1, position.get(i+1) * y);
//            System.out.println(position.get(i) * x);
        }
    }

    public int TextBoxW()
    {
        return w;
    }
    public int TextBoxH()
    {
        return h;
    }

//    public void Clear()
//    {
//        MemoryUtil.memFree(position);
//        MemoryUtil.memFree(uvs);
//    }

    public void render()
    {
        render(Color);
    }

    public void render(Vector4f Color)
    {
        font.Bind(0);
//        Renderer.Instance().addBufferInfo(vertex, 6 * text.length());
        Renderer.Instance().ShapeByBuffer(position, uvs, Color, 6 * text.length());
    }

    public String getAsociatedString()
    {
        return text;
    }

}
