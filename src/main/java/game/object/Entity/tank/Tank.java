package game.object.Entity.tank;

import Managers.Renderer;
import Managers.Settings;
import auxiliar.Direction;
import auxiliar.TextureRegion;
import game.WindowTimer;
import game.level.Scene;
import game.object.GameObject;
import game.object.Munition.Munition;
import game.object.Munition.MunitionFactory;
import game.object.ObjectMediator;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;

public class Tank extends GameObject {

    ObjectMediator mediator;

    //Render part
    private Texture tex;
    private TextureRegion region;
    private final float frameQuad;
    private float frame = 0;

    //Components
    private int fractionID;
    private HealthBar hb;
    private Field fb;

    //Movement part
    private float Speed = 50;

    //Parameters for object transition
    private Vector2f targetPosition;
    private Direction desired;
    private float toFrame = 0;

    //State flags
    private boolean rotatingFlag = false;
    private boolean movingFlag = false;
    private boolean collideFlag = false; //Has collision on the active direction


    //Atacking part
    private boolean CanAtack = false;
    private double TresHold = 0.6f;
    private double CountDown = TresHold;

    boolean AtIntention = false;

    //
    private String shellType;

    public Tank(ObjectMediator mediator, Texture tex, TextureRegion RegionID, int x, int y, int w, int h, Direction direction, int fractionID, HealthBar hb, Field field, String shellType)
    {
        this.mediator = mediator;

        this.ObjectType = 1;
//        this.scene = scene;

        this.tex = tex;
        this.region = RegionID;

        hitbox = new Vector4f(x, y, w, h);
        targetPosition = new Vector2f(x, y);
        this.direction = direction;
        this.desired = this.direction;

        frameQuad = (float) region.w() / region.frames();

        this.fractionID = fractionID;
        this.hb = hb;
        this.fb = field;
        this.shellType = shellType;

        //Notifing mediator about current position
        mediator.notifyDesired(this, new Vector2f(hitbox.x + hitbox.z/2, hitbox.y + hitbox.w/2));
    }

    @Override
    public void update()
    {
        CountDowns();
        Movement();
        fb.update();
    }

    @Override
    public void render() {
        fb.render(hitbox);
        Renderer.Instance().Draw(tex ,new Vector4f(region.x() + (frameQuad * (int)frame), region.y(), frameQuad, region.h()), new Vector4f(hitbox.x* Settings.ScaleRatio(), hitbox.y* Settings.ScaleRatio(), frameQuad* Settings.ScaleRatio(), region.h()* Settings.ScaleRatio()));
        hb.render(new Vector2f(hitbox.x, hitbox.y - 1));
    }

    private void Movement()
    {
        //If in animation
        float deltaframe = toFrame - frame;
        float sign = Math.signum(deltaframe);
        if(sign != 0)
        {
            //Change frame increment direction flag
            float chdir = (Math.abs(deltaframe) > region.frames()/2) ? (-1) : (1);

            frame += chdir * sign * 20 * WindowTimer.Instance().GetDt();

            if(chdir != -1)
            {
                if(Math.signum(toFrame - frame) != sign)
                {
                    frame = toFrame;
                    direction = desired;
                    rotatingFlag = false;
                }
            }
            else
            {
                if(frame < 0)
                    frame = region.frames();
                else
                if(frame > region.frames()-1)
                    frame = -1;
            }
            return;
        }

        //Rotation
        //desired direction - current direcion
        int sg = desired.value - direction.value;
        if(sg != 0)
        {
            //how much to go + where is it now
            toFrame = sg * (region.frames()/4) + (int)frame;
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

            //Notifing mediator about current position
            mediator.notifyCurrent(this);

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

            //Notifing mediator about current position
            mediator.notifyCurrent(this);
            return;
        }
    }

    private void CountDowns()
    {
        //Atack CountDown
        if(CountDown < TresHold)
        {
            CountDown += WindowTimer.Instance().GetDt();
        }
        else
        {
            CanAtack = true;
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

    public void MoveBy(float d, Direction direction) {
        if (this.direction != direction) {
            if (Idle()) {
                desired = direction;
                //Rotate to direction
                rotatingFlag = true;
            }
        } else {
            if (!collideFlag && Idle()) {
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
                if(!mediator.notifyDesired(this, new Vector2f(targetPosition.x + hitbox.z/2, targetPosition.y + hitbox.w/2)))
                {
                    targetPosition.x = hitbox.x;
                    targetPosition.y = hitbox.y;
                }
            }
        }
    }


    public void WantToAtack()
    {
        if(!rotatingFlag && CanAtack)
        {
            MunitionFactory.SpawnShell(shellType, direction, new Vector2f(hitbox.x, hitbox.y), fractionID);
            CanAtack = false;
            //Reset countdown
            CountDown = 0f;
        }
    }


    public boolean Idle()
    {
        return !rotatingFlag && !movingFlag;
    }

    public boolean CanAtack()
    {
        return CanAtack;
    }

    public void AtackIntention(boolean value)
    {
        AtIntention = value;
    }

    public boolean IntentToAtack()
    {
        return AtIntention;
    }

    public void RecieveDamage(int dmg)
    {
        if(fb.getSP() > 0)
        {
            fb.Absorb(dmg);
        }
        else
        {
            hb.TakeDamage(dmg);
            if(hb.HealthPoints() <= 0)
            {
                Existence = false;
                MunitionFactory.SpawnExplosion(2, new Vector2f(hitbox.x + hitbox.z/2, hitbox.y + hitbox.w/2));
            }
        }
    }

    public int getFractionID()
    {
        return fractionID;
    }

}
