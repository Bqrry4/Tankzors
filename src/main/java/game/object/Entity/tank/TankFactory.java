package game.object.Entity.tank;

import auxiliar.Direction;
import game.level.IScene;
import game.object.GameObject;
import org.joml.Vector2f;
import renderer.TextureMap;

public class TankFactory {

    static IScene Scene = null;

    static TextureMap textureMap = null;

    //Not the greatest idea, but...
    public static void SetScene(IScene scene)
    {
        Scene = scene;
    }

    public static void SetTextureMap(TextureMap texMap)
    {
        textureMap = texMap;
    }

    static public GameObject SpawnTank(String type, Direction direction, Vector2f position, int Fraction, int HP, int SP, String shelltype)
    {
        //If scene is not set, ignore request
        if(Scene == null) return null;

        //Constructing HealthBar component
        HealthBar healthBar = null;
        switch (Fraction)
        {
            case 0:
            case 1:
                healthBar = new HealthBar(textureMap.getRegion(6), textureMap.getTexture(), HP);
                break;
            case 2:
                healthBar = new HealthBar(textureMap.getRegion(7), textureMap.getTexture(), HP);
                break;
            case 3:
                healthBar = new HealthBar(textureMap.getRegion(8), textureMap.getTexture(), HP);
                break;
        }

        //Constructing field component
        Field field = new Field(Fraction, textureMap.getRegion(5), textureMap.getTexture(), SP);


        //Construct the main object
        Tank tank = null;
        if(type.equals("light")) {
            tank =  new Tank(Scene.getMediator(),textureMap.getTexture(), textureMap.getRegion(0), (int) position.x, (int) position.y, 24, 24, direction, Fraction, healthBar, field, shelltype);
        }
        if(type.equals("heavy")) {
            tank =  new Tank(Scene.getMediator(),textureMap.getTexture(), textureMap.getRegion(1), (int) position.x, (int) position.y, 24, 24, direction, Fraction, healthBar, field, shelltype);
        }
        if(type.equals("siege")) {
            tank =  new Tank(Scene.getMediator(),textureMap.getTexture(), textureMap.getRegion(2), (int) position.x, (int) position.y, 24, 24, direction, Fraction, healthBar, field, shelltype);
        }
        if(type.equals("kamikaze")) {
            tank =  new Tank(Scene.getMediator(),textureMap.getTexture(), textureMap.getRegion(3), (int) position.x, (int) position.y, 24, 24, direction, Fraction, healthBar, field, shelltype);
        }
        if(type.equals("turret")) {
            tank =  new Tank(Scene.getMediator(),textureMap.getTexture(), textureMap.getRegion(4), (int) position.x, (int) position.y, 24, 24, direction, Fraction, healthBar, field, shelltype);
        }


        Scene.addObject(tank);
        return tank;
    }
}
