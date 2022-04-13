package game.level;

import Managers.Renderer;
import Managers.ResourceManager;
import game.layer.TileLayer;
import game.object.Entity.Player;
import game.object.Entity.tank.Field;
import game.object.Entity.tank.HealthBar;
import game.object.Entity.tank.Tank;
import renderer.TextureMap;
import auxiliar.TextureRegion;
import game.layer.Layer;
import auxiliar.Direction;
import game.object.GameObjects;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    //Size of the Scene
    private final Vector2i size = new Vector2i(100);
    //List of Objects
    List<GameObjects> objectList = new ArrayList<GameObjects>();
    //List of Layers
    List<Layer> layerList = new ArrayList<Layer>();

    Player player;

    public Scene()
    {

    }

    public void Load()
    {

        TextureMap textureMap = new TextureMap();
        textureMap.setTexture(ResourceManager.Instance().GetTexture("tanks"));

        TextureRegion[] Indices = {
            new TextureRegion(0, 0, 384, 24),
                new TextureRegion(0, 24, 384, 24),
                new TextureRegion(0, 48, 384, 24),
                new TextureRegion(0, 72, 384, 24),
                new TextureRegion(0, 96, 384, 28),
                new TextureRegion(0, 122, 168, 28),
                new TextureRegion(0, 150, 288, 4)


        };
        textureMap.setIndices(Indices);


        player = new Player(new Tank(textureMap, 0, 18, 18, 24, 24, Direction.Down, new HealthBar(textureMap.getRegion(6) ,12, textureMap), new Field(0, textureMap.getRegion(5), 6, textureMap)));
//        objectList.add(new Player2(textureMap,0, 18, 18, 24, 24, Direction.Down));


        int[][] map = new int[][] {
                {1,2,2,3,1,5,4,6,3,3,4,4,2,3,1,4},
                {5,5,4,1,1,1,5,6,5,6,3,2,1,2,1,5},
                {5,2,1,2,1,5,3,2,6,6,5,4,2,2,6,2},
                {4,4,6,4,6,3,3,6,6,5,5,5,1,3,1,6},
                {6,6,5,3,4,2,1,1,1,6,6,6,5,6,5,2},
                {4,6,6,2,6,5,3,3,2,4,5,6,3,5,3,6},
                {1,5,3,3,6,3,5,4,1,3,5,3,4,4,4,4},
                {3,2,3,1,1,6,1,1,1,1,6,3,2,3,1,3},
                {4,2,1,4,4,6,4,4,1,3,6,1,5,4,2,2},
                {6,6,1,3,5,6,2,5,2,3,5,4,4,6,2,4},
                {2,3,3,2,3,5,4,3,2,2,3,5,4,5,3,6},
                {4,1,4,6,5,2,5,4,3,5,1,1,4,5,2,3},
                {4,1,2,2,5,3,2,2,6,5,1,1,5,5,1,5},
                {1,6,6,4,3,2,6,3,3,4,2,2,3,1,2,3},
                {5,1,1,1,6,6,5,6,5,3,3,4,2,2,3,1},
                {3,3,1,5,1,4,4,6,6,6,5,1,1,1,2,2},
                {2,5,4,2,5,1,6,2,2,5,5,6,4,6,5,5},
                {1,4,5,5,6,4,1,2,1,1,1,6,5,6,5,3},
                {1,6,4,1,2,4,6,3,2,6,5,4,2,4,2,6},
                {3,3,3,2,3,6,3,1,5,1,3,2,1,3,3,2},
                {6,3,4,5,3,4,6,1,2,3,2,3,1,5,6,6},
                {2,3,5,2,4,5,5,6,2,1,5,6,1,1,3,4}

        };
        layerList.add(new TileLayer(ResourceManager.Instance().GetTexture("ground"), 20, 20, map));
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
        layerList.add(new TileLayer(ResourceManager.Instance().GetTexture("walls"), 20, 20, map2));
    }

    public void Process()
    {
        Update();

        Render();
    }

    public void UpdateSize(int w, int h)
    {
        size.x = w;
        size.y = h;
    }


    private void Update()
    {
        //update objects state
        for(GameObjects object : objectList)
        {
            object.update();
        }
        player.update();

        //Collision
//        Collision.detect((Player2) objectList.get(0), (TileLayer) layerList.get(1));
//        Collision.detect((Player2) objectList.get(0), size.x, size.y );

    }

    private void Render()
    {
        //then render them
        //update layers and objects

        ResourceManager.Instance().GetTexture("tanks").Bind(0);

        for(Layer layer : layerList)
        {
            layer.render();
        }
        Renderer.Instance().Present(ResourceManager.Instance().GetShader("default"), 0);

        ResourceManager.Instance().GetTexture("tanks").Bind(0);

        for(GameObjects object : objectList)
        {
            object.render();
        }
        player.render();

        Renderer.Instance().Present(ResourceManager.Instance().GetShader("default"), 0);
    }
}
