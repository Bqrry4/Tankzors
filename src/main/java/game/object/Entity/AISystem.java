package game.object.Entity;

import auxiliar.Direction;
import game.InputHandler;
import game.object.Entity.tank.AStar;
import game.object.Entity.tank.Tank;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class AISystem {

//    List<Tank> tanks = new ArrayList<Tank>();
    AStar pf;


    public AISystem()
    {
        int[][] map2 = {
                {217,217,218,217,217,218,217,218,218,217,218,218,218,217,218,217},
                {218,0,0,0,0,0,0,0,0,218,217,217,217,218,217,217},
                {217,0,0,0,0,0,0,0,0,0,0,0,0,0,218,218},
                {217,0,0,0,0,0,0,0,0,0,0,0,0,0,217,217},
                {217,0,0,0,0,0,0,0,0,0,0,0,0,0,217,218},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,217,217},
                {217,0,0,0,0,0,0,0,0,0,0,0,0,224,217,217},
                {225,224,225,225,225,226,226,0,0,0,0,0,225,226,225,225},
                {0,0,0,0,0,0,0,0,0,0,0,0,225,232,234,217},
                {0,0,0,0,0,0,0,0,0,0,0,225,226,0,0,218},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,218},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,218},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,218},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,218},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,217},
                {218,217,217,218,217,217,217,0,0,0,0,0,0,0,0,218},
                {218,0,0,0,0,0,217,0,0,0,0,0,0,0,0,217},
                {218,0,0,0,0,0,217,0,0,0,0,0,218,218,218,217},
                {218,0,0,0,0,0,217,0,0,0,0,0,0,0,0,217},
                {218,217,217,217,217,217,218,218,218,217,218,217,217,218,218,217}
        };
        pf = new AStar(map2);
    }

    Tank stalked;
    Tank stalker;

    public void addStalker(Tank t)
    {
        stalker = t;
    }

    public void addStalked(Tank t)
    {
        stalked = t;
    }

/*
    public void addComponent(Tank t)
    {
        tanks.add(t);
    }
*/

    List<Vector2i> l = null;

    public void ProcessCommend()
    {

        Vector2i cure = new Vector2i((int) (stalker.getHitbox().x/20 + 1), (int) (stalker.getHitbox().y/20 + 1));

//        System.out.println("" + cure.x + cure.y);

//        tanks.get(0).MoveBy(20, Direction.Down);
        if(InputHandler.keyState(GLFW_KEY_SPACE))
        {
            l = pf.solve(new Vector2i((int) (stalker.getHitbox().x/20 + 1), (int) (stalker.getHitbox().y/20 + 1)), new Vector2i((int) (stalked.getHitbox().x/20 + 1), (int) (stalked.getHitbox().y/20 + 1)));
        }



       if (l != null && !l.isEmpty()) {

//           l.remove(0);
           if (stalker.Idle()) {
               System.out.println("" + l.get(0).x + l.get(0).y);
               Vector2i got = l.remove(0);
               if (cure.x - got.x > 0)
                   stalker.MoveBy(20, Direction.Left);
               if (cure.x - got.x < 0)
                   stalker.MoveBy(20, Direction.Right);

               if (cure.y - got.y > 0)
                   stalker.MoveBy(20, Direction.Up);
               if (cure.y - got.y < 0)
                   stalker.MoveBy(20, Direction.Down);
           }
       }
       else
       {
       }

    }




}
