package game.level;

import Managers.ResourceManager;
import auxiliar.Direction;
import auxiliar.TextureRegion;
import game.Controller.NPC;
import game.Controller.Player;
import game.level.layer.InteractionLayer;
import game.level.layer.TileLayer;
import game.object.Entity.AISystem;
import game.object.Entity.tank.Tank;
import game.object.Entity.tank.TankFactory;
import game.object.GameObject;
import game.object.Munition.MunitionFactory;
import org.joml.Vector2f;
import renderer.TextureMap;

public class Level {

    //Interaction between object (Front-End?)
    Scene scene;

    //Behavior of NPC (Back-End?)
    AISystem system;


    public Level()
    {
//        scene = new Scene();
//        scene.Load();
//
//        system = new AISystem();

    }

    public void Load()
    {
        //Level independent part
    }

    public void process()
    {
        system.Process();
        scene.Process();
    }

    public void setScene(Scene scene)
    {
        this.scene = scene;
    }

    public void setSystem(AISystem system)
    {
        this.system = system;
    }
}
