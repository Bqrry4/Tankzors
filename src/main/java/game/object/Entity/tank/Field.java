package game.object.Entity.tank;

import Managers.Renderer;
import Managers.Settings;
import auxiliar.TextureRegion;
import game.WindowTimer;
import org.joml.Vector4f;
import renderer.Texture;

public class Field {

    //5 types
    private int type = 0; //0,123

    //Shield Points
    private final int MaxSP;
    private float SP;

    private TextureRegion region;
    private final float frameQuad;
    private Texture tex;

    public Field(int type, TextureRegion region, Texture tex, int SP)
    {
        this.type = type;

        this.region = region;

        frameQuad = (float) region.w() / region.frames();

        this.tex = tex;

        this.MaxSP = SP;
        this.SP = SP;

    }

    public void update()
    {
        if(SP < MaxSP)
            SP += (float) (1 * WindowTimer.Instance().GetDt());
    }

    public void render(Vector4f position)
    {
        int state;
        if((int)SP <= 0)
        {
            state = type;
        }
        else
        {
            if(MaxSP == (int)SP)
            {
                state = 5;
            }
            else
            {
                state = 4;
            }
        }


        Renderer.Instance().Draw(tex, new Vector4f(region.x() + frameQuad * state, region.y(), frameQuad, region.h()), new Vector4f((position.x + position.z/2 - frameQuad/2)* Settings.ScaleRatio(), (position.y + position.w/2 - (float) region.h()/2)* Settings.ScaleRatio(), frameQuad* Settings.ScaleRatio(), region.h()* Settings.ScaleRatio()));
    }

    public int getSP()
    {
        return (int) SP;
    }

    public void Absorb(int dmg)
    {
        SP -= dmg;
        if(SP < 0)
            SP = 0;
    }

}
