package game.object.Entity.tank;

import Managers.Renderer;
import auxiliar.TextureRegion;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;
import renderer.TextureMap;

public class HealthBar {

    private final int MaxHP = 100;
    private int HP = 53;

    private TextureRegion region;

    private final float frameQuad;

    private Texture tex;


    public HealthBar(TextureRegion region, Texture tex)
    {
        this.region = region;

        frameQuad = (float) region.w() / region.frames();

        this.tex = tex;

    }


    public void render(Vector2f position)
    {
        float ratio = (float) HP/MaxHP;
        Renderer.Instance().Draw(tex, new Vector4f(region.x() + (frameQuad * (int)(ratio * (region.frames()-1)) ), region.y(), frameQuad, region.h()), new Vector4f(position.x*2 , position.y*2, frameQuad*2, region.h()*2));
    }


    public void TakeDamage(int dmg)
    {
        HP -= dmg;
    }

    public int HealthPoints()
    {
        return HP;
    }

}
