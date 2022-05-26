package game.object.Munition;

import auxiliar.Direction;
import game.level.IScene;
import game.object.GameObject;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.TextureMap;



public class MunitionFactory {

    static IScene Scene = null;

    static TextureMap textureMap = null;

    public static void SetScene(IScene scene)
    {
        Scene = scene;
    }

    public static void SetTextureMap(TextureMap texMap)
    {
        textureMap = texMap;
    }

    static public GameObject SpawnShell(String type, Direction direction, Vector2f position, int Fraction)
    {
        GameObject munition = null;
        if(type.equals("shell")) {
            //If direction is Up or Down
            Vector4f pos = new Vector4f(position, 6, 12);
            if(direction == Direction.Left || direction == Direction.Right) {
                pos.z = 12;
                pos.w = 6;
            }

            munition = new Shell(Scene.getMediator(), direction, pos, textureMap.getRegion(0), textureMap.getTexture(),0, Fraction, 1);
        }

        if(type.equals("plasma")) {
            //If direction is Up or Down
            Vector4f pos = new Vector4f(position, 6, 12);
            if(direction == Direction.Left || direction == Direction.Right) {
                pos.z = 12;
                pos.w = 6;
            }

            munition = new Shell(Scene.getMediator(), direction, pos, textureMap.getRegion(1), textureMap.getTexture(),1, Fraction, 2);
        }

        if(type.equals("armored")) {
            //If direction is Up or Down
            Vector4f pos = new Vector4f(position, 6, 12);
            if(direction == Direction.Left || direction == Direction.Right) {
                pos.z = 12;
                pos.w = 6;
            }
            munition = new Shell(Scene.getMediator(), direction, pos, textureMap.getRegion(3), textureMap.getTexture(),0, Fraction, 3);
        }
        Scene.addObject(munition);

        return null;
    }

    static public GameObject SpawnExplosion(int type, Vector2f position)
    {
        GameObject explosion = null;
        switch (type)
        {
            case 0:
                explosion = new Explosion(position, textureMap.getRegion(7), textureMap.getTexture());
                break;
            case 1:
                explosion = new Explosion(position, textureMap.getRegion(8), textureMap.getTexture());
                break;
            case 2:
                explosion = new Explosion(position, textureMap.getRegion(9), textureMap.getTexture());
                break;
        }
        Scene.addToBuffer(explosion);

        return null;
    }

    static public GameObject spawn()
    {
//        return new Shell(Direction.Left, new Vector4f(100, 100, 24, 24), new TextureRegion(72, 0, 24, 24), shellTexture);
        return null;
    }


}
