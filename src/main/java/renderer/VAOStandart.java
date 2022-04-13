package renderer;

import Managers.Renderer;
import Managers.Settings;
import org.joml.Vector4i;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class VAOStandart {

    //Singleton part
    private static VAOStandart instance = new VAOStandart();
    private VAOStandart(){};
    public static VAOStandart Instance() {
        return instance;
    }

    int defVao, fontVao;

    public void Init()
    {
        InitDefaultVao();
        InitFontVao();
    }

    private void InitDefaultVao()
    {
        defVao = glGenVertexArrays();
        glBindVertexArray(defVao);

        //position attribute
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
        //texture coord attribute
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glBindVertexArray(0);

    }

    private void InitFontVao()
    {

    }

    public int GetDefVao()
    {
        return defVao;
    }
}











