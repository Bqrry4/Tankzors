package game.object.Entity.tank;

import Managers.Renderer;
import Managers.Settings;
import auxiliar.TextureRegion;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;
import renderer.TextureMap;

public class HealthBar {

    private final int MaxHP;
    private int HP;

    private TextureRegion region;

    private final float frameQuad;

    private Texture tex;


    public HealthBar(TextureRegion region, Texture tex, int HP)
    {
        this.region = region;

        frameQuad = (float) region.w() / region.frames();

        this.tex = tex;

        this.MaxHP = HP;
        this.HP = HP;
    }


    public void render(Vector2f position)
    {
        float ratio = (float) HP/MaxHP;
        Renderer.Instance().Draw(tex, new Vector4f(region.x() + (frameQuad * (int)(ratio * (region.frames()-1)) ), region.y(), frameQuad, region.h()), new Vector4f(position.x* Settings.ScaleRatio(), position.y* Settings.ScaleRatio(), frameQuad* Settings.ScaleRatio(), region.h()* Settings.ScaleRatio()));
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
