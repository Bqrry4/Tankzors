package game.object;

import Managers.Renderer;
import auxiliar.Direction;
import game.InputHandler;
import game.WindowTimer;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends GameObjects{

    private Vector2f targetPosition;

    private final Matrix4f mx = new Matrix4f();

    boolean collideFlag = false; //Have collision on the active direction


    Direction desired = Direction.Down;

    int[] animState = {
            0, 4, 8, 12
    };

    int framePerState = 4;
    int framesCount = 16;

    float frame = 0;
    float toFrame = 0;

    int vt = 50;


    public Player(int x, int y, int w, int h, Direction direction)
    {
        hitbox = new Vector4f(x, y, w, h);
        targetPosition = new Vector2f(x, y);
        this.direction = direction;

    }
    @Override
    public void update() {
        Movement();
    }

    @Override
    public void render() {
/*        Renderer.Instance().DrawObject(0, 0, hitbox, mx);*/

        Renderer.Instance().beginBatching();
        Renderer.Instance().DrawBatching(1, new Vector4f(new float[]{hitbox.z * (int)frame , 0, 24, 24}), new Vector4f(new float[]{hitbox.x*4 , hitbox.y*4, 24*4, 24*4}));
        Renderer.Instance().endBatching(0, 0, mx);

/*        shader.SetUniform("transform", mx);

        shader.SetUniform("u_texture", 0);
        mesh.Bind();
        texture.Bind(0);
        mesh.render();*/
    }

    private void Movement()
    {
        float sign = Math.signum(toFrame - frame);
        if(sign != 0)
        {
            frame += sign * 20 * WindowTimer.Instance().GetDt();

            if(Math.signum(toFrame - frame) != sign )
            {
                frame = toFrame;
                direction = desired;
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
            toFrame = sg * 4;
        }

        //0 -> 1 = 1
        // 0 -> 3 = 3 -> -1

        //Move to target position
        sign = Math.signum(targetPosition.x - hitbox.x);
        if(sign != 0)
        {
            hitbox.x += sign * vt * WindowTimer.Instance().GetDt();

            //If passed over, fix to target
            if(Math.signum(targetPosition.x - hitbox.x) != sign )
            {
                hitbox.x = targetPosition.x;
            }
            return;
        }

        //Same for y axis
        sign = Math.signum(targetPosition.y - hitbox.y);
        if(sign != 0)
        {
            hitbox.y += sign * vt * WindowTimer.Instance().GetDt();

            if(Math.signum(targetPosition.y - hitbox.y) != sign )
            {
                hitbox.y = targetPosition.y;
            }
            return;
        }

        if(InputHandler.keyState(GLFW_KEY_UP))
        {
            if(direction == Direction.Up && !collideFlag)
            {
                targetPosition.y -= 20;
            }
            else
            {
                desired = Direction.Up;
            }
            return;
        }

        if(InputHandler.keyState(GLFW_KEY_DOWN))
        {
            if(direction == Direction.Down && !collideFlag)
            {
                targetPosition.y += 20;
            }
            else
            {
                desired = Direction.Down;
            }
            return;
        }

        if(InputHandler.keyState(GLFW_KEY_LEFT))
        {
            if(direction == Direction.Left && !collideFlag)
            {
                targetPosition.x -= 20;
            }
            else
            {
                desired = Direction.Left;
            }
            return;
        }

        if(InputHandler.keyState(GLFW_KEY_RIGHT))
        {
            if(direction == Direction.Right && !collideFlag)
            {
                targetPosition.x += 20;
            }
            else
            {
                desired = Direction.Right;
            }
        }
    }




    public void SetCollideFlag(boolean value)
    {
        collideFlag = value;
    }
}
