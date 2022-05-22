package game.level;

import Managers.Renderer;
import Managers.ResourceManager;
import game.Controller.Entity;
import game.level.layer.InteractionLayer;
import game.level.layer.Layer;
import game.object.GameObject;
import game.object.ObjectMediator;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Scene implements IScene {

    //Size of the Scene ////NEED A REWORK
    private final Vector2i size;
    //List of Objects
    List<GameObject> objectList;
    ObjectMediator mediator;
    //List of Layers
    List<Layer> layerList;


    public Scene()
    {
        //Just initializing
        size = new Vector2i(1280, 720);

        objectList = new LinkedList<>();
        layerList = new ArrayList<>();
    }

    public void Load()
    {


    }

    public void Process()
    {

        Interaction();

        Update();

        Render();
    }

    public void UpdateSize(int w, int h)
    {
        size.x = w;
        size.y = h;
    }

    //Checking for interaction between objects
    private void Interaction()
    {
        //Object with object
//       Shellul ar trebui sa creeze explozii, exploziile de pe levvel dau damage//NU

    }

    private void Update()
    {

        //update objects state
/*        for(GameObject object : objectList)
        {
            object.update();
        }*/

        //Using iterator for removing while iterating through
        Iterator<GameObject> iterator = objectList.iterator();

        while(iterator.hasNext())
        {
            GameObject object = iterator.next();
            if(!object.ShouldExist())
                iterator.remove();

            object.update();
        }


    }

    private void Render()
    {

        for(Layer layer : layerList)
        {
            layer.render();
        }

        for(GameObject object : objectList)
        {
            object.render();
        }


        Renderer.Instance().Present(ResourceManager.Instance().GetShader("default"), 0);
    }

    @Override
    public void addObject(GameObject obj)
    {
        objectList.add(obj);
    }

    public void addObjectMediator(ObjectMediator med)
    {
        mediator = med;
    }



    @Override
    public void addLayer(Layer lay)
    {
        layerList.add(lay);
    }

    @Override
    public ObjectMediator getMediator() {
        return mediator;
    }
}
