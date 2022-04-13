package game.object.Entity.tank;

import Managers.Renderer;
import auxiliar.TextureRegion;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.TextureMap;

public class HealthBar {

    int type = 0;

    final int MaxHP = 100;
    int HP = 53;

    TextureRegion region;

    final int frames;
    final float frameQuad;

    TextureMap tex;


    public HealthBar(TextureRegion region, int frames, TextureMap tex)
    {
        this.region = region;

        this.frames = frames;
        frameQuad = (float) region.w() / frames;

        this.tex = tex;

    }


    public void render(Vector2f position)
    {
        float ratio = (float) HP/MaxHP;
        Renderer.Instance().Draw(tex, new Vector4f(region.x() + (frameQuad * (int)(ratio * (frames-1)) ), region.y(), frameQuad, region.h()), new Vector4f(position.x*4 , position.y*4, frameQuad*4, region.h()*4));
    }

}
