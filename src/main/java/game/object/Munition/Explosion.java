package game.object.Munition;

import Managers.Renderer;
import Managers.Settings;
import auxiliar.TextureRegion;
import game.WindowTimer;
import game.object.GameObject;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;

public class Explosion extends GameObject {

    TextureRegion region;
    Texture tex;

    int ASpeed = 20;

    float frame = 0;
    float frameQuad;

    public Explosion(Vector2f hitbox, TextureRegion region, Texture tex)
    {
        this.hitbox = new Vector4f(hitbox, 0, 0);

        this.region = region;
        this.tex = tex;

        frameQuad = (float) region.w() / region.frames();

    }

    @Override
    public void update() {

        frame += ASpeed * WindowTimer.Instance().GetDt();

        if(frame > region.frames())
            SetExistence(false);

    }

    @Override
    public void render() {

        Renderer.Instance().Draw(tex ,new Vector4f(region.x() + (frameQuad * (int)frame), region.y(), frameQuad, region.h()), new Vector4f((hitbox.x + hitbox.z/2 - frameQuad/2)* Settings.ScaleRatio(), (hitbox.y + hitbox.w/2 - (float) region.h()/2)* Settings.ScaleRatio(), frameQuad* Settings.ScaleRatio(), region.h()* Settings.ScaleRatio()));
    }
}
