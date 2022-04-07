package gui;

import Managers.Renderer;
import Managers.Settings;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import renderer.Font;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.system.MemoryStack.stackPush;

public class Text {

    String text;
    Font font;

    ByteBuffer vertex;

    public Text(String text, Font font)
    {
        this.text = text;
        this.font = font;

        vertex = MemoryUtil.memAlloc(6 * 4 * this.text.length() * Float.BYTES);
        setText(this.text);
    }

    public void setText(String text)
    {
        //If new text length is different from previous, realloc the buffer
        if(this.text.length() != text.length())
        {
            MemoryUtil.memFree(vertex);
            vertex = MemoryUtil.memAlloc(6 * 4 * this.text.length() * Float.BYTES);
        }

        //Using a quad to extract char rectangle from bitmap
        STBTTAlignedQuad quad = STBTTAlignedQuad.create();

        try (MemoryStack stack = stackPush()) {
            FloatBuffer x = stack.callocFloat(1);
            FloatBuffer y = stack.callocFloat(1);

            for (int i = 0; i < text.length(); ++i) {
                font.GetChar(quad, text.charAt(i), x, y);
                System.out.println("" + quad.x0() + " " + quad.x1() + " " + quad.y0() + " " + quad.y1() + " " + quad.t0() + " " + quad.t1() + " " + quad.s0() + " " + quad.s1());

                vertex.putFloat(quad.x0() / Settings.getWidth() - 1f).putFloat( -1 * quad.y1() / Settings.getHeigth()).putFloat(quad.s0()).putFloat(quad.t1());
                vertex.putFloat(quad.x0() / Settings.getWidth() - 1f).putFloat( -1 * quad.y0() / Settings.getHeigth()).putFloat(quad.s0()).putFloat(quad.t0());
                vertex.putFloat(quad.x1() / Settings.getWidth() - 1f).putFloat(-1 * quad.y0() / Settings.getHeigth()).putFloat(quad.s1()).putFloat(quad.t0());

                vertex.putFloat(quad.x0() / Settings.getWidth() - 1f).putFloat(-1 * quad.y1() / Settings.getHeigth()).putFloat(quad.s0()).putFloat(quad.t1());
                vertex.putFloat(quad.x1() / Settings.getWidth() - 1f).putFloat(-1 * quad.y1() / Settings.getHeigth()).putFloat(quad.s1()).putFloat(quad.t1());
                vertex.putFloat(quad.x1() / Settings.getWidth() - 1f).putFloat(-1 * quad.y0() / Settings.getHeigth()).putFloat(quad.s1()).putFloat(quad.t0());
            }
        }

        //Reset buffer pointer
        vertex.flip();

        //Free the quad
        quad.free();
    }

    public void Clear()
    {
        MemoryUtil.memFree(vertex);
    }

    public void render()
    {

        font.Bind(0);
        Renderer.Instance().addBufferInfo(vertex, 6 * text.length()  );
    }

}
