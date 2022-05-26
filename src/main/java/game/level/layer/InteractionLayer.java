package game.level.layer;

import game.object.Entity.tank.Tank;
import game.object.GameObject;
import game.object.Munition.Shell;
import game.object.ObjectMediator;
import org.joml.Vector2f;

import java.util.List;
import java.util.Map;

import static Managers.Collision.checkCollision;

public class InteractionLayer implements ObjectMediator {

    int tileW;
    int tileH;

    GameObject[][]  Map;

    int[][] phMap;

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

        if(phMap[c][r] != 0)
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

        if(!obj.ShouldExist()) return;

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
                if ((r+1) < Map[0].length && Map[c][r+1] == obj)
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

            //First is tank
            if(obj.GetObjectType() == 1)
            {
                Tank tank = (Tank)obj;
                switch (obj.Direction())
                {
                    case Right:
                        for(int i = 1; i < 3; ++i)
                        {
                            if((r+i) < Map[0].length && Map[c][r+i] != null)
                            {
                                Tank tank2 = (Tank)Map[c][r+i];
                                if(tank.getFractionID() != tank2.getFractionID())
                                    tank.AtackIntention(true);
                            }
                        }
                        break;
                    case Left:
                        for(int i = 1; i < 3; ++i)
                        {
                            if((r - i) >= 0 && Map[c][r-i] != null)
                            {
                                Tank tank2 = (Tank)Map[c][r-i];
                                if(tank.getFractionID() != tank2.getFractionID())
                                    tank.AtackIntention(true);
                            }
                        }
                        break;
                    case Down:
                        for(int i = 1; i < 3; ++i)
                        {
                            if((c+i) < Map.length && Map[c+i][r] != null)
                            {
                                Tank tank2 = (Tank)Map[c+i][r];
                                if(tank.getFractionID() != tank2.getFractionID())
                                    tank.AtackIntention(true);
                            }
                        }
                        break;
                    case Up:
                        for(int i = 1; i < 3; ++i)
                        {
                            if((c-i) >= 0 && Map[c-i][r] != null)
                            {
                                Tank tank2 = (Tank)Map[c-i][r];
                                if(tank.getFractionID() != tank2.getFractionID())
                                    tank.AtackIntention(true);
                            }
                        }
                        break;
                }
            }

            //First is shell
            if(obj.GetObjectType() == 2)
            {
                //Second is a tank
                if(Map[c][r].GetObjectType() == 1)
                {
                    Tank tank = (Tank) Map[c][r];
                    Shell shell = (Shell) obj;

                    if(tank.getFractionID() != shell.getFractionID() && checkCollision(Map[c][r].getHitbox(), obj.getHitbox()))
                    {
                        tank.RecieveDamage(shell.DoDamage());
                        shell.Destroy();

                        if(!tank.ShouldExist())
                        {

                            //Clear the possible position of the dead obj
                            switch (Map[c][r].Direction())
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
                                    if ((r+1) < Map[0].length && Map[c][r+1] == obj)
                                        Map[c][r+1] = null;
                                    break;
                                case Right:
                                    if (r > 0 && Map[c][r-1] == obj)
                                        Map[c][r-1] = null;
                                    break;
                            }
                            Map[c][r] = null;

                        }
                    }
                }
            }


        }

    }

    public void addPhysicalMap(int[][] m)
    {
        phMap = m;
    }

}
