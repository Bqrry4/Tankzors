package game.level;

import Managers.InputHandler;
import Managers.Renderer;
import Managers.ResourceManager;
import Managers.Settings;
import auxiliar.TextureRegion;
import exceptions.EmptySpawner;
import exceptions.ExitFromMenuEvent;
import game.Controller.NPC;
import game.Game;
import game.menu.Button;
import game.menu.Menu;
import game.menu.MenuComponent;
import game.menu.subMenu;
import game.object.Entity.AISystem;
import game.object.Entity.EnemySpawner;
import game.object.Entity.tank.Tank;
import game.object.GameObject;
import gui.Text;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class Level {

    public static boolean LevelCleared = false;
    public static boolean GameOver = false;

    //Interaction between object (Front-End?)
    Scene scene;

    //Behavior of NPC (Back-End?)
    AISystem system;

    Menu pause;
    Menu victory;
    Menu loose;

    Text score;

    List<EnemySpawner> enemySpawners = new ArrayList<>();


    public Level()
    {
//        scene = new Scene();
//        scene.Load();
//
//        system = new AISystem();


        score = new Text("Score: " + LevelLoader.Score, ResourceManager.Instance().GetFont("font"));
        score.TranslateTo(30, 30);


        TextureRegion region = new TextureRegion(0,0,126,16,1);

        pause = new Menu(ResourceManager.Instance().GetTexture("menu"));

        subMenu pauseMenu = new subMenu("pauseMenu");
        pauseMenu.IsRoot(true);

        MenuComponent Resume = new Button(new Vector4f((float) Settings.getWidth() / 2 - 125, 320, 250, 40), new Text("Resume", ResourceManager.Instance().GetFont("font"), new Vector4f(0.8f, 0.1f, 0f,1f)), ResourceManager.Instance().GetTexture("gui") ,region)
        {
            @Override
            public void functionality() throws ExitFromMenuEvent {

                throw new ExitFromMenuEvent();
            }
        };



        MenuComponent Exit = new Button(new Vector4f((float) Settings.getWidth() / 2 - 125, 395, 250, 40), new Text("Exit", ResourceManager.Instance().GetFont("font"), new Vector4f(0.8f, 0.1f, 0f,1f)), ResourceManager.Instance().GetTexture("gui") ,region )
        {
            @Override
            public void functionality() throws ExitFromMenuEvent {

                Game.InGame = false;

                throw new ExitFromMenuEvent();
            }
        };

        pauseMenu.add(Resume);
        pauseMenu.add(Exit);

        pause.SetMenuRootAttribute(pauseMenu);


        victory = new Menu(ResourceManager.Instance().GetTexture("menu"));

        subMenu victoryMenu = new subMenu("Victory");
        victoryMenu.IsRoot(true);

        MenuComponent NextLevel = new Button(new Vector4f((float) Settings.getWidth() / 2 - 125, 320, 250, 40), new Text("NextLevel", ResourceManager.Instance().GetFont("font"), new Vector4f(0.8f, 0.1f, 0f,1f)), ResourceManager.Instance().GetTexture("gui") ,region)
        {
            @Override
            public void functionality() throws ExitFromMenuEvent {

                LevelLoader.LevelLoaded = false;
                LevelLoader.LevelID++;
                LevelLoader.OldScore = LevelLoader.Score;
                LevelCleared = false;
                throw new ExitFromMenuEvent();
            }
        };


        victoryMenu.add(NextLevel);
        victoryMenu.add(Exit);

        victory.SetMenuRootAttribute(victoryMenu);

        loose = new Menu(ResourceManager.Instance().GetTexture("menu"));

        subMenu looseMenu = new subMenu("GameOver");
        looseMenu.IsRoot(true);

        MenuComponent Retry = new Button(new Vector4f((float) Settings.getWidth() / 2 - 125, 320, 250, 40), new Text("Retry", ResourceManager.Instance().GetFont("font"), new Vector4f(0.8f, 0.1f, 0f,1f)), ResourceManager.Instance().GetTexture("gui") ,region)
        {
            @Override
            public void functionality() throws ExitFromMenuEvent {
                LevelLoader.LevelLoaded = false;
                LevelLoader.Score = LevelLoader.OldScore;
                GameOver = false;
                throw new ExitFromMenuEvent();
            }
        };


        looseMenu.add(Retry);
        looseMenu.add(Exit);

        loose.SetMenuRootAttribute(looseMenu);


    }


    public void process()
    {

        if(InputHandler.keyAction(GLFW_KEY_ESCAPE))
        {
            pause.ActivateTrigger();
        }

        if(GameOver)
        {
            loose.ActivateTrigger();
        }
        if(LevelCleared)
        {
            victory.ActivateTrigger();
        }

        if(victory.isTriggered())
        {
            victory.Process();
            return;
        }

        if(loose.isTriggered())
        {
            loose.Process();
            return;
        }


        if(pause.isTriggered())
        {
            pause.Process();
        }
        else {

            for(EnemySpawner spawner : enemySpawners)
            {
                try {
                    Tank spawned = spawner.TryToSpawn();
                    if(spawned != null)
                    {
                        NPC npc = new NPC(system, (Tank)spawned);
                        system.addComponent(npc);
                    }
                }
                catch (EmptySpawner e)
                {
                    enemySpawners.remove(e.getSpawner());

                    if(enemySpawners.isEmpty())
                    {
                        Level.LevelCleared = true;
                    }
                    break;
                }

            }

            system.Process();
            scene.Process();

            if(!score.getAsociatedString().equals("Score: " + LevelLoader.Score))
            {
                score = new Text("Score: " + LevelLoader.Score, ResourceManager.Instance().GetFont("font"));
                score.TranslateTo(30, 30);
            }

            score.render();
            Renderer.Instance().Present(ResourceManager.Instance().GetShader("font"), 1);
        }
    }

    public void setScene(Scene scene)
    {
        this.scene = scene;
    }

    public void setSystem(AISystem system)
    {
        this.system = system;
    }

    public void addSpawner(EnemySpawner spawner)
    {
        enemySpawners.add(spawner);
    }
}
