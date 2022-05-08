package game.object.Entity;

import auxiliar.PathFinding.PathFinder;
import game.Controller.Entity;
import game.Controller.EntityMediator;
import auxiliar.PathFinding.AStar;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AISystem implements EntityMediator {

    PathFinder pf;

    List<Entity> entities = new LinkedList<>();

    //Add aswell goals, for enemy Unities

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
        pf = new AStar(map2, new Vector2i(20));
    }


    public void addComponent(Entity entity)
    {
        entities.add(entity);
    }

/*
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
*/


/*    public void ProcessCommend()
    {

        Vector2i cure = new Vector2i((int) (stalker.getHitbox().x/20 + 1), (int) (stalker.getHitbox().y/20 + 1));

//        System.out.println("" + cure.x + cure.y);

//        tanks.get(0).MoveBy(20, Direction.Down);
        if(InputHandler.keyState(GLFW_KEY_SPACE))
        {
            l = pf.FindPath(new Vector2i((int) (stalker.getHitbox().x/20 + 1), (int) (stalker.getHitbox().y/20 + 1)), new Vector2i((int) (stalked.getHitbox().x/20 + 1), (int) (stalked.getHitbox().y/20 + 1)));
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

    }*/

    public void Process()
    {
        //Using iterator for removing while iterating through
        Iterator<Entity> iterator = entities.iterator();

        while(iterator.hasNext())
        {
            Entity entity = iterator.next();
            if(entity.OutOfScope())
                iterator.remove();

            entity.Process();
        }

    }

    @Override
    public List<Vector2i> LeadMe(Entity ent) {

        //calculate to what position

        int fID = ent.GetFractionID();

        Entity target = null;

        for(Entity entity : entities)
        {
            if(fID != entity.GetFractionID())
            {
                target = entity;
            }
        }

        Vector2f dst = (target != null) ? target.GetTargetPosition() : null;

        return pf.FindPath(ent.GetTargetPosition(), dst);
    }
}
