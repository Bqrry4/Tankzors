package game.object.Entity.tank;

import Managers.Renderer;
import auxiliar.Direction;
import auxiliar.TextureRegion;
import game.WindowTimer;
import game.level.Scene;
import game.object.GameObject;
import game.object.Munition.MunitionFactory;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;

public class Tank extends GameObject {

    //A reference to scene that it belongs
    Scene scene;

    Texture tex;
    TextureRegion region;
    final int frames = 16;
    final float frameQuad;

    int fractionID;

    HealthBar hb;

    Field fb;

    float Speed = 50;

    private Direction direction;
    float frame = 0;

    //Parameters for object transition
    private Vector2f targetPosition;
    private Direction desired;
    float toFrame = 0;

    boolean rotatingFlag = false;
    boolean movingFlag = false;
    boolean collideFlag = false; //Has collision on the active direction


    //Atacking part
    boolean CanAtack = false;
    int TresHold = 30;
    double CountDown = 0;

    public Tank(Scene scene, Texture tex, TextureRegion RegionID, int x, int y, int w, int h, Direction direction, HealthBar hb, Field field)
    {
        this.scene = scene;

        this.tex = tex;
        this.region = RegionID;

        hitbox = new Vector4f(x, y, w, h);
        targetPosition = new Vector2f(x, y);
        this.direction = direction;
        this.desired = this.direction;

        frameQuad = (float) region.w() / frames;

        this.hb = hb;
        this.fb = field;
    }

    @Override
    public void update()
    {
        Movement();
    }

    @Override
    public void render() {
        fb.render(hitbox);
        Renderer.Instance().Draw(tex ,new Vector4f(region.x() + (frameQuad * (int)frame), region.y(), frameQuad, region.h()), new Vector4f(hitbox.x*2 , hitbox.y*2, frameQuad*2, region.h()*2));
        hb.render(new Vector2f(hitbox.x, hitbox.y - 1));
    }

    private void Movement()
    {

        //If in animation
        float sign = Math.signum(toFrame - frame);
        if(sign != 0)
        {
            frame += sign * 20 * WindowTimer.Instance().GetDt();

            if(Math.signum(toFrame - frame) != sign )
            {
                frame = toFrame;
                direction = desired;
                rotatingFlag = false;
            }
            if(frame > 15)
                frame = 0;
            if(frame < 0)
                frame = 15;
            return;
        }


        //desired direction - current direcion
        int sg = desired.value - direction.value;
        if(sg != 0)
        {
            toFrame = sg * 4 + frame;
            return;
        }

        //Move to target position
        sign = Math.signum(targetPosition.x - hitbox.x);
        if(sign != 0)
        {
            movingFlag = true;
            hitbox.x += sign * Speed * WindowTimer.Instance().GetDt();

            //If passed over, fix to target
            if(Math.signum(targetPosition.x - hitbox.x) != sign )
            {
                hitbox.x = targetPosition.x;
                movingFlag = false;
            }
            return;
        }

        //Same for y axis
        sign = Math.signum(targetPosition.y - hitbox.y);
        if(sign != 0)
        {
            movingFlag = true;
            hitbox.y += sign * Speed * WindowTimer.Instance().GetDt();

            if(Math.signum(targetPosition.y - hitbox.y) != sign )
            {
                hitbox.y = targetPosition.y;
                movingFlag = false;
            }
            return;
        }
    }

    public void SetCollideFlag(boolean value)
    {
        collideFlag = value;
    }

    public boolean IsColliding()
    {
        return collideFlag;
    }

    public Direction Direction()
    {
        return direction;
    }

    public void MoveBy(float d, Direction direction)
    {
        if(this.direction == direction)
        {
            if(!movingFlag && !collideFlag && !rotatingFlag) {
                switch (direction) {
                    case Up:
                        targetPosition.y -= d;
                        break;
                    case Down:
                        targetPosition.y += d;
                        break;
                    case Left:
                        targetPosition.x -= d;
                        break;
                    case Right:
                        targetPosition.x += d;
                        break;
                }
            }
        }
        else
        {
            if(!movingFlag && !rotatingFlag)
            {
                desired = direction;
                rotatingFlag = true;
            }
        }
    }

    public void WantToAtack(boolean value)
    {
/*        if(CountDown < TresHold)
        {
            CountDown += WindowTimer.Instance().GetDt();
        }
        else
        {

        }*/

        //
        scene.addObject(MunitionFactory.spawn());


        CanAtack = value;
    }

    public boolean Idle()
    {
        return !rotatingFlag && !movingFlag;
    }

    public boolean CanAtack()
    {
        return CanAtack;
    }

}
