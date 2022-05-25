package game.level.layer;

import game.object.Entity.tank.Tank;
import game.object.GameObject;
import game.object.Munition.Shell;
import game.object.ObjectMediator;
import org.joml.Vector2f;

import java.util.List;

import static Managers.Collision.checkCollision;

public class InteractionLayer implements ObjectMediator {

    int tileW;
    int tileH;

    GameObject[][]  Map;

    Layer lay;

    public InteractionLayer(int tilew, int tileH, int r, int c)
    {
        Map = new GameObject[c][r];

        this.tileW = tilew;
        this.tileH = tileH;
    }



    @Override
    public boolean notifyDesired(GameObject obj, Vector2f position) {

        int r = (int) (position.x/tileW);
        int c = (int) (position.y/tileH);

        if(r >= Map[0].length || r < 0 || c < 0 ||  c >= Map.length)
            return false;

        if(Map[c][r] == null)
        {
            Map[c][r] = obj;
            return true;
        }
        return false;
    }


    @Override
    public void notifyCurrent(GameObject obj) {

        Vector2f position = obj.getPosition();

        int r = (int) (position.x/tileW);
        int c = (int) (position.y/tileH);

        //Check for out of map
        if(r >= Map[0].length || r < 0 || c < 0 ||  c >= Map.length)
        {
            obj.SetExistence(false);
            return;
        }


        //Clearing the previous position when reaching desired

        switch (obj.Direction())
        {
            case Up:
                if ((c+1) < Map.length && Map[c+1][r] == obj)
                    Map[c+1][r] = null;
                break;
            case Down:
                if (c > 0 && Map[c-1][r] == obj)
                    Map[c-1][r] = null;
                break;
            case Left:
                if ((c+1) < Map[0].length && Map[c][r+1] == obj)
                    Map[c][r+1] = null;
                break;
            case Right:
                if (r > 0 && Map[c][r-1] == obj)
                    Map[c][r-1] = null;
                break;
        }


        if(Map[c][r] != null)
        {
            //Check for interaction

            //First is tank
//            if(obj.GetObjectType() == 1)
//            {
//                if(obj == Map[c][r]) {
//                    return;
//                }
//                else
//                {
//                    System.out.println("No");
//                }
//                //Second is Tank
//                if(Map[c][r].GetObjectType() == 1) {
//                    if (checkCollision(Map[c][r].getHitbox(), obj.getHitbox())) {
//
//
//                        //Doing things
//                        Tank tank = (Tank) obj;
//                        tank.SetCollideFlag(true);
//
//                        //Check colision on direction
//                        if (obj != Map[c][r])
//                            System.out.println("It is");
//
//
//                    }
//                }
//
//                //Second is a shell
//                if(Map[c][r].GetObjectType() == 2)
//                {
//                    System.out.println("Hrere");
//                    if(checkCollision(Map[c][r].getHitbox(), obj.getHitbox()))
//                    {
//                        System.out.println("DSSD");
//                        Tank tank = (Tank) Map[c][r];
//                        Shell shell = (Shell) obj;
//
//                        tank.RecieveDamage(shell.DoDamage());
//                        shell.SetExistence(false);
//                    }
//
//                }
//            }

            //First is shell
            if(obj.GetObjectType() == 2)
            {
                //Second is a tank
                System.out.println((int)Map[c][r].GetObjectType());
                if(Map[c][r].GetObjectType() == 1)
                {
                    Tank tank = (Tank) Map[c][r];
                    Shell shell = (Shell) obj;

                    if(tank.getFractionID() != shell.getFractionID() && checkCollision(Map[c][r].getHitbox(), obj.getHitbox()))
                    {
                        tank.RecieveDamage(shell.DoDamage());
                        shell.SetExistence(false);

                        if(!tank.ShouldExist())
                        {
                            Map[c][r] = null;
                        }
                    }
                }
            }


        }
//        else {
//            Map[c][r] = obj;
//        }

            if(obj == null)
                System.out.println("Error there");


    }

    public void addPhysicalLayer(Layer l)
    {
        lay = l;
    }

}
