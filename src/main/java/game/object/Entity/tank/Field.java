package game.object.Entity.tank;

import Managers.Renderer;
import auxiliar.TextureRegion;
import org.joml.Vector4f;
import renderer.Texture;

public class Field {

    //5 types
    int type = 0; //0,123

    //Shield Points
    final int MaxSP = 3;
    int SP = 0;

    TextureRegion region;

    final int frames;
    final float frameQuad;

    Texture tex;

    public Field(int type, TextureRegion region, int frames, Texture tex)
    {
        this.type = type;

        this.region = region;

        this.frames = frames;
        frameQuad = (float) region.w() / frames;

        this.tex = tex;

    }

    public void render(Vector4f position)
    {
        int state;
        if(SP <= 0)
        {
            state = type;
        }
        else
        {
            if(MaxSP == SP)
            {
                state = 5;
            }
            else
            {
                state = 4;
            }
        }


        Renderer.Instance().Draw(tex, new Vector4f(region.x() + frameQuad * state, region.y(), frameQuad, region.h()), new Vector4f((position.x + position.z/2 - frameQuad/2)*2 , (position.y + position.w/2 - (float) region.h()/2)*2, frameQuad*2, region.h()*2));
    }



}
