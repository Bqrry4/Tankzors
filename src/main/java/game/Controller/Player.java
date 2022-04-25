package game.Controller;

import auxiliar.Direction;
import game.InputHandler;
import game.object.Entity.tank.Tank;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;

public class Player {

    //Mainly target is the tank
    Tank tank;

    public Player(Tank tank)
    {
        this.tank = tank;
    }


    public void update()
    {
        ProcessCommend();
    }

    private void ProcessCommend()
    {
        //Recieving input for desired later actions
        if(InputHandler.keyState(GLFW_KEY_UP))
        {
            tank.MoveBy(20, Direction.Up);
            return;
        }

        if(InputHandler.keyState(GLFW_KEY_LEFT))
        {
            tank.MoveBy(20, Direction.Left);
            return;
        }

        if(InputHandler.keyState(GLFW_KEY_DOWN))
        {
            tank.MoveBy(20, Direction.Down);
            return;
        }


        if(InputHandler.keyState(GLFW_KEY_RIGHT))
        {
            tank.MoveBy(20, Direction.Right);
            return;
        }

        if(InputHandler.keyState(GLFW_KEY_SPACE))
        {
            tank.WantToAtack(true);
        }
    }



}
